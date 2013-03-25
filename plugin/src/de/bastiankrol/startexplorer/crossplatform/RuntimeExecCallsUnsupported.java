package de.bastiankrol.startexplorer.crossplatform;

import java.io.File;
import java.util.List;

import de.bastiankrol.startexplorer.util.MessageDialogHelper;

/**
 * {@link IRuntimeExecCalls} implementation that just shows annoying popups
 * instead of doing anything useful.
 * 
 * @author Bastian Krol
 */
class RuntimeExecCallsUnsupported implements IRuntimeExecCalls
{
  private MessageDialogHelper messageDialogHelper;

  /**
   * Creates an instance.
   */
  RuntimeExecCallsUnsupported()
  {
    this.messageDialogHelper = new MessageDialogHelper();
  }

  public void startFileManagerForFileList(List<File> fileList,
      boolean selectFile)
  {
    this.annoyUser();
  }

  public void startSystemApplicationForFileList(List<File> fileList)
  {
    this.annoyUser();
  }

  public void startShellForFileList(List<File> fileList)
  {
    this.annoyUser();
  }

  public void startCustomCommandForFileList(String[] cmdArray,
      List<File> fileList)
  {
    this.annoyUser();
  }

  public void startFileManagerForFile(File file, boolean selectFile)
  {
    this.annoyUser();
  }

  public void startSystemApplicationForFile(File file)
  {
    this.annoyUser();
  }

  public void startShellForFile(File file)
  {
    this.annoyUser();
  }

  public void startCustomCommandForFile(String[] cmdArray, File file)
  {
    this.annoyUser();
  }

  public String[] convertCommandStringToArray(String command)
  {
    return new String[0];
  }

  public boolean isFileSelectionSupportedByFileManager()
  {
    return false;
  }

  private void annoyUser()
  {
    this.messageDialogHelper
        .displayErrorMessage(
            "Unsupported Desktop Environment",
            "The operating system or desktop environment you are using could not be automatically detected or is not supported. "
                + "Please set the correct operating system/desktop environment in the preferences or define a set of commands for "
                + "a custom desktop environment.");
  }
}
