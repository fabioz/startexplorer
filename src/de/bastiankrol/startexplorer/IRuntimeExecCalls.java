package de.bastiankrol.startexplorer;

import java.io.File;
import java.util.List;

public interface IRuntimeExecCalls
{

  /**
   * Starts the file manager (windows explorer or linux equivalent, like
   * Nautilus or Konqueror) for the paths in the list.
   * 
   * @param fileList the list of File objects to start a file manager for.
   */
  public abstract void startFileManagerForFileList(List<File> fileList);

  /**
   * Starts a (windows or linux) system application for the paths in the list.
   * 
   * @param fileList the list of File objects to start a system application for.
   */
  public abstract void startSystemApplicationForFileList(List<File> fileList);

  /**
   * Starts a command prompt or shell for the paths in the list.
   * 
   * @param fileList the list of File objects to start a cmd.exe/shell in.
   */
  public abstract void startCmdExeOrShellForFileList(List<File> fileList);

  /**
   * Starts a custom command, defined by user preferences, for the given list of
   * files.
   * 
   * @param customCommand the custom command to execute
   * @param fileList the list of File objects to execute the custom command for
   */
  public abstract void startCustomCommandForFileList(String customCommand,
      List<File> fileList);

  /**
   * Starts the file manager (windows explorer or linux equivalent, like
   * Nautilus or Konqueror) for the given path.
   * 
   * @param file the File to start a windows explorer for.
   */
  public abstract void startFileManagerForFile(File file);

  /**
   * Starts a system application for the file given by <code>file</code>. This
   * is pretty much the same as &quot;Open With - System Editor&quot;.
   * 
   * @param file the File to start a system application for.
   */
  public abstract void startSystemApplicationForFile(File file);

  /**
   * Starts a command prompt/shell for the file given by <code>file</code>.
   * 
   * @param file the File representing the path to start a cmd.exe/shell in.
   */
  public abstract void startCmdExeOrShellForFile(File file);

  /**
   * Starts a custom command, defined by user preferences, for the given file.
   * 
   * @param customCommand the custom command to execute
   * @param file the File
   */
  public abstract void startCustomCommandForFile(String customCommand, File file);

}