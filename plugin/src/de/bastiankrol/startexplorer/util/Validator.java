package de.bastiankrol.startexplorer.util;

import static de.bastiankrol.startexplorer.Activator.getPluginContext;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import de.bastiankrol.startexplorer.ResourceType;

/**
 * Checks paths, URLs and stuff.
 * 
 * @author Bastian Krol
 */
public class Validator
{
  private MessageDialogHelper messageDialogHelper;

  public enum Reason
  {
    RESOURCE_DOES_NOT_EXIST, NOT_A_DIRECTORY, NOT_A_FILE;
  }

  public class MaybeFile
  {
    public File file;
    public Reason reason;

    private MaybeFile(File file)
    {
      this.file = file;
    }

    private MaybeFile(Reason reason)
    {
      this.reason = reason;
    }
  }

  public Validator()
  {
    this.messageDialogHelper = new MessageDialogHelper();
  }

  void setMessageDialogHelper(MessageDialogHelper messageDialogHelper)
  {
    this.messageDialogHelper = messageDialogHelper;
  }

  /**
   * Checks if the <code>pathString</code> is a valid filesystem path, if not, a
   * message dialog is shown.
   * 
   * @param pathString a String meant to be a filesystem path
   * @param resourceType either ResourceType.FILE or ResourceType.DIRECTORY,
   *          depending on which resource type is expected or ResourceType.BOTH,
   *          if both resource types are acceptable
   * @param event the ExecutionEvent in which's context pathString occured
   * @throws ExecutionException this method calls
   *           {@link org.eclipse.ui.handlers.HandlerUtil#getActiveShellChecked(ExecutionEvent)}
   *           with the given <code>event</code>, this method is declared to
   *           throw ExecutionException.
   * @return the file object specified by <code>pathString</code> or {@link
   *         null}, if <code>pathString</code> does not point to a valid
   *         file/directory.
   */
  public File checkPathAndShowMessage(String pathString,
      ResourceType resourceType, ExecutionEvent event)
      throws ExecutionException
  {
    MaybeFile maybeFile = this.checkPath(pathString, resourceType, null);
    if (maybeFile.file != null)
    {
      return maybeFile.file;
    }
    else
    {
      this.showMessageFor(maybeFile.reason, pathString, event);
      return null;
    }
  }

  /**
   * Checks if the <code>pathString</code> is a valid filesystem path.
   * 
   * @param pathString a String meant to be a filesystem path
   * @param resourceType either ResourceType.FILE or ResourceType.DIRECTORY,
   *          depending on which resource type is expected or ResourceType.BOTH,
   *          if both resource types are acceptable
   * @param fileOpenedInEditor the file that is currently open in the editor, if
   *          any - used to resolve relative paths
   * @return the absolute path of the file specified by <code>pathString</code>
   *         or a {@link Reason}, if <code>pathString</code> does not point to a
   *         valid file/directory.
   */
  public MaybeFile checkPath(String pathString, ResourceType resourceType,
      File fileOpenedInEditor)
  {
    if (pathString == null)
    {
      throw new IllegalArgumentException("pathString is null");
    }
    if (resourceType == null)
    {
      throw new IllegalArgumentException("resourceType is null");
    }
    File file = new File(pathString);
    if (!file.isAbsolute() && fileOpenedInEditor != null)
    {
      try
      {
        file = absolutize(fileOpenedInEditor.getParentFile(), file);
      }
      catch (IOException e)
      {
        // That should not happen under normal circumstances. If it does, ignore
        // it and work with the non-absolute path
        getPluginContext()
            .getLogFacility()
            .logException(
                "Could not resolve "
                    + file.getPath()
                    + " to an absolute path relative to the file that is currently opened in the editor ("
                    + fileOpenedInEditor.getPath() + ").", e);
      }
    }
    if (!file.exists())
    {
      File parentFile = file.getParentFile();
      if (parentFile == null)
      {
        return new MaybeFile(Reason.RESOURCE_DOES_NOT_EXIST);
      }
      else if (!parentFile.exists())
      {
        return new MaybeFile(Reason.RESOURCE_DOES_NOT_EXIST);
      }
      file = parentFile;
    }
    if (resourceType == ResourceType.DIRECTORY && !file.isDirectory())
    {
      File parentFile = file.getParentFile();
      if (parentFile == null)
      {
        return new MaybeFile(Reason.NOT_A_DIRECTORY);
      }
      else if (!parentFile.exists())
      {
        return new MaybeFile(Reason.NOT_A_DIRECTORY);
      }
      file = parentFile;
    }
    if (resourceType == ResourceType.FILE && !file.isFile())
    {
      return new MaybeFile(Reason.NOT_A_FILE);
    }
    return new MaybeFile(file);
  }

  private File absolutize(File base, File relativePath) throws IOException
  {
    if (base == null){
      throw new NullPointerException(
          "Can't create absolute path because base is null.");
    }
    if (relativePath == null)
    {
      throw new NullPointerException(
          "Can't create absolute path because relativePath is null.");
    }
    String relative = relativePath.getPath();
    relative = relative.replace("\\", "/");
    return new File(base, relative).getCanonicalFile();
  }

  /**
   * Shows a message dialog for the given reason.
   * 
   * @param reason the reason
   * @param pathString the path string that is not a valid file system resource
   * @param event the ExecutionEvent in which's context pathString occured
   * @throws ExecutionException this method calls
   *           {@link org.eclipse.ui.handlers.HandlerUtil#getActiveShellChecked(ExecutionEvent)}
   *           with the given <code>event</code>, this method is declared to
   *           throw ExecutionException.
   */
  public void showMessageFor(Reason reason, String pathString,
      ExecutionEvent event) throws ExecutionException
  {
    if (reason == null)
    {
      throw new IllegalArgumentException("reason is null");
    }
    if (pathString == null)
    {
      throw new IllegalArgumentException("pathString is null");
    }
    if (event == null)
    {
      throw new IllegalArgumentException("event is null");
    }
    switch (reason)
    {
      case RESOURCE_DOES_NOT_EXIST:
        this.messageDialogHelper
            .displayErrorMessage(
                "Resource does not exist",
                "The path "
                    + pathString
                    + " is not an existing file or folder and there is no parent folder available.",
                event);
        break;
      case NOT_A_DIRECTORY:
        this.messageDialogHelper
            .displayErrorMessage(
                "Not a directory",
                "The path "
                    + pathString
                    + " is a file, not a directory, and there is no parent folder available.",
                event);
        break;
      case NOT_A_FILE:
        this.messageDialogHelper.displayErrorMessage("Not a file", "The path "
            + pathString + " is a directory, not a file.", event);
        break;
      default:
        throw new IllegalArgumentException("Unknown reason: " + reason);
    }
  }

  /**
   * Checks if the <code>pathString</code> is a valid URL.
   * 
   * @param urlString a String meant to be a URL
   * @param event the ExecutionEvent in which's context pathString occured
   * @return the URL object specified by <code>urlString</code> or
   *         <code>null</code> if <code>urlString</code> does not represent a
   *         valid URL.
   */
  public URL checkUrl(String urlString, ExecutionEvent event)
  {
    if (urlString == null)
    {
      throw new IllegalArgumentException("pathString is null");
    }

    try
    {
      return new URL(urlString);
    }
    catch (MalformedURLException e)
    {
      getPluginContext().getLogFacility().logDebug(
          urlString + " could not be parsed to a URL.");
      return null;
    }
    catch (Exception e)
    {
      getPluginContext().getLogFacility().logException(
          "Unexpected exception when trying to parse " + urlString
              + " to a URL.", e);
      return null;
    }
  }
}
