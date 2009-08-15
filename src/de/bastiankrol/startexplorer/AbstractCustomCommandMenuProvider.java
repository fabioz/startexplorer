package de.bastiankrol.startexplorer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.Category;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
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

  private PreferenceUtil preferenceUtil = new PreferenceUtil();

  AbstractCustomCommandMenuProvider()
  {
    super();
    this.registerWithActivator();
  }

  AbstractCustomCommandMenuProvider(String id)
  {
    super(id);
    this.registerWithActivator();
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.ui.actions.CompoundContributionItem#getContributionItems()
   */
  @Override
  protected IContributionItem[] getContributionItems()
  {
    this.doCleanup();
    List<CommandConfig> commandConfigList =
        this.preferenceUtil.getCommandConfigListFromPreferences();
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

    List<IContributionItem> contributionItemList =
        new ArrayList<IContributionItem>();

    for (int i = 0; i < commandConfigList.size(); i++)
    {
      CommandConfig commandConfig = commandConfigList.get(i);

      if (!isEnabled(commandConfig))
      {
        continue;
      }

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
      // BEGIN: CODE FROM 3.4 ON (DOES NOT WORK WITH 3.3 OR BELOW)
      CommandContributionItemParameter commandContributionItemParameter =
          new CommandContributionItemParameter( //
              serviceLocator, // IServiceLocator serviceLocator,
              commandId, // String id,
              commandId, // String commandId,
              CommandContributionItem.STYLE_PUSH// int style)
          );
      commandContributionItemParameter.label =
          this.getNameFromCommandConfig(commandConfig);
      contributionItemList.add(new CommandContributionItem(
          commandContributionItemParameter));
      // END: CODE FROM 3.4 ON (DOES NOT WORK WITH 3.3 OR BELOW)

      // BEGIN: CODE FOR UP TO 3.3 (would work with 3.4 also)
      // Map<String, String> parms = new HashMap<String, String>();
      // contributionItemList.add(
      // new CommandContributionItem(
      // serviceLocator, // serviceLocator
      // commandId, // id (contributionItemArray-Id)
      // commandId, // commandId
      // parms, // parameters
      // null, // icon
      // null, // disabledIcon
      // null, // hoverIcon
      // this.getNameFromCommandConfig(commandConfig), // label
      // null, // mnemonic
      // null, // tooltip
      // CommandContributionItem.STYLE_PUSH));
      // END: CODE FOR UP TO 3.3 (would work with 3.4 also)
    }
    return contributionItemList
        .toArray(new CommandContributionItem[contributionItemList.size()]);
  }

  protected abstract boolean isEnabled(CommandConfig commandConfig);

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

  /**
   * 
   */
  private void registerWithActivator()
  {
    Activator.getDefault().registerCustomCommandMenuProvider(this);
  }

  /**
   * Does clean up operations when the plug-in is stopped.
   */
  void doCleanupAtPluginStop()
  {
    this.doCleanup();
    if (this.customCommandCategory != null)
    {
      this.customCommandCategory.undefine();
      this.customCommandCategory = null;
    }
  }
}
