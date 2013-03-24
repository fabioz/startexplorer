package de.bastiankrol.startexplorer.crossplatform;

import static de.bastiankrol.startexplorer.Activator.*;

import java.io.File;
import java.util.List;

import de.bastiankrol.startexplorer.util.Util;
import de.bastiankrol.startexplorer.variables.VariableManager;

/**
 * Bundles common functionality for all implementations of
 * {@link IRuntimeExecCalls}.
 * 
 * @author Bastian Krol
 */
abstract class AbstractRuntimeExecCalls implements IRuntimeExecCalls
{
  IRuntimeExecDelegate runtimeExecDelegate;

  /**
   * Creates a new instance and initializes the {@link RuntimeExecDelegate}.
   */
  AbstractRuntimeExecCalls()
  {
    this.runtimeExecDelegate = new RuntimeExecDelegate();
  }

  /**
   * Creates a new instance with the given {@link RuntimeExecDelegate}.
   * 
   * @param delegate the RuntimeExecDelegate to use
   */
  AbstractRuntimeExecCalls(RuntimeExecDelegate delegate)
  {
    this.runtimeExecDelegate = delegate;
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
   * @see de.bastiankrol.startexplorer.crossplatform.IRuntimeExecCalls#startFileManagerForFileList
   *      (java.util.List, boolean)
   */
  public void startFileManagerForFileList(List<File> fileList,
      boolean selectFile)
  {
    for (File file : fileList)
    {
      this.startFileManagerForFile(file, selectFile);
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
   * @see de.bastiankrol.startexplorer.crossplatform.IRuntimeExecCalls#startShellForFileList
   *      (java.util.List)
   */
  public void startShellForFileList(List<File> fileList)
  {
    for (File file : fileList)
    {
      this.startShellForFile(file);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.crossplatform.IRuntimeExecCalls#startCustomCommandForFileList(String[], List)
   */
  public void startCustomCommandForFileList(String[] customCommand,
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
   * @see de.bastiankrol.startexplorer.crossplatform.IRuntimeExecCalls#startFileManagerForFile
   *      (java.io.File, boolean)
   */
  public void startFileManagerForFile(File file, boolean selectFile)
  {
    this.runtimeExecDelegate.exec(
        this.getCommandForStartFileManager(file, selectFile),
        this.getWorkingDirectoryForStartFileManager(file));
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.crossplatform.IRuntimeExecCalls#startShellForFile
   *      (java.io.File)
   */
  public void startShellForFile(File file)
  {
    this.runtimeExecDelegate.exec(this.getCommandForStartShell(file),
        this.getWorkingDirectoryForForStartShell(file));
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.crossplatform.IRuntimeExecCalls#startSystemApplicationForFile
   *      (java.io.File)
   */
  public void startSystemApplicationForFile(File file)
  {
    this.runtimeExecDelegate.exec(
        this.getCommandForStartSystemApplication(file),
        this.getWorkingDirectoryForForStartSystemApplication(file));
  }

  abstract String[] getCommandForStartFileManager(File file, boolean selectFile);

  abstract File getWorkingDirectoryForStartFileManager(File file);

  abstract String[] getCommandForStartShell(File file);

  abstract File getWorkingDirectoryForForStartShell(File file);

  abstract String[] getCommandForStartSystemApplication(File file);

  abstract File getWorkingDirectoryForForStartSystemApplication(File file);

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.crossplatform.IRuntimeExecCalls#startCustomCommandForFile(String[], File)
   */
  public void startCustomCommandForFile(String[] cmdArray, File file)
  {
    boolean wrapFileParts = this.doFilePartsWantWrapping();
    boolean escapeFileParts = this.doFilePartsWantEscaping();
    getVariableManager().replaceAllVariablesInCommand(
        cmdArray, file, wrapFileParts, escapeFileParts);
    this.runtimeExecDelegate.exec(cmdArray,
        this.getWorkingDirectoryForCustomCommand(file));
  }

  /**
   * Replaces all supported variables in the given {@code command} and returns
   * the result. If {@code command} contains no variables, it will be returned
   * unchanged.
   * 
   * @param command the string in which variables will be replaced
   * @param file the file to evaluate for the variables
   * @param wrapFileParts if file parts should be wrapped in quotes
   * @return the command with variables substituted, if any.
   */

  abstract File getWorkingDirectoryForCustomCommand(File file);

  /**
   * @return {@code true} if and only if file path/name parts should be wrapped
   *         in quotes for this operating system's/desktop manager's file
   *         manager
   */
  abstract boolean doFilePartsWantWrapping();

  /**
   * @return {@code true} if and only if spaces in file path/name parts should
   *         be escaped by prepending a backspace for this operating
   *         system's/desktop manager's file manager
   */
  abstract boolean doFilePartsWantEscaping();

  VariableManager getVariableManager()
  {
    return getPluginContext().getVariableManager();
  }

  String getPath(File file)
  {
    return Util.getPath(file, this.doFilePartsWantWrapping(),
        this.doFilePartsWantEscaping());
  }

  String getParentPath(File file)
  {
    return Util.getParentPath(file, this.doFilePartsWantWrapping(),
        this.doFilePartsWantEscaping());
  }

  String getName(File file)
  {
    return Util.getName(file, this.doFilePartsWantWrapping(),
        this.doFilePartsWantEscaping());
  }
}
