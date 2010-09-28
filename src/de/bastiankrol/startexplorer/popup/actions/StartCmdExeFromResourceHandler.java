package de.bastiankrol.startexplorer.popup.actions;

import java.io.File;
import java.util.List;

import de.bastiankrol.startexplorer.util.PathChecker;

/**
 * Handler for the command start cmd.exe from resource
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$
 */
public class StartCmdExeFromResourceHandler extends
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
    this.getRuntimeExecCalls().startCmdExeOrShellForFileList(fileList);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromResourceHandler#getAppropriateStartFromStringHandler()
   */
  @Override
  protected AbstractStartFromStringHandler getAppropriateStartFromStringHandler()
  {
    return new StartCmdExeFromStringHandler();
  }
}
