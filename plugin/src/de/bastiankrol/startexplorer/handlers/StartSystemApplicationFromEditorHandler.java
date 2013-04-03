package de.bastiankrol.startexplorer.handlers;

import de.bastiankrol.startexplorer.handlers.delegates.StartSystemApplicationFromEditorHandlerDelegate;

/**
 * Examines the selected region in a text file, tries to interpret it as a
 * Windows filesystem path and tries to start the default windows system
 * application for the file described by that path.
 * 
 * @author Bastian Krol
 */
public class StartSystemApplicationFromEditorHandler extends
    AbstractStartFromEditorHandler
{
  @Override
  StartSystemApplicationFromEditorHandlerDelegate getDelegate()
  {
    return new StartSystemApplicationFromEditorHandlerDelegate();
  }
}
