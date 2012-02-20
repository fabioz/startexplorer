package de.bastiankrol.startexplorer.popup.actions;

import java.io.File;

import de.bastiankrol.startexplorer.ResourceType;

/**
 * Examines the selected region in a text file, tries to interpret it as a
 * Windows filesystem path and tries to start the default windows system
 * application for the file described by that path.
 * 
 * @author Bastian Krol
 */
public class StartSystemApplicationFromEditorHandler extends
    AbstractStartFromEditorHandler
{
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromEditorHandler#getResourceType()
   */
  protected ResourceType getResourceType()
  {
    return ResourceType.FILE;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromEditorHandler#doActionForFile(java.io.File)
   */
  protected void doActionForFile(File file)
  {
    this.getRuntimeExecCalls().startSystemApplicationForFile(file);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromEditorHandler#getAppropriateStartFromResourceHandler()
   */
  @Override
  protected AbstractStartFromResourceHandler getAppropriateStartFromResourceHandler()
  {
    return new StartSystemApplicationFromResourceHandler();
  }
}
