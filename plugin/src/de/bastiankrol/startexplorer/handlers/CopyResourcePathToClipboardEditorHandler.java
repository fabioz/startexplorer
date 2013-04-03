package de.bastiankrol.startexplorer.handlers;

import de.bastiankrol.startexplorer.handlers.delegates.CopyResourcePathToClipboardEditorHandlerDelegate;

public class CopyResourcePathToClipboardEditorHandler extends
    AbstractStartFromEditorHandler
{

  @Override
  CopyResourcePathToClipboardEditorHandlerDelegate getDelegate()
  {
    return new CopyResourcePathToClipboardEditorHandlerDelegate();
  }
}
