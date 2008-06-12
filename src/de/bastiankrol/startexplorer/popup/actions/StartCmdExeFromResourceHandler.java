package de.bastiankrol.startexplorer.popup.actions;

import java.util.List;

import de.bastiankrol.startexplorer.RuntimeExecWrapper;

public class StartCmdExeFromResourceHandler extends
AbstractStartFromResourceHandler
{
  
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#getResourceType()
   */
  protected PathCheck.ResourceType getResourceType()
  {
    return PathCheck.ResourceType.DIRECTORY;
  }
  
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromResourceHandler#doActionForResources(java.util.List)
   */
  @Override
  protected void doActionForResources(List<String> pathList)
  {
    RuntimeExecWrapper.startCmdExeForPathList(pathList);
  }
}
