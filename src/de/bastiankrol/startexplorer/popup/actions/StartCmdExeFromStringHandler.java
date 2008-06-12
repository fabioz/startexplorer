package de.bastiankrol.startexplorer.popup.actions;

import de.bastiankrol.startexplorer.RuntimeExecWrapper;

/**
 * Examines the selected region in a text file, tries to interpret it as a
 * Windows filesystem path and tries to start cmd.exe for the directory described by that path.
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
  protected PathCheck.ResourceType getResourceType()
  {
    return PathCheck.ResourceType.DIRECTORY;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#doActionForPath(java.lang.String)
   */
  protected void doActionForPath(String pathString)
  {
    RuntimeExecWrapper.startCmdExeForPath(pathString);
  }
}
