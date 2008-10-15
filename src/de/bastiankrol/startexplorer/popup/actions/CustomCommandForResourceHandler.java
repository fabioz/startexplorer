package de.bastiankrol.startexplorer.popup.actions;

import java.io.File;
import java.util.List;

import de.bastiankrol.startexplorer.preferences.CommandConfig;
import de.bastiankrol.startexplorer.util.PathChecker;

/**
 * Examines the selection in the package explorer/navigator and opens a Windows
 * Explorer for all selected files/folders.
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$ $Author:$
 */
public class CustomCommandForResourceHandler extends
    AbstractStartFromResourceHandler
{

  private CommandConfig commandConfig;

  /**
   * Creates a CustomCommandForResourceHandler for the given CommandConfig
   * 
   * @param commandConfig the command configuration which, among other things,
   *          contains the command line string to execute by this handler
   */
  public CustomCommandForResourceHandler(CommandConfig commandConfig)
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
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromResourceHandler#doActionForFileList(java.util.List)
   */
  @Override
  protected void doActionForFileList(List<File> fileList)
  {
    this.getRuntimeExecCalls().startCustomCommandForFileList(
        this.getCommandConfig().getCommand(), fileList);
  }
}
