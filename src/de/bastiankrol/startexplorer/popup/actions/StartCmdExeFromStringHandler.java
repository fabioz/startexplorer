package de.bastiankrol.startexplorer.popup.actions;

import java.io.File;

import de.bastiankrol.startexplorer.ResourceType;

/**
 * Examines the selected region in a text file, tries to interpret it as a
 * Windows filesystem path and tries to start cmd.exe for the directory
 * described by that path.
 * 
 * @author Bastian Krol
 */
public class StartCmdExeFromStringHandler extends
    AbstractStartFromStringHandler
{
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#getResourceType()
   */
  protected ResourceType getResourceType()
  {
    return ResourceType.DIRECTORY;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#doActionForFile(java.io.File)
   */
  protected void doActionForFile(File file)
  {
    this.getRuntimeExecCalls().startCmdExeForFile(file);
  }
  
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#getAppropriateStartFromResourceHandler()
   */
  @Override
  protected AbstractStartFromResourceHandler getAppropriateStartFromResourceHandler()
  {
    return new StartCmdExeFromResourceHandler();
  }
}
