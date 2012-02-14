package de.bastiankrol.startexplorer.popup.actions;

import java.io.File;

import de.bastiankrol.startexplorer.util.PathChecker;

/**
 * Examines the selected region in a text file, tries to interpret it as a
 * Windows filesystem path and opens a Windows Explorer in the location
 * described by that path.
 * 
 * @author Bastian Krol
 */
public class StartExplorerFromStringHandler extends
    AbstractStartFromStringHandler
{
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#getResourceType()
   */
  protected PathChecker.ResourceType getResourceType()
  {
    if (this.getPreferenceUtil().getSelectFileInExplorer())
    {
      return PathChecker.ResourceType.BOTH;
    }
    else
    {
      return PathChecker.ResourceType.DIRECTORY;
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#doActionForFile(java.io.File)
   */
  protected void doActionForFile(File file)
  {
    this.getRuntimeExecCalls().startWindowsExplorerForFile(file,
        this.getPreferenceUtil().getSelectFileInExplorer());
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#getAppropriateStartFromResourceHandler()
   */
  @Override
  protected AbstractStartFromResourceHandler getAppropriateStartFromResourceHandler()
  {
    return new StartExplorerFromResourceHandler();
  }
}
