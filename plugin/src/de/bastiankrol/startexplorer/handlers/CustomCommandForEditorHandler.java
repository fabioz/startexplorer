package de.bastiankrol.startexplorer.handlers;

import de.bastiankrol.startexplorer.customcommands.CommandConfig;
import de.bastiankrol.startexplorer.handlers.delegates.CustomCommandForEditorHandlerDelegate;

/**
 * @author Bastian Krol
 */
public class CustomCommandForEditorHandler extends
    AbstractStartFromEditorHandler
{
  // TODO We only have this here as an instance variable to be able to pass it
  // to the delegate constructor later. This should be refactored.
  private CommandConfig commandConfig;

  public CustomCommandForEditorHandler(CommandConfig commandConfig)
  {
    this.commandConfig = commandConfig;
  }

  @Override
  CustomCommandForEditorHandlerDelegate getDelegate()
  {
    return new CustomCommandForEditorHandlerDelegate(this.commandConfig);
  }
}
