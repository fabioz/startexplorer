package de.bastiankrol.startexplorer.handlers.delegates;

import static de.bastiankrol.startexplorer.Activator.*;

import java.io.File;

import de.bastiankrol.startexplorer.ResourceType;

/**
 * Examines the selected region in a text file, tries to interpret it as a
 * Windows filesystem path and opens a Windows Explorer in the location
 * described by that path.
 * 
 * @author Bastian Krol
 */
public class StartExplorerFromEditorHandlerDelegate extends
    AbstractStartFromEditorHandlerDelegate
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
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromEditorHandler#doActionForFile(java.io.File)
   */
  protected void doActionForFile(File file)
  {
    this.getRuntimeExecCalls().startFileManagerForFile(file,
        this.getPreferenceModel().isSelectFileInExplorer());
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromEditorHandler#getAppropriateStartFromResourceHandler()
   */
  @Override
  protected AbstractStartFromResourceHandlerDelegate getAppropriateStartFromResourceHandlerDelegate()
  {
    return new StartExplorerFromResourceHandlerDelegate();
  }
}
