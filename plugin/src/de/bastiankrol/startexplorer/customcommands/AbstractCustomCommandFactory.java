package de.bastiankrol.startexplorer.customcommands;

import static de.bastiankrol.startexplorer.Activator.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Category;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.swt.SWTException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.services.IServiceLocator;

import de.bastiankrol.startexplorer.preferences.PreferenceModel;
import de.bastiankrol.startexplorer.util.Util;

abstract class AbstractCustomCommandFactory
{
  private static final String CUSTOM_COMMAND_CATEGORY = "de.bastiankrol.startexplorer.customCommandCategory";

  private static final String COMMAND_ID_DUMMY_COME_BACK_LATER = "de.bastiankrol.startexplorer.dummy_id_come_back_later";

  private static int customCommandIdNumber;

  private Category customCommandCategory;
  private List<CommandConfig> commandConfigList;
  private boolean customCommandsFromSharedFileHaveBeenAdded;

  private Command dummyCommandComeBackLater;

  private static synchronized int getNextCustomCommandIdNumber()
  {
    return customCommandIdNumber++;
  }

  IContributionItem[] getContributionItems()
  {
    getLogFacility().logDebug("getContributionItems() start");
    this.doCleanup();
    getLogFacility().logDebug("getContributionItems() cleanup done");

    getLogFacility().logDebug("fetching command configs from preferences");
    this.commandConfigList = getPreferenceModel().getCommandConfigList();
    this.customCommandsFromSharedFileHaveBeenAdded = getPreferenceModel()
        .customCommandsFromSharedFileHaveBeenAdded();
    getLogFacility().logDebug(
        "fetched " + commandConfigList.size() + " configs.");

    IContributionItem[] contributionItems = this.createContributionItems();
    getLogFacility().logDebug("getContributionItems() done");
    return contributionItems;
  }

  private IContributionItem[] createContributionItems()
  {
    getLogFacility().logDebug("createContributionItems() start");

    List<IContributionItem> contributionItemList = new ArrayList<IContributionItem>();
    for (CommandConfig commandConfig : this.commandConfigList)
    {
      getLogFacility().logDebug(
          "creating contribution item for " + commandConfig);
      if (!isEnabled(commandConfig))
      {
        getLogFacility().logDebug("not enabled");
        continue;
      }

      // Retrieve the cached command from the command config.
      // If it has not been initialized before, it will be created on demand and
      // stored in the command config.
      Command command = this.getCommandFromCommandConfig(commandConfig);
      getLogFacility().logDebug(
          "got command for " + commandConfig.getCommand() + ": " + command);
      getLogFacility().logDebug("command.isDefined(): " + command.isDefined());

      CommandContributionItemParameter commandContributionItemParameter = new CommandContributionItemParameter( //
          this.getServiceLocator(), // IServiceLocator serviceLocator,
          command.getId(), // String id,
          command.getId(), // String commandId,
          CommandContributionItem.STYLE_PUSH // int style)
      );
      commandContributionItemParameter.label = this
          .getNameFromCommandConfig(commandConfig);
      contributionItemList.add(this
          .createContributionItem(commandContributionItemParameter));
      getLogFacility().logDebug("contribution item added to list");
    }
    this.addComeBackLaterDummyCommand(contributionItemList);

    CommandContributionItem[] contributionItems = contributionItemList
        .toArray(new CommandContributionItem[contributionItemList.size()]);
    getLogFacility().logDebug("createContributionItems() done");
    return contributionItems;
  }

  private void addComeBackLaterDummyCommand(
      List<IContributionItem> contributionItemList)
  {
    if (!this.customCommandsFromSharedFileHaveBeenAdded)
    {
      if (this.dummyCommandComeBackLater == null)
      {
        this.initDummyCommandComeBackLater();
      }
      CommandContributionItemParameter commandContributionItemParameter = new CommandContributionItemParameter(
          this.getServiceLocator(), COMMAND_ID_DUMMY_COME_BACK_LATER,
          COMMAND_ID_DUMMY_COME_BACK_LATER, CommandContributionItem.STYLE_PUSH);
      contributionItemList.add(this
          .createContributionItem(commandContributionItemParameter));
    }
  }

  private void initDummyCommandComeBackLater()
  {
    ICommandService commandService = this
        .getCommandService(getServiceLocator());
    this.dummyCommandComeBackLater = commandService
        .getCommand(COMMAND_ID_DUMMY_COME_BACK_LATER);
    this.dummyCommandComeBackLater.define(
        SharedFileFinder.TITLE_SHARED_FILES_LATER,
        SharedFileFinder.MESSAGE_SHARED_FILES_LATER,
        this.getLazyInitCategory(commandService));
    this.dummyCommandComeBackLater.setHandler(new AbstractHandler()
    {
      @Override
      public Object execute(ExecutionEvent event) throws ExecutionException
      {
        getPluginContext().getMessageDialogHelper().displayInformationMessage(
            SharedFileFinder.TITLE_SHARED_FILES_LATER,
            SharedFileFinder.MESSAGE_SHARED_FILES_LATER);
        return null;
      }
    });
  }

  CommandContributionItem createContributionItem(
      CommandContributionItemParameter commandContributionItemParameter)
  {
    return new CommandContributionItem(commandContributionItemParameter);
  }

  /**
   * Get the command from the command config. If it has not been created yet, it
   * will be created on demand.
   * 
   * @param commandConfig the command config to ask for the command object
   * @return the command object corresponding the command config
   */
  abstract Command getCommandFromCommandConfig(CommandConfig commandConfig);

  /**
   * Returns the proper name from the command config.
   * 
   * @param commandConfig the CommandConfig
   * @return the proper name for the given command config
   */
  abstract String getNameFromCommandConfig(CommandConfig commandConfig);

  abstract IHandlerActivation getHandlerActivationFromCommandConfig(
      CommandConfig commandConfig);

  abstract void setHandlerActivationInCommandConfig(
      CommandConfig commandConfig, IHandlerActivation handlerActivation);

  /**
   * Callback method for CommandConfig; in case the command object is not yet
   * initialized when {@link CommandConfig#getEclipseCommandForResourceView} or
   * {@link CommandConfig#getEclipseCommandForEditor} is called, the
   * CommandConfig calls this method.
   * 
   * @param commandConfig the command config for which the command object will
   *          be created
   * @return the command object corresponding the command config
   */
  Command createCommand(CommandConfig commandConfig)
  {
    getLogFacility().logDebug(
        "createCommand(" + commandConfig.getCommand() + ") start");
    String commandNumberString = Util
        .intToString(getNextCustomCommandIdNumber());
    String commandId = "de.bastiankrol.startexplorer.customCommand"
        + commandNumberString;
    ICommandService commandService = this
        .getCommandService(getServiceLocator());
    Command command = commandService.getCommand(commandId);
    String commandName = "StartExplorer Custom Command " + commandNumberString;
    getLogFacility().logDebug(
        "defining command for " + commandConfig.getCommand() + " as "
            + commandName);
    command.define(commandName, this.getNameFromCommandConfig(commandConfig),
        this.getLazyInitCategory(commandService));
    this.activateHandler(commandConfig, commandId);
    getLogFacility().logDebug(
        "createCommand(" + commandConfig.getCommand() + ") done");
    return command;
  }

  private void activateHandler(CommandConfig commandConfig, String commandId)
  {
    IHandlerService handlerService = (IHandlerService) getHandlerService(getServiceLocator());
    IHandler handler = this.createHandlerForCustomCommand(commandConfig);
    IHandlerActivation handlerActivation = handlerService.activateHandler(
        commandId, handler);
    this.setHandlerActivationInCommandConfig(commandConfig, handlerActivation);
  }

  /**
   * Returns {@code true} if and only if the command is enabled for this view
   * (resource view or editor).
   * 
   * @param commandConfig the command config to query
   * @return {@code true} if and only if the command is enabled for this view
   *         (resource view or editor)
   */
  abstract boolean isEnabled(CommandConfig commandConfig);

  /**
   * Creates a handler for the given command config
   * 
   * @param commandConfig the CommandConfig to create a handler for
   * @return a Handler for the given command config
   */
  protected abstract IHandler createHandlerForCustomCommand(
      CommandConfig commandConfig);

  /**
   * Initializes the category.
   * 
   * @param commandService the {@link ICommandService} reference
   */
  private Category getLazyInitCategory(ICommandService commandService)
  {
    if (this.customCommandCategory == null)
    {
      this.customCommandCategory = commandService
          .getCategory(CUSTOM_COMMAND_CATEGORY);
    }
    return this.customCommandCategory;
  }

  /**
   * Does clean up operations when the plug-in is stopped.
   */
  public void doCleanupAtPluginStop()
  {
    getLogFacility().logDebug("doCleanupAtPluginStop() start");
    this.doCleanup(true);
    if (this.customCommandCategory != null)
    {
      this.customCommandCategory.undefine();
      this.customCommandCategory = null;
    }
    if (this.dummyCommandComeBackLater != null)
    {
      this.dummyCommandComeBackLater.undefine();
      this.dummyCommandComeBackLater = null;
    }
    getLogFacility().logDebug("doCleanupAtPluginStop() done");
  }

  /**
   * Undefines all created commands.
   */
  void doCleanup()
  {
    this.doCleanup(false);
  }

  /**
   * Undefines all created commands and deactivates all handlers.
   */
  private void doCleanup(boolean atPluginStop)
  {
    getLogFacility().logDebug("doCleanup(" + atPluginStop + ") start");
    if (this.commandConfigList != null)
    {
      for (CommandConfig commandConfig : this.commandConfigList)
      {
        Command eclipseCommandForResourceViewNoInit = commandConfig
            .getEclipseCommandForResourceViewNoInit();
        this.disposeCommand(eclipseCommandForResourceViewNoInit, atPluginStop);
        commandConfig.deleteEclipseCommandForResourceView();
        deactivateHandler(commandConfig.getHandlerActivationForResourceView(),
            commandConfig.getNameForResourcesMenu());
        commandConfig.setHandlerActivationForResourceView(null);

        Command eclipseCommandForEditorNoInit = commandConfig
            .getEclipseCommandForEditorNoInit();
        this.disposeCommand(eclipseCommandForEditorNoInit, atPluginStop);
        commandConfig.deleteEclipseCommandForEditor();
        deactivateHandler(commandConfig.getHandlerActivationForEditor(),
            commandConfig.getNameForTextSelectionMenu());
        commandConfig.setHandlerActivationForEditor(null);
      }
      this.commandConfigList = null;
    }
    getLogFacility().logDebug("doCleanup(" + atPluginStop + ") done");
  }

  private void disposeCommand(Command command, boolean atPluginStop)
  {
    if (command != null)
    {
      try
      {
        getLogFacility().logDebug("undefining command: " + command);
        command.undefine();
        getLogFacility().logDebug("command undefined");
      }
      catch (SWTException e)
      {
        // Sometimes on plugin stop the device is already disposed when
        // undefining the commands, which results in an SWTException. If the
        // device has been disposed, we do not need to undefine the command
        // anymore. We do not want to clutter the log with this, so we eat
        // the SWTException here.
        if (!atPluginStop)
        {
          throw e;
        }
      }
      command = null;
    }
  }

  private void deactivateHandler(IHandlerActivation handlerActivation,
      String name)
  {
    if (handlerActivation != null)
    {
      IHandlerService handlerService = this.getHandlerService(this
          .getServiceLocator());
      if (handlerService != null)
      {
        getLogFacility().logDebug("deactivating handler for: " + name);
        handlerService.deactivateHandler(handlerActivation);
        getLogFacility().logDebug("handler " + name + " deactivated.");
      }
    }
  }

  PreferenceModel getPreferenceModel()
  {
    return getPluginContext().getPreferenceModel();
  }

  IServiceLocator getServiceLocator()
  {
    return (IServiceLocator) PlatformUI.getWorkbench();
  }

  ICommandService getCommandService(IServiceLocator serviceLocator)
  {
    return (ICommandService) serviceLocator.getService(ICommandService.class);
  }

  IHandlerService getHandlerService(IServiceLocator serviceLocator)
  {
    return (IHandlerService) serviceLocator.getService(IHandlerService.class);
  }
}
