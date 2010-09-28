package de.bastiankrol.startexplorer;

import org.eclipse.core.commands.IHandler;

import de.bastiankrol.startexplorer.popup.actions.CustomCommandForStringHandler;
import de.bastiankrol.startexplorer.preferences.CommandConfig;

/**
 * TODO Klasse kommentieren
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$ $Author:$
 */
public class CustomCommandEditorMenuProvider extends
    AbstractCustomCommandMenuProvider
{
  /** Die CM_VERSION. */
  public static final String CM_VERSION = "$Revision:$ $HeadURL:$";

  /**
   * CustomCommandEditorMenuProvider Konstruktor.
   */
  public CustomCommandEditorMenuProvider()
  {
    super();
  }

  /**
   * CustomCommandEditorMenuProvider Konstruktor.
   * 
   * @param id
   */
  public CustomCommandEditorMenuProvider(String id)
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
    return new CustomCommandForStringHandler(commandConfig);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.AbstractCustomCommandMenuProvider#getNameFromCommandConfig(de.bastiankrol.startexplorer.preferences.CommandConfig)
   */
  @Override
  protected String getNameFromCommandConfig(CommandConfig commandConfig)
  {
    return commandConfig.getNameForTextSelectionMenu();
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.AbstractCustomCommandMenuProvider#isEnabled(de.bastiankrol.startexplorer.preferences.CommandConfig)
   */
  @Override
  protected boolean isEnabled(CommandConfig commandConfig)
  {
    return commandConfig.isEnabledForTextSelectionMenu();
  }
}
