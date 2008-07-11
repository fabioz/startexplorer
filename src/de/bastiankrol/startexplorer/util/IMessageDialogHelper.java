package de.bastiankrol.startexplorer.util;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public interface IMessageDialogHelper
{

  /**
   * Shows a message dialog with an error message.
   * 
   * @param title
   *          the title of the dialog window
   * @param message
   *          the message of the dialog
   * @param event
   *          the ExecutionEvent which is the context in which the error occured
   */
  void displayErrorMessage(String title, String message, ExecutionEvent event)
      throws ExecutionException;

}