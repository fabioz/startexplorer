package de.bastiankrol.startexplorer.handlers;

import de.bastiankrol.startexplorer.customcommands.CommandConfig;
import de.bastiankrol.startexplorer.handlers.delegates.CustomCommandForResourceHandlerDelegate;

/**
 * Examines the selection in the package explorer/navigator and opens a Windows
 * Explorer for all selected files/folders.
 * 
 * @author Bastian Krol
 */
public class CustomCommandForResourceHandler extends
    AbstractStartFromResourceHandler
{
  // TODO We only have this here as an instance variable to be able to pass it
  // to the delegate constructor later. This should be refactored.
  private CommandConfig commandConfig;

  public CustomCommandForResourceHandler(CommandConfig commandConfig)
  {
    this.commandConfig = commandConfig;
  }

  @Override
  CustomCommandForResourceHandlerDelegate getDelegate()
  {
    return new CustomCommandForResourceHandlerDelegate(this.commandConfig);
  }
}
