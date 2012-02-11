package de.bastiankrol.startexplorer.popup.actions;

import java.io.File;

import de.bastiankrol.startexplorer.util.PathChecker;

/**
 * Examines the selected region in a text file, tries to interpret it as a
 * Windows filesystem path and tries to start the default windows system
 * application for the file described by that path.
 * 
 * @author Bastian Krol
 */
public class StartSystemApplicationFromStringHandler extends
    AbstractStartFromStringHandler
{
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#getResourceType()
   */
  protected PathChecker.ResourceType getResourceType()
  {
    return PathChecker.ResourceType.FILE;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#doActionForFile(java.io.File)
   */
  protected void doActionForFile(File file)
  {
    this.getRuntimeExecCalls().startWindowsSystemApplicationForFile(file);
  }
  
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#getAppropriateStartFromResourceHandler()
   */
  @Override
  protected AbstractStartFromResourceHandler getAppropriateStartFromResourceHandler()
  {
    return new StartSystemApplicationFromResourceHandler();
  }
}
