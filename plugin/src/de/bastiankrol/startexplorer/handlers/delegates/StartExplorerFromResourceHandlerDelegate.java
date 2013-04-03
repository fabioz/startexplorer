package de.bastiankrol.startexplorer.handlers.delegates;

import static de.bastiankrol.startexplorer.Activator.*;

import java.io.File;
import java.util.List;

import de.bastiankrol.startexplorer.ResourceType;

/**
 * Examines the selection in the package explorer/navigator and opens a Windows
 * Explorer for all selected files/folders.
 * 
 * @author Bastian Krol
 */
public class StartExplorerFromResourceHandlerDelegate extends
    AbstractStartFromResourceHandlerDelegate
{

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromEditorHandler#getResourceType()
   */
  protected ResourceType getResourceType()
  {
    if (getPluginContext().isFileSelectionSupportedByFileManager()
        && this.getPreferenceModel().isSelectFileInExplorer())
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
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromResourceHandler#doActionForFileList(java.util.List)
   */
  @Override
  protected void doActionForFileList(List<File> fileList)
  {
    this.getRuntimeExecCalls().startFileManagerForFileList(fileList,
        this.getPreferenceModel().isSelectFileInExplorer());
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromResourceHandler#getAppropriateStartFromStringHandler()
   */
  @Override
  protected AbstractStartFromEditorHandlerDelegate getAppropriateStartFromEditorHandlerDelegate()
  {
    return new StartExplorerFromEditorHandlerDelegate();
  }
}
