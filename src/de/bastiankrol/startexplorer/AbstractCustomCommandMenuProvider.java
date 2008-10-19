package de.bastiankrol.startexplorer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.Category;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.services.IServiceLocator;

import de.bastiankrol.startexplorer.preferences.CommandConfig;
import de.bastiankrol.startexplorer.preferences.PreferenceUtil;
import de.bastiankrol.startexplorer.util.Util;

/**
 * TODO Klasse kommentieren
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$ $Author:$
 */
public abstract class AbstractCustomCommandMenuProvider extends
    CompoundContributionItem
{
  private static final String CUSTOM_COMMAND_CATEGORY =
      "de.bastiankrol.startexplorer.customCommandCategory";

  private Category customCommandCategory;
  private Command[] customCommandArray;

  AbstractCustomCommandMenuProvider()
  {
    super();
  }

  AbstractCustomCommandMenuProvider(String id)
  {
    super(id);
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.ui.actions.CompoundContributionItem#getContributionItems()
   */
  @Override
  protected IContributionItem[] getContributionItems()
  {
    IContextService contextService = (IContextService) PlatformUI
    .getWorkbench().getService(IContextService.class);
    contextService.getDefinedContextIds();
    contextService.getActiveContextIds();
    // contextService.getContext("org.eclipse.ui.edit.text.actionSet.navigation");

    
    this.doCleanup();
    List<CommandConfig> commandConfigList =
        PreferenceUtil.getCommandConfigListFromPreferences();
    return this.createContributionItems(commandConfigList);
  }

  private IContributionItem[] createContributionItems(
      List<CommandConfig> commandConfigList)
  {
    IServiceLocator serviceLocator =
        (IServiceLocator) PlatformUI.getWorkbench();
    ICommandService commandService =
        (ICommandService) serviceLocator.getService(ICommandService.class);
    this.lazyInitCategory(commandService);

    this.doCleanup();
    this.customCommandArray = new Command[commandConfigList.size()];

    IContributionItem[] contributionItemArray =
        new IContributionItem[commandConfigList.size()];

    for (int i = 0; i < commandConfigList.size(); i++)
    {
      CommandConfig commandConfig = commandConfigList.get(i);

      // create command
      String commandNumberString = Util.intToString(i);
      String commandId =
          "de.bastiankrol.startexplorer.customCommand" + commandNumberString;
      this.customCommandArray[i] = commandService.getCommand(commandId);
      this.customCommandArray[i].define("StartExplorer Custom Command "
          + commandNumberString, this.getNameFromCommandConfig(commandConfig),
          this.customCommandCategory);
      IHandler myHandler = this.createHandlerForCustomCommand(commandConfig);
      this.customCommandArray[i].setHandler(myHandler);
      commandConfig.attachEclipseCommand(this.customCommandArray[i]);

      // create contributionItemArray
      Map<String, String> parms = new HashMap<String, String>();
      contributionItemArray[i] = new CommandContributionItem( //
          serviceLocator, // serviceLocator
          commandId, // id (contributionItemArray-Id)
          commandId, // commandId
          parms, // parameters
          null, // icon
          null, // disabledIcon
          null, // hoverIcon
          this.getNameFromCommandConfig(commandConfig), // label
          null, // mnemonic
          null, // tooltip
          CommandContributionItem.STYLE_PUSH);
    }
    return contributionItemArray;
  }

  /**
   * Creates a handler for the given command config
   * 
   * @param commandConfig the CommandConfig to create a handler for
   * @return a Handler for the given command config
   */
  protected abstract IHandler createHandlerForCustomCommand(
      CommandConfig commandConfig);

  /**
   * Returns the proper name from the command config.
   * 
   * @param commandConfig the CommandConfig
   * @return the proper name for the given command config
   */
  protected abstract String getNameFromCommandConfig(CommandConfig commandConfig);

  private void lazyInitCategory(ICommandService commandService)
  {
    if (this.customCommandCategory == null)
    {
      this.customCommandCategory =
          commandService.getCategory(CUSTOM_COMMAND_CATEGORY);
    }
  }

  private void doCleanup()
  {
    if (this.customCommandArray != null)
    {
      for (Command command : this.customCommandArray)
      {
        if (command != null)
        {
          command.undefine();
          command = null;
        }
      }
      this.customCommandArray = null;
    }
  }

  private void doCompleteCleanup()
  {
    this.doCleanup();
    if (this.customCommandCategory != null)
    {
      this.customCommandCategory.undefine();
    }
  }
}
