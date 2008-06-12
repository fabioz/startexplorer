package de.bastiankrol.startexplorer.popup.actions;

import java.util.List;

public class CopyResourcePathToClipboardHandler extends
AbstractStartFromResourceHandler
{
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#getResourceType()
   */
  protected PathCheck.ResourceType getResourceType()
  {
    return PathCheck.ResourceType.BOTH;
  }
  
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromResourceHandler#doActionForResources(java.util.List)
   */
  @Override
  protected void doActionForResources(List<String> pathList)
  {
    throw new RuntimeException("CopyResourcePathToClipboardHandler: Not implemented");
  }
}
