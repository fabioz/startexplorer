package de.bastiankrol.startexplorer.popup.actions;

import java.io.File;
import java.util.List;

import de.bastiankrol.startexplorer.ResourceType;

/**
 * Handler for the command start system application from resource handler
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$
 */
public class StartSystemApplicationFromResourceHandler extends
    AbstractStartFromResourceHandler
{
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#getResourceType()
   */
  protected ResourceType getResourceType()
  {
    return ResourceType.FILE;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromResourceHandler#doActionForFileList(java.util.List)
   */
  @Override
  protected void doActionForFileList(List<File> fileList)
  {
    this.getRuntimeExecCalls().startWindowsSystemApplicationForFileList(
        fileList);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromResourceHandler#getAppropriateStartFromStringHandler()
   */
  @Override
  protected AbstractStartFromStringHandler getAppropriateStartFromStringHandler()
  {
    return new StartSystemApplicationFromStringHandler();
  }
}
