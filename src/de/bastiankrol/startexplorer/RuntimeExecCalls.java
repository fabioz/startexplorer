package de.bastiankrol.startexplorer;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.IStatus;

/**
 * Provides preconfigured calls to {@link java.lang.Runtime#exec(String)}.
 * Offers two services:
 * <ul>
 * <li>Opening one or several Windows Explorer windows for a certain path in
 * the filesystem,</li>
 * <li>starting the default windows application for a file and</li>
 * <li>starting a command prompt for a path.</li>
 * </ul>
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$ $Author:$
 */
public class RuntimeExecCalls
{
  public static final String VAR_BEGIN = "${";
  public static final String VAR_END = "}";
  private static final String RESOURCE_PATH = "resource_path";
  private static final String RESOURCE_PARENT = "resource_parent";
  private static final String RESOURCE_NAME = "resource_name";

  public static final String RESOURCE_PATH_VAR = VAR_BEGIN + RESOURCE_PATH
      + VAR_END;
  public static final String RESOURCE_PARENT_VAR = VAR_BEGIN + RESOURCE_PARENT
      + VAR_END;
  public static final String RESOURCE_NAME_VAR = VAR_BEGIN + RESOURCE_NAME
      + VAR_END;

  private IRuntimeExecDelegate runtimeExecDelegate;

  /**
   * Creates a new RuntimeExecCalls instance.
   */
  RuntimeExecCalls()
  {
    this.runtimeExecDelegate = new RuntimeExecDelegate();
  }

  /**
   * Sets the IRuntimeExecDelegate which executes the calls to
   * {@link Runtime#exec(String)}.
   * 
   * @param runtimeExecDelegate
   *          the {@link IRuntimeExecDelegate}
   */
  public void setRuntimeExecDelegate(IRuntimeExecDelegate runtimeExecDelegate)
  {
    this.runtimeExecDelegate = runtimeExecDelegate;
  }

  /**
   * Starts the windows explorer for the paths in the list.
   * 
   * @param fileList
   *          the list of File objects to start a windows explorer for.
   */
  public void startWindowsExplorerForFileList(List<File> fileList)
  {
    for (File file : fileList)
    {
      this.startWindowsExplorerForFile(file);
    }
  }

  /**
   * Starts a windows system application for the paths in the list.
   * 
   * @param fileList
   *          the list of File objects to start a windows system application
   *          for.
   */
  public void startWindowsSystemApplicationForFileList(List<File> fileList)
  {
    for (File file : fileList)
    {
      this.startWindowsSystemApplicationForFile(file);
    }
  }

  /**
   * Starts a command prompt for the paths in the list.
   * 
   * @param fileList
   *          the list of File objects to start a cmd.exe in.
   */
  public void startCmdExeForFileList(List<File> fileList)
  {
    for (File file : fileList)
    {
      this.startCmdExeForFile(file);
    }
  }

  /**
   * Starts a custom command, defined by user preferences, for the given list of
   * files.
   * 
   * @param customCommand
   *          the custom command to execute
   * @param fileList
   *          the list of File objects to execute the custom command for
   */
  public void startCustomCommandForFileList(String customCommand,
      List<File> fileList)
  {
    for (File file : fileList)
    {
      this.startCustomCommandForFile(customCommand, file);
    }
  }

  /**
   * Starts the windows explorer for the given path.
   * 
   * @param file
   *          the File to start a windows explorer for.
   */
  public void startWindowsExplorerForFile(File file)
  {
    String execCommandString = "Explorer.exe /e," + file.getAbsolutePath();
    this.runtimeExecDelegate.exec(execCommandString);
  }

  /**
   * Starts a windows system application for the file given by <code>file</code>.
   * This is pretty much the same as &quot;Open With - System Editor&quot;.
   * 
   * @param file
   *          the File to start a windows system application for.
   */
  public void startWindowsSystemApplicationForFile(File file)
  {
    String execCommandString = "cmd.exe /c " + file.getAbsolutePath();
    this.runtimeExecDelegate.exec(execCommandString);
  }

  /**
   * Starts a command prompt for the file given by <code>file</code>.
   * 
   * @param file
   *          the File representing the path to start a cmd.exe in.
   */
  public void startCmdExeForFile(File file)
  {
    String execCommandString = "cmd.exe /c start /d " + file.getAbsolutePath();
    this.runtimeExecDelegate.exec(execCommandString);
  }

  /**
   * Starts a custom command, defined by user preferences, for the given file.
   * 
   * @param customCommand
   *          the custom command to execute
   * @param file
   *          the File
   */
  public void startCustomCommandForFile(String customCommand, File file)
  {
    String path = file.getAbsolutePath();
    customCommand = customCommand.replace(RESOURCE_PATH_VAR, path);
    String name = file.getName();
    customCommand = customCommand.replace(RESOURCE_NAME_VAR, name);
    File parent = file.getParentFile();
    String parentPath;
    if (parent != null)
    {
      parentPath = parent.getAbsolutePath();
      customCommand = customCommand.replace(RESOURCE_PARENT_VAR, parentPath);
    }
    else if (customCommand.contains(RESOURCE_PARENT_VAR))
    {
      Activator.logMessage(IStatus.WARNING,
          "The custom command contains the variable " + RESOURCE_PARENT_VAR
              + " but the file " + file.getAbsolutePath() + "has no parent.");
    }
    this.runtimeExecDelegate.exec(customCommand);
  }
}
