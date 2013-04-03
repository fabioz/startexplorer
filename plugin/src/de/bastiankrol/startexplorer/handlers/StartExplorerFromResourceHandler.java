package de.bastiankrol.startexplorer.handlers;

import de.bastiankrol.startexplorer.handlers.delegates.StartExplorerFromResourceHandlerDelegate;

/**
 * Examines the selection in the package explorer/navigator and opens a Windows
 * Explorer for all selected files/folders.
 * 
 * @author Bastian Krol
 */
public class StartExplorerFromResourceHandler extends
    AbstractStartFromResourceHandler
{
  @Override
  StartExplorerFromResourceHandlerDelegate getDelegate()
  {
    return new StartExplorerFromResourceHandlerDelegate();
  }
}
