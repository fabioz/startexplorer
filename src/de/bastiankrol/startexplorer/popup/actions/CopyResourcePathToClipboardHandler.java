package de.bastiankrol.startexplorer.popup.actions;

import java.io.File;
import java.util.List;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

import de.bastiankrol.startexplorer.util.PathChecker;

public class CopyResourcePathToClipboardHandler extends
    AbstractStartFromResourceHandler
{
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromStringHandler#getResourceType()
   */
  protected PathChecker.ResourceType getResourceType()
  {
    return PathChecker.ResourceType.BOTH;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromResourceHandler#doActionForFileList(java.util.List)
   */
  @Override
  protected void doActionForFileList(List<File> fileList)
  {
    if (fileList.isEmpty())
    {
      return;
    }
    StringBuffer clipboardContentBuffer = new StringBuffer();
    for (File file : fileList)
    {
      clipboardContentBuffer.append(file.getAbsolutePath());

      // TODO Make this configurable via preference dialog, also allow line
      // break
      clipboardContentBuffer.append(", ");
    }
    String clipboardContent = clipboardContentBuffer.substring(0,
        clipboardContentBuffer.length() - 2);

    Display display = Display.getDefault();
    Clipboard clipboard = new Clipboard(display);
    TextTransfer textTransfer = TextTransfer.getInstance();
    clipboard.setContents(new Object[] { clipboardContent },
        new Transfer[] { textTransfer });
    clipboard.dispose();
  }
}
