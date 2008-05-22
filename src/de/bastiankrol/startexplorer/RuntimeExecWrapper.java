package de.bastiankrol.startexplorer;

import java.io.IOException;
import java.util.List;

/**
 * Wrapper for calls to {@link java.lang.Runtime#exec(String)}. Offers two
 * services:
 * <ul>
 * <li>Opening one or several Windows Explorer windows for a certain path in
 * the filesystem</li>
 * <li>Starting the default windows application for a file.
 * </ul>
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$ $Author:$
 */
public class RuntimeExecWrapper
{
  private static final Runtime RUNTIME = Runtime.getRuntime();

  /**
   * Starts the windows explorer for the paths in the list.
   * 
   * @param pathList the path list to start a windows explorer for.
   */
  public static void startWindowsExplorerForPathList(List<String> pathList)
  {
    for (String path : pathList)
    {
      startWindowsExplorerForPath(path);
    }
  }

  /**
   * Starts the windows explorer for the given path.
   * 
   * @param path the path to start a windows explorer for.
   */
  public static void startWindowsExplorerForPath(String path)
  {
    try
    {
      String execCommandString = "Explorer.exe /e," + path;
      RUNTIME.exec(execCommandString);
    }
    catch (IOException e)
    {
      Activator.logException(e);
    }
  }

  /**
   * Starts a windows system application for the file given by <code>path</code>.
   * This is pretty much the same as &quot;Open With - System Editor&quot;.
   * 
   * @param path the path to start a windows system application for.
   */
  public static void startWindowsSystemApplicationForPath(String path)
  {
    try
    {
      String execCommandString = "cmd.exe /c " + path;
      RUNTIME.exec(execCommandString);
    }
    catch (IOException e)
    {
      Activator.logException(e);
    }
  }
}
