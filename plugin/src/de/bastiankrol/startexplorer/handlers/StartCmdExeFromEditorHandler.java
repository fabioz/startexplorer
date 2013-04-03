package de.bastiankrol.startexplorer.handlers;

import de.bastiankrol.startexplorer.handlers.delegates.StartCmdExeFromEditorHandlerDelegate;

/**
 * Examines the selected region in a text file, tries to interpret it as a
 * Windows filesystem path and tries to start cmd.exe for the directory
 * described by that path.
 * 
 * @author Bastian Krol
 */
public class StartCmdExeFromEditorHandler extends
    AbstractStartFromEditorHandler
{
  @Override
  StartCmdExeFromEditorHandlerDelegate getDelegate()
  {
    return new StartCmdExeFromEditorHandlerDelegate();
  }
}
