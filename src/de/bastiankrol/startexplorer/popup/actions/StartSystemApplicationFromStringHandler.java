package de.bastiankrol.startexplorer.popup.actions;

import de.bastiankrol.startexplorer.RuntimeExecWrapper;

/**
 * Examines the selected region in a text file, tries to interpret it as a
 * Windows filesystem path and tries to start the default windows system
 * application for the file described by that path.
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$ $Author:$
 */
public class StartSystemApplicationFromStringHandler extends
    AbstractStartFromStringHandler
{
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#getResourceType()
   */
  protected PathCheck.ResourceType getResourceType()
  {
    return PathCheck.ResourceType.FILE;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#doActionForPath(java.lang.String)
   */
  protected void doActionForPath(String pathString)
  {
    RuntimeExecWrapper.startWindowsSystemApplicationForPath(pathString);
  }
}
