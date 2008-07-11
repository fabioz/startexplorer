package de.bastiankrol.startexplorer.popup.actions;

import java.io.File;

import de.bastiankrol.startexplorer.util.PathChecker;

/**
 * Examines the selected region in a text file, tries to interpret it as a
 * Windows filesystem path and tries to start cmd.exe for the directory
 * described by that path.
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$ $Author:$
 */
public class StartCmdExeFromStringHandler extends
    AbstractStartFromStringHandler
{
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#getResourceType()
   */
  protected PathChecker.ResourceType getResourceType()
  {
    return PathChecker.ResourceType.DIRECTORY;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#doActionForFile(java.io.File)
   */
  protected void doActionForFile(File file)
  {
    this.getRuntimeExecCalls().startCmdExeForFile(file);
  }
}
