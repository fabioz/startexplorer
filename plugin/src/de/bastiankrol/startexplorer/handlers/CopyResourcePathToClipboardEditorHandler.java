package de.bastiankrol.startexplorer.handlers;

import java.io.File;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

import de.bastiankrol.startexplorer.ResourceType;

public class CopyResourcePathToClipboardEditorHandler extends
    AbstractStartFromEditorHandler
{

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromEditorHandler#getResourceType()
   */
  @Override
  protected ResourceType getResourceType()
  {
    return ResourceType.BOTH;
  }

  @Override
  protected boolean alwaysUseFileOpenedInEditor()
  {
    return true;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromEditorHandler#doActionForFile(java.io.File)
   */
  @Override
  protected void doActionForFile(File file)
  {
    Display display = Display.getDefault();
    Clipboard clipboard = new Clipboard(display);
    TextTransfer textTransfer = TextTransfer.getInstance();
    clipboard.setContents(new Object[] { file.getAbsolutePath() },
        new Transfer[] { textTransfer });
    clipboard.dispose();
    System.out.println(file.getAbsolutePath());
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromEditorHandler#getAppropriateStartFromResourceHandler()
   */
  @Override
  AbstractStartFromResourceHandler getAppropriateStartFromResourceHandler()
  {
    return new CopyResourcePathToClipboardResourceViewHandler();
  }
}
