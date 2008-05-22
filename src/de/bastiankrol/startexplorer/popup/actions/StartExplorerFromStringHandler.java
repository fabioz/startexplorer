package de.bastiankrol.startexplorer.popup.actions;

import de.bastiankrol.startexplorer.RuntimeExecWrapper;

/**
 * Examines the selected region in a text file, tries to interpret it as a
 * Windows filesystem path and opens a Windows Explorer in the location
 * described by that path.
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$ $Author:$
 */
public class StartExplorerFromStringHandler extends
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
    RuntimeExecWrapper.startWindowsExplorerForPath(pathString);
  }
}
