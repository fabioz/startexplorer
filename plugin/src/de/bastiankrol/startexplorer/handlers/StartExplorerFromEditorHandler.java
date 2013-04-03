package de.bastiankrol.startexplorer.handlers;

import de.bastiankrol.startexplorer.handlers.delegates.StartExplorerFromEditorHandlerDelegate;

/**
 * Examines the selected region in a text file, tries to interpret it as a
 * Windows filesystem path and opens a Windows Explorer in the location
 * described by that path.
 * 
 * @author Bastian Krol
 */
public class StartExplorerFromEditorHandler extends
    AbstractStartFromEditorHandler
{
  @Override
  StartExplorerFromEditorHandlerDelegate getDelegate()
  {
    return new StartExplorerFromEditorHandlerDelegate();
  }
}
