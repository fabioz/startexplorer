package de.bastiankrol.startexplorer.popup.actions;

import java.io.File;
import java.util.List;

import de.bastiankrol.startexplorer.Activator;
import de.bastiankrol.startexplorer.ResourceType;

/**
 * Examines the selection in the package explorer/navigator and opens a Windows
 * Explorer for all selected files/folders.
 * 
 * @author Bastian Krol
 */
public class StartExplorerFromResourceHandler extends
    AbstractStartFromResourceHandler
{

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromEditorHandler#getResourceType()
   */
  protected ResourceType getResourceType()
  {
    if (Activator.getDefault().isFileSelectionSupportedByFileManager()
        && this.getPreferenceUtil().getSelectFileInExplorer())
    {
      return ResourceType.BOTH;
    }
    else
    {
      return ResourceType.DIRECTORY;
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromResourceHandler#doActionForFileList(java.util.List)
   */
  @Override
  protected void doActionForFileList(List<File> fileList)
  {
    this.getRuntimeExecCalls().startFileManagerForFileList(fileList,
        this.getPreferenceUtil().getSelectFileInExplorer());
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromResourceHandler#getAppropriateStartFromStringHandler()
   */
  @Override
  protected AbstractStartFromEditorHandler getAppropriateStartFromStringHandler()
  {
    return new StartExplorerFromEditorHandler();
  }
}
