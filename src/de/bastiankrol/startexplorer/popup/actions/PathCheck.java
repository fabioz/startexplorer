package de.bastiankrol.startexplorer.popup.actions;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Checks paths.
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$ $Author:$
 */
public class PathCheck
{
  /**
   * Type of a filesystem resource, either file or directory.
   */
  public enum ResourceType
  {
    /**
     * Resource of type file (in contrast to directory).
     */
    FILE,

    /**
     * Resource of type directory (in contrast to file).
     */
    DIRECTORY
  }

  /**
   * Checks if the <code>pathString</code> is a valid filesystem path.
   * 
   * @param pathString a String meant to be a filesystem path
   * @param event the ExecutionEvent in which's context pathString occured
   * @param resourceType either ResourceType.FILE or ResourceType.DIRECTORY,
   *          depending on which resource type is expected
   * @return the absolute path of the file specified by <code>pathString</code>
   *         or <code>null</code> if <code>pathString</code> does not point
   *         to a valid file/directory.
   * @throws ExecutionException this method calls {@link 
   *           org.eclipse.ui.handlers.HandlerUtil#getActiveShellChecked(ExecutionEvent)}
   *           with the given <code>event</code>, this method is declared to
   *           throw ExecutionException.
   */
  public static String checkPath(String pathString, ExecutionEvent event,
      ResourceType resourceType) throws ExecutionException
  {
    if (pathString == null)
    {
      throw new IllegalArgumentException("pathString is null");
    }
    if (event == null)
    {
      throw new IllegalArgumentException("event is null");
    }
    if (resourceType == null)
    {
      throw new IllegalArgumentException("resourceType is null");
    }
    File file = new File(pathString);
    if (!file.exists())
    {
      File parentFile = file.getParentFile();
      if (parentFile == null)
      {
        MessageDialog
            .openError(
                HandlerUtil.getActiveShellChecked(event),
                "Resource does not exist",
                "The path "
                    + pathString
                    + " does not point to an existing file or folder and there is no parent folder available.");
        return null;
      }
      if (!parentFile.exists())
      {
        MessageDialog
            .openError(
                HandlerUtil.getActiveShellChecked(event),
                "Resource does not exist",
                "The path "
                    + pathString
                    + " does not point to an existing file or folder nor does it's parent.");
        return null;
      }
      file = parentFile;
    }
    if (resourceType == ResourceType.DIRECTORY && !file.isDirectory())
    {
      File parentFile = file.getParentFile();
      if (parentFile == null)
      {
        MessageDialog
            .openError(
                HandlerUtil.getActiveShellChecked(event),
                "Not a directory",
                "The path "
                    + pathString
                    + " points to a file (not a directory) and there is no parent folder available.");
        return null;
      }
      file = parentFile;
    }
    if (resourceType == ResourceType.FILE && !file.isFile())
    {
      MessageDialog.openError(HandlerUtil.getActiveShellChecked(event),
          "Not a file", "The path " + pathString
              + " points to a directory, not to a file.");
      return null;
    }
    return file.getAbsolutePath();
  }
}
