package de.bastiankrol.startexplorer.customcommands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.Category;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.services.IServiceLocator;

import de.bastiankrol.startexplorer.preferences.PreferenceUtil;
import de.bastiankrol.startexplorer.util.Util;

abstract class AbstractCustomCommandFactory
{
  private static final String CUSTOM_COMMAND_CATEGORY = "de.bastiankrol.startexplorer.customCommandCategory";

  private static int customCommandIdNumber;

  private Category customCommandCategory;
  private List<Command> customCommands;

  private PreferenceUtil preferenceUtil = new PreferenceUtil();

  private static synchronized int getNextCustomCommandIdNumber()
  {
    return customCommandIdNumber++;
  }

  public IContributionItem[] getContributionItems()
  {
    List<CommandConfig> commandConfigList = this.preferenceUtil
        .getCommandConfigListFromPreferences();
    return this.createContributionItems(commandConfigList);
  }

  private IContributionItem[] createContributionItems(
      List<CommandConfig> commandConfigList)
  {
    this.doCleanup();

    List<IContributionItem> contributionItemList = new ArrayList<IContributionItem>();
    this.customCommands = new ArrayList<Command>(commandConfigList.size());
    for (CommandConfig commandConfig : commandConfigList)
    {
      if (!isEnabled(commandConfig))
      {
        continue;
      }

      Command command = this.getCommandFromCommandConfig(commandConfig);
      this.customCommands.add(command);

      // create contributionItemArray
      CommandContributionItemParameter commandContributionItemParameter = new CommandContributionItemParameter( //
          this.getServiceLocator(), // IServiceLocator serviceLocator,
          command.getId(), // String id,
          command.getId(), // String commandId,
          CommandContributionItem.STYLE_PUSH// int style)
      );
      commandContributionItemParameter.label = this
          .getNameFromCommandConfig(commandConfig);
      contributionItemList.add(this.createContributionItem(commandContributionItemParameter));
    }
    return contributionItemList
        .toArray(new CommandContributionItem[contributionItemList.size()]);
  }

  CommandContributionItem createContributionItem(
      CommandContributionItemParameter commandContributionItemParameter)
  {
    return new CommandContributionItem(
        commandContributionItemParameter);
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
    ICommandService commandService = this
        .getCommandService(getServiceLocator());
    String commandNumberString = Util
        .intToString(getNextCustomCommandIdNumber());
    String commandId = "de.bastiankrol.startexplorer.customCommand"
        + commandNumberString;
    Command command = commandService.getCommand(commandId);
    command.define("StartExplorer Custom Command " + commandNumberString,
        this.getNameFromCommandConfig(commandConfig),
        this.getLazyInitCategory(commandService));
    IHandler handler = this.createHandlerForCustomCommand(commandConfig);
    command.setHandler(handler);
    return command;
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
   * Undefines all created commands.
   */
  void doCleanup()
  {
    if (this.customCommands != null)
    {
      for (Command command : this.customCommands)
      {
        if (command != null)
        {
          command.undefine();
          command = null;
        }
      }
      this.customCommands = null;
    }
  }

  /**
   * Does clean up operations when the plug-in is stopped.
   */
  public void doCleanupAtPluginStop()
  {
    this.doCleanup();
    if (this.customCommandCategory != null)
    {
      this.customCommandCategory.undefine();
      this.customCommandCategory = null;
    }
  }

  IServiceLocator getServiceLocator()
  {
    return (IServiceLocator) PlatformUI.getWorkbench();
  }

  ICommandService getCommandService(IServiceLocator serviceLocator)
  {
    return (ICommandService) serviceLocator.getService(ICommandService.class);
  }

  void injectPreferenceUtil(PreferenceUtil preferenceUtil)
  {
    this.preferenceUtil = preferenceUtil;
  }
}
