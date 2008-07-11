package de.bastiankrol.startexplorer.popup.actions;

import java.io.File;
import java.util.List;

import de.bastiankrol.startexplorer.util.PathChecker;

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
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#getResourceType()
   */
  protected PathChecker.ResourceType getResourceType()
  {
    return PathChecker.ResourceType.DIRECTORY;
  }

  /**
   * {@inheritDoc}
   *
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromResourceHandler#doActionForFileList(java.util.List)
   */
  @Override
  protected void doActionForFileList(List<File> fileList)
  {
    this.getRuntimeExecCalls().startWindowsExplorerForFileList(fileList);
  }

}
