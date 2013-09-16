package de.bastiankrol.startexplorer.crossplatform;

import java.io.File;
import java.net.URL;
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

  private final Capabilities capabilities = Capabilities.create()
      .build();

  private MessageDialogHelper messageDialogHelper;

  /**
   * Creates an instance.
   */
  RuntimeExecCallsUnsupported()
  {
    this.messageDialogHelper = new MessageDialogHelper();
  }

  @Override
  public void startFileManagerForFileList(List<File> fileList,
      boolean selectFile)
  {
    this.annoyUser();
  }

  @Override
  public void startSystemApplicationForFileList(List<File> fileList)
  {
    this.annoyUser();
  }

  @Override
  public void startShellForFileList(List<File> fileList)
  {
    this.annoyUser();
  }

  @Override
  public void startCustomCommandForFileList(String[] cmdArray,
      List<File> fileList)
  {
    this.annoyUser();
  }

  @Override
  public void startFileManagerForFile(File file, boolean selectFile)
  {
    this.annoyUser();
  }

  @Override
  public void startFileManagerForUrl(URL url)
  {
    this.annoyUser();

  }

  @Override
  public void startSystemApplicationForFile(File file)
  {
    this.annoyUser();
  }

  @Override
  public void startSystemApplicationForUrl(URL url)
  {
    this.annoyUser();
  }

  @Override
  public void startShellForFile(File file)
  {
    this.annoyUser();
  }

  @Override
  public void startCustomCommandForFile(String[] cmdArray, File file)
  {
    this.annoyUser();
  }

  @Override
  public String[] convertCommandStringToArray(String command)
  {
    return new String[0];
  }

  @Override
  public Capabilities getCapabilities()
  {
    return capabilities;
  }

  private void annoyUser()
  {
    this.messageDialogHelper
        .displayErrorMessage(
            "Please configure Your Desktop Environment",
            "The operating system or desktop environment you are using could not be automatically detected or is not supported "
                + "out of the box. Please set the correct operating system/desktop environment in the preferences or define a "
                + "set of commands for a your desktop environment.");
  }
}
