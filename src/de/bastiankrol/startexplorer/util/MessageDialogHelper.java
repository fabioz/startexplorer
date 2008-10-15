package de.bastiankrol.startexplorer.util;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public class MessageDialogHelper implements IMessageDialogHelper
{
  private final static Shell SHELL;

  static
  {
    SHELL = new Shell();
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.util.IMessageDialogHelper#displayErrorMessage(java.lang.String,
   *      java.lang.String, org.eclipse.core.commands.ExecutionEvent)
   */
  public void displayErrorMessage(String title, String message,
      ExecutionEvent event) throws ExecutionException
  {
    MessageDialog.openError(HandlerUtil.getActiveShellChecked(event), title,
        message);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.util.IMessageDialogHelper#displayErrorMessage(java.lang.String,
   *      java.lang.String)
   */
  public void displayErrorMessage(String title, String message)
  {
    MessageDialog.openError(SHELL, title, message);
  }
}
