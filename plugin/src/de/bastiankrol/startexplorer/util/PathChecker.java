package de.bastiankrol.startexplorer.util;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import de.bastiankrol.startexplorer.ResourceType;

/**
 * Checks paths.
 * 
 * @author Bastian Krol
 */
public class PathChecker
{
  private IMessageDialogHelper messageDialogHelper;

  public PathChecker()
  {
    this.messageDialogHelper = new MessageDialogHelper();
  }

  void setMessageDialogHelper(IMessageDialogHelper messageDialogHelper)
  {
    this.messageDialogHelper = messageDialogHelper;
  }

  /**
   * Checks if the <code>pathString</code> is a valid filesystem path.
   * 
   * @param pathString a String meant to be a filesystem path
   * @param resourceType either ResourceType.FILE or ResourceType.DIRECTORY,
   *          depending on which resource type is expected or ResourceType.BOTH,
   *          if both resource types are acceptable
   * @param event the ExecutionEvent in which's context pathString occured
   * @return the absolute path of the file specified by <code>pathString</code>
   *         or <code>null</code> if <code>pathString</code> does not point to a
   *         valid file/directory.
   * @throws ExecutionException this method calls
   *           {@link org.eclipse.ui.handlers.HandlerUtil#getActiveShellChecked(ExecutionEvent)}
   *           with the given <code>event</code>, this method is declared to
   *           throw ExecutionException.
   */
  public File checkPath(String pathString, ResourceType resourceType,
      ExecutionEvent event) throws ExecutionException
  {
    if (pathString == null)
    {
      throw new IllegalArgumentException("pathString is null");
    }
    if (resourceType == null)
    {
      throw new IllegalArgumentException("resourceType is null");
    }
    if (event == null)
    {
      throw new IllegalArgumentException("event is null");
    }
    File file = new File(pathString);
    if (!file.exists())
    {
      File parentFile = file.getParentFile();
      if (parentFile == null)
      {
        this.messageDialogHelper
            .displayErrorMessage(
                "Resource does not exist",
                "The path "
                    + pathString
                    + " is not an existing file or folder and there is no parent folder available.",
                event);
        return null;
      }
      if (!parentFile.exists())
      {
        this.messageDialogHelper
            .displayErrorMessage(
                "Resource does not exist",
                "The path "
                    + pathString
                    + " is not an existing file or folder nor does its parent exist.",
                event);
        return null;
      }
      file = parentFile;
    }
    if (resourceType == ResourceType.DIRECTORY && !file.isDirectory())
    {
      File parentFile = file.getParentFile();
      if (parentFile == null)
      {
        this.messageDialogHelper
            .displayErrorMessage(
                "Not a directory",
                "The path "
                    + pathString
                    + " is a file, not a directory, and there is no parent folder available.",
                event);
        return null;
      }
      file = parentFile;
    }
    if (resourceType == ResourceType.FILE && !file.isFile())
    {
      this.messageDialogHelper.displayErrorMessage("Not a file", "The path "
          + pathString + " is a directory, not a file.", event);
      return null;
    }
    return file;
  }
}
