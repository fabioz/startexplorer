package de.bastiankrol.startexplorer.crossplatform;

import java.io.File;
import java.util.List;

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
 */
public interface IRuntimeExecCalls
{
  /**
   * Starts the file manager (windows explorer or linux equivalent, like
   * Nautilus or Konqueror) for the paths in the list.
   * 
   * @param fileList the list of File objects to start a file manager for.
   * @param selectFile if {@code true} then for all objects from
   *          {@code fileList}: If the object is a file (not a directory) then
   *          the file will be selected/highlighted in the new file manager
   *          window (if supported by the file manager)
   */
  void startFileManagerForFileList(List<File> fileList, boolean selectFile);

  /**
   * Starts a (windows or linux) system application for the paths in the list.
   * 
   * @param fileList the list of File objects to start a system application for.
   */
  void startSystemApplicationForFileList(List<File> fileList);

  /**
   * Starts a command prompt or shell for the paths in the list.
   * 
   * @param fileList the list of File objects to start a cmd.exe/shell in.
   */
  void startShellForFileList(List<File> fileList);

  /**
   * Starts a custom command, defined by user preferences, for the given list of
   * files.
   * 
   * @param customCommand the custom command to execute
   * @param fileList the list of File objects to execute the custom command for
   */
  void startCustomCommandForFileList(String customCommand, List<File> fileList);

  /**
   * Starts the file manager (windows explorer or linux equivalent, like
   * Nautilus or Konqueror) for the given path.
   * 
   * @param file the File to start the file manager for.
   * @param selectFile if {@code true} and {@code file} is a file (not a
   *          directory) then the file will be selected/highlighted in the new
   *          file manager window (if supported by the file manager)
   */
  void startFileManagerForFile(File file, boolean selectFile);

  /**
   * Starts a system application for the file given by <code>file</code>. This
   * is pretty much the same as &quot;Open With - System Editor&quot;.
   * 
   * @param file the File to start a system application for.
   */
  void startSystemApplicationForFile(File file);

  /**
   * Starts a command prompt/shell for the file given by <code>file</code>.
   * 
   * @param file the File representing the path to start a cmd.exe/shell in.
   */
  void startShellForFile(File file);

  /**
   * Starts a custom command, defined by user preferences, for the given file.
   * 
   * @param customCommand the custom command to execute
   * @param file the File
   */
  void startCustomCommandForFile(String customCommand, File file);

  /**
   * @return {@code true} if and only if the operating system's/desktop
   *         manager's file manager supports selecting files (as opposed to just
   *         opening a certain directory) on startup
   */
  boolean isFileSelectionSupportedByFileManager();
}