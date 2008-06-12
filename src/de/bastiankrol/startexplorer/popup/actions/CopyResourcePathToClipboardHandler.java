package de.bastiankrol.startexplorer.popup.actions;

import java.util.List;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

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
    if (pathList.isEmpty())
    {
      return;
    }
    StringBuffer clipboardContentBuffer = new StringBuffer();
    for (String path : pathList)
    {
      clipboardContentBuffer.append(path);
      
      // TODO Make this configurable via preference dialog, also allow line break
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
