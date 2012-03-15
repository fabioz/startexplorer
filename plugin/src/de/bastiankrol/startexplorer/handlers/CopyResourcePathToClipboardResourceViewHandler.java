package de.bastiankrol.startexplorer.handlers;

import java.io.File;
import java.util.List;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

import de.bastiankrol.startexplorer.ResourceType;

/**
 * Handler for the command copy resource path to clipboard
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$
 */
public class CopyResourcePathToClipboardResourceViewHandler extends
    AbstractStartFromResourceHandler
{

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromEditorHandler#getResourceType()
   */
  protected ResourceType getResourceType()
  {
    return ResourceType.BOTH;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromResourceHandler#doActionForFileList(java.util.List)
   */
  @Override
  protected void doActionForFileList(List<File> fileList)
  {
    if (fileList.isEmpty())
    {
      return;
    }
    StringBuffer clipboardContentBuffer = new StringBuffer();
    String copyResourcePathSeparator = this.getPreferenceModel()
        .getCopyResourcePathSeparatorStringFromPreferences();
    for (File file : fileList)
    {
      clipboardContentBuffer.append(file.getAbsolutePath());
      clipboardContentBuffer.append(copyResourcePathSeparator);
    }

    // clip last separator
    String clipboardContent = clipboardContentBuffer.substring(0,
        clipboardContentBuffer.length() - copyResourcePathSeparator.length());

    Display display = Display.getDefault();
    Clipboard clipboard = new Clipboard(display);
    TextTransfer textTransfer = TextTransfer.getInstance();
    clipboard.setContents(new Object[] { clipboardContent },
        new Transfer[] { textTransfer });
    clipboard.dispose();
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromResourceHandler#getAppropriateStartFromStringHandler()
   */
  @Override
  protected AbstractStartFromEditorHandler getAppropriateStartFromStringHandler()
  {
    return new CopyResourcePathToClipboardEditorHandler();
  }
}
