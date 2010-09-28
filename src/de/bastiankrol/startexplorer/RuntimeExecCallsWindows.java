package de.bastiankrol.startexplorer;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.IStatus;

/**
 * Provides preconfigured calls to {@link java.lang.Runtime#exec(String)}.
 * Offers the following services:
 * <ul>
 * <li>Opening one or several file manager windows (Windows Explorer or Linux
 * equivalents, like Nautilus or Konqueror) for a given path in the filesystem,</li>
 * <li>starting the default system application for a file,</li>
 * <li>starting a command prompt/shell for a path and</li>
 * <li>starting a custom command for a path.</li>
 * </ul>
 * 
 * All services are also available for lists of paths.
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$ $Author:$
 */
public class RuntimeExecCallsWindows implements IRuntimeExecCalls
{
  /**
   * prefix for variables
   */
  public static final String VAR_BEGIN = "${";

  /**
   * suffix for variables
   */
  public static final String VAR_END = "}";

  private static final String RESOURCE_PATH = "resource_path";
  private static final String RESOURCE_PARENT = "resource_parent";
  private static final String RESOURCE_NAME = "resource_name";

  /**
   * variable for resource path
   */
  public static final String RESOURCE_PATH_VAR = VAR_BEGIN + RESOURCE_PATH
      + VAR_END;

  /**
   * variable for resource parent
   */
  public static final String RESOURCE_PARENT_VAR = VAR_BEGIN + RESOURCE_PARENT
      + VAR_END;

  /**
   * variable for resource name
   */
  public static final String RESOURCE_NAME_VAR = VAR_BEGIN + RESOURCE_NAME
      + VAR_END;

  private IRuntimeExecDelegate runtimeExecDelegate;

  /**
   * Creates a new RuntimeExecCalls instance.
   */
  RuntimeExecCallsWindows()
  {
    this.runtimeExecDelegate = new RuntimeExecDelegate();
  }

  /**
   * Sets the IRuntimeExecDelegate which executes the calls to
   * {@link Runtime#exec(String)}.
   * 
   * @param runtimeExecDelegate the {@link IRuntimeExecDelegate}
   */
  public void setRuntimeExecDelegate(IRuntimeExecDelegate runtimeExecDelegate)
  {
    this.runtimeExecDelegate = runtimeExecDelegate;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.IRuntimeExecCalls#startFileManagerForFileList
   *      (java.util.List)
   */
  public void startFileManagerForFileList(List<File> fileList)
  {
    for (File file : fileList)
    {
      this.startFileManagerForFile(file);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @seede.bastiankrol.startexplorer.IRuntimeExecCalls# 
   *                                                     startSystemApplicationForFileList
   *                                                     (java.util.List)
   */
  public void startSystemApplicationForFileList(List<File> fileList)
  {
    for (File file : fileList)
    {
      this.startSystemApplicationForFile(file);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.IRuntimeExecCalls#startCmdExeOrShellForFileList
   *      (java.util.List)
   */
  public void startCmdExeOrShellForFileList(List<File> fileList)
  {
    for (File file : fileList)
    {
      this.startCmdExeOrShellForFile(file);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.IRuntimeExecCalls#startCustomCommandForFileList
   *      (java.lang.String, java.util.List)
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
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.IRuntimeExecCalls#startFileManagerForFile
   *      (java.io.File)
   */
  public void startFileManagerForFile(File file)
  {
    String execCommandString = "Explorer.exe /e," + getWrappedPath(file);
    this.runtimeExecDelegate.exec(execCommandString);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.IRuntimeExecCalls#startSystemApplicationForFile
   *      (java.io.File)
   */
  public void startSystemApplicationForFile(File file)
  {
    String execCommandString = "cmd.exe /c " + getWrappedPath(file);
    this.runtimeExecDelegate.exec(execCommandString);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.IRuntimeExecCalls#startCmdExeOrShellForFile
   *      (java.io.File)
   */
  public void startCmdExeOrShellForFile(File file)
  {
    String execCommandString = "cmd.exe /c start /d " + getWrappedPath(file);
    this.runtimeExecDelegate.exec(execCommandString);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.IRuntimeExecCalls#startCustomCommandForFile
   *      (java.lang.String, java.io.File)
   */
  public void startCustomCommandForFile(String customCommand, File file)
  {
    String path = getWrappedPath(file);
    customCommand = customCommand.replace(RESOURCE_PATH_VAR, path);
    String name = getWrappedName(file);
    customCommand = customCommand.replace(RESOURCE_NAME_VAR, name);
    File parent = file.getParentFile();
    String parentPath;
    if (parent != null)
    {
      parentPath = getWrappedPath(parent);
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

  private static String getWrappedPath(File file)
  {
    return "\"" + file.getAbsolutePath() + "\"";
  }

  private static String getWrappedName(File file)
  {
    return "\"" + file.getName() + "\"";
  }
}
