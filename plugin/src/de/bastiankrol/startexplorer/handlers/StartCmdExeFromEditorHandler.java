package de.bastiankrol.startexplorer.handlers;

import java.io.File;

import de.bastiankrol.startexplorer.ResourceType;

/**
 * Examines the selected region in a text file, tries to interpret it as a
 * Windows filesystem path and tries to start cmd.exe for the directory
 * described by that path.
 * 
 * @author Bastian Krol
 */
public class StartCmdExeFromEditorHandler extends
    AbstractStartFromEditorHandler
{
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromEditorHandler#getResourceType()
   */
  protected ResourceType getResourceType()
  {
    return ResourceType.DIRECTORY;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromEditorHandler#doActionForFile(java.io.File)
   */
  protected void doActionForFile(File file)
  {
    this.getRuntimeExecCalls().startShellForFile(file);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromEditorHandler#getAppropriateStartFromResourceHandler()
   */
  @Override
  protected AbstractStartFromResourceHandler getAppropriateStartFromResourceHandler()
  {
    return new StartCmdExeFromResourceHandler();

  }
}
