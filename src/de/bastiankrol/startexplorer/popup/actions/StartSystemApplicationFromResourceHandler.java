package de.bastiankrol.startexplorer.popup.actions;

import java.util.List;

import de.bastiankrol.startexplorer.RuntimeExecWrapper;

public class StartSystemApplicationFromResourceHandler extends
    AbstractStartFromResourceHandler
{
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#getResourceType()
   */
  protected PathCheck.ResourceType getResourceType()
  {
    return PathCheck.ResourceType.FILE;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromResourceHandler#doActionForResources(java.util.List)
   */
  @Override
  protected void doActionForResources(List<String> pathList)
  {
    RuntimeExecWrapper.startWindowsSystemApplicationForPathList(pathList);
  }
}
