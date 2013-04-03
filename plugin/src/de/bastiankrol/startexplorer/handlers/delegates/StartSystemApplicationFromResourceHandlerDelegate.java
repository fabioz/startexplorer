package de.bastiankrol.startexplorer.handlers.delegates;

import java.io.File;
import java.util.List;

import de.bastiankrol.startexplorer.ResourceType;

/**
 * Handler for the command start system application from resource handler
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$
 */
public class StartSystemApplicationFromResourceHandlerDelegate extends
    AbstractStartFromResourceHandlerDelegate
{
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromEditorHandler#getResourceType()
   */
  protected ResourceType getResourceType()
  {
    return ResourceType.FILE;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromResourceHandler#doActionForFileList(java.util.List)
   */
  @Override
  protected void doActionForFileList(List<File> fileList)
  {
    this.getRuntimeExecCalls().startSystemApplicationForFileList(fileList);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromResourceHandler#getAppropriateStartFromStringHandler()
   */
  @Override
  protected AbstractStartFromEditorHandlerDelegate getAppropriateStartFromEditorHandlerDelegate()
  {
    return new StartSystemApplicationFromEditorHandlerDelegate();
  }
}
