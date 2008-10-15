package de.bastiankrol.startexplorer.popup.actions;

import java.io.File;

import de.bastiankrol.startexplorer.preferences.CommandConfig;
import de.bastiankrol.startexplorer.util.PathChecker;

/**
 * @author Bastian Krol
 * @version $Revision:$ $Date:$ $Author:$
 */
public class CustomCommandForStringHandler extends
    AbstractStartFromStringHandler
{

  private CommandConfig commandConfig;

  /**
   * Creates a CustomCommandForResourceHandler for the given CommandConfig
   * 
   * @param commandConfig the command configuration which, among other things,
   *          contains the command line string to execute by this handler
   */
  public CustomCommandForStringHandler(CommandConfig commandConfig)
  {
    this.commandConfig = commandConfig;
  }

  /**
   * Returns the Command Config for this handler
   * 
   * @return the Command Config for this handler
   */
  protected CommandConfig getCommandConfig()
  {
    return this.commandConfig;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#getResourceType()
   */
  protected PathChecker.ResourceType getResourceType()
  {
    // TODO should be configurable in preferences
    return PathChecker.ResourceType.BOTH;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#doActionForFile(java.io.File)
   */
  @Override
  protected void doActionForFile(File file)
  {
    this.getRuntimeExecCalls().startCustomCommandForFile(
        this.getCommandConfig().getCommand(), file);
  }
}
