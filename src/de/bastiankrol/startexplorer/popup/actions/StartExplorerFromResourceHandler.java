package de.bastiankrol.startexplorer.popup.actions;

import java.util.List;

import de.bastiankrol.startexplorer.RuntimeExecWrapper;

/**
 * Examines the selection in the package explorer/navigator and opens a Windows
 * Explorer for all selected files/folders.
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$ $Author:$
 */
public class StartExplorerFromResourceHandler extends
    AbstractStartFromResourceHandler
{

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromResourceHandler#doActionForResources(java.util.List)
   */
  @Override
  protected void doActionForResources(List<String> pathList)
  {
    RuntimeExecWrapper.startWindowsExplorerForPathList(pathList);
  }

}
