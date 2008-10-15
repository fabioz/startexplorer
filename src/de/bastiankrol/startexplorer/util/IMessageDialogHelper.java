package de.bastiankrol.startexplorer.util;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

/**
 * Displays dialogs.
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$ $Author:$
 */
public interface IMessageDialogHelper
{

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
  void displayErrorMessage(String title, String message, ExecutionEvent event)
      throws ExecutionException;

  /**
   * Shows a message dialog with an error message. This variant can be used, if
   * no ExecutionEvent is available. If an ExecutionEvent is available,
   * {@link #displayErrorMessage(String, String, ExecutionEvent)} should be
   * used.
   * 
   * @param title the title of the dialog window
   * @param message the message of the dialog
   */
  void displayErrorMessage(String title, String message);

}