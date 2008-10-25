package de.bastiankrol.startexplorer;

import org.eclipse.core.commands.IHandler;

import de.bastiankrol.startexplorer.popup.actions.CustomCommandForResourceHandler;
import de.bastiankrol.startexplorer.preferences.CommandConfig;

/**
 * TODO Klasse kommentieren
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$ $Author:$
 */
public class CustomCommandPackageExplorerMenuProvider extends
    AbstractCustomCommandMenuProvider
{
  /** Die CM_VERSION. */
  public static final String CM_VERSION = "$Revision:$ $HeadURL:$";

  /**
   * CustomCommandPackageExplorerMenuProvider Konstruktor.
   */
  public CustomCommandPackageExplorerMenuProvider()
  {
    super();
  }

  /**
   * CustomCommandPackageExplorerMenuProvider Konstruktor.
   * 
   * @param id
   *          ...
   */
  public CustomCommandPackageExplorerMenuProvider(String id)
  {
    super(id);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.AbstractCustomCommandMenuProvider#createHandlerForCustomCommand(de.bastiankrol.startexplorer.preferences.CommandConfig)
   */
  @Override
  protected IHandler createHandlerForCustomCommand(CommandConfig commandConfig)
  {
    return new CustomCommandForResourceHandler(commandConfig);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.AbstractCustomCommandMenuProvider#getNameFromCommandConfig(de.bastiankrol.startexplorer.preferences.CommandConfig)
   */
  @Override
  protected String getNameFromCommandConfig(CommandConfig commandConfig)
  {
    return commandConfig.getNameForResourcesMenu();
  }
}
