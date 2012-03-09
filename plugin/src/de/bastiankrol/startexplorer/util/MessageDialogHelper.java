package de.bastiankrol.startexplorer.util;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public class MessageDialogHelper
{
  private final static Shell SHELL;

  static
  {
    SHELL = new Shell();
  }

  /**
   * Shows a message dialog with an error message. If no ExecutionEvent is
   * available, {@link #displayErrorMessage(String, String)} can be used.
   * 
   * @param title the title of the dialog window
   * @param message the message of the dialog
   * @param event the ExecutionEvent which is the context in which the error
   *          occured
   * @throws ExecutionException If the active shell variable is not found.
   */
  public void displayErrorMessage(String title, String message,
      ExecutionEvent event) throws ExecutionException
  {
    MessageDialog.openError(HandlerUtil.getActiveShellChecked(event), title,
        message);
  }

  /**
   * Shows a message dialog with an error message. This variant can be used, if
   * no ExecutionEvent is available. If an ExecutionEvent is available,
   * {@link #displayErrorMessage(String, String, ExecutionEvent)} should be
   * used.
   * 
   * @param title the title of the dialog window
   * @param message the message of the dialog
   */
  public void displayErrorMessage(String title, String message)
  {
    MessageDialog.openError(SHELL, title, message);
  }

  /**
   * Shows a message dialog with an informational message.
   * 
   * @param title the title of the dialog window
   * @param message the message of the dialog
   * @param event the ExecutionEvent which is the context for the dialog
   */
  public void displayInformationMessage(String title, String message,
      ExecutionEvent event) throws ExecutionException
  {
    MessageDialog.openInformation(HandlerUtil.getActiveShellChecked(event),
        title, message);
  }

  /**
   * Shows a message dialog with an informational message.
   * 
   * @param title the title of the dialog window
   * @param message the message of the dialog
   */
  public void displayInformationMessage(String title, String message)
  {
    MessageDialog.openInformation(SHELL, title, message);
  }

  /**
   * Shows a message dialog with a question.
   * 
   * @param title the title of the dialog window
   * @param message the message of the dialog
   * @return {@code true} if the user presses the Yes button, {@code false}
   *         otherwise
   */
  public boolean displayQuestionDialog(String title, String message)
  {
    return MessageDialog.openQuestion(SHELL, title, message);
  }
}
