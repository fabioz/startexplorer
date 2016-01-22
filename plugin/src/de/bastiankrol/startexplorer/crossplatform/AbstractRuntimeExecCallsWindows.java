package de.bastiankrol.startexplorer.crossplatform;

import java.io.File;
import java.net.URL;

/**
 * Runtime exec related code common to all Windows variants (plain Windows,
 * Cygwin, PowerShell, Msys Git Bash, ...).
 * 
 * @author Bastian Krol
 */
abstract class AbstractRuntimeExecCallsWindows extends AbstractRuntimeExecCalls
{
  /*
   * Maintenance notice:
   * 
   * For some very weird reason, on windows it is much safer to use
   * java.lang.Runtime.getRuntime().exec(String, String[], File) instead of
   * java.lang.Runtime.getRuntime().exec(String[], String[], File)!
   * 
   * That's why all methods here return a String array of length 1, with all
   * command parts concatenated.
   * 
   * See also:
   * http://stackoverflow.com/questions/6686592/runtime-exec-on-argument
   * -containing-multiple-spaces
   * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6511002
   * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6468220
   */

  private static final Capabilities WINDOWS_CAPABILITIES = Capabilities
      .create().withFileSelectionSupport().withUrlSupport().build();

  /**
   * Creates a new instance and initializes the {@link RuntimeExecDelegate}.
   */
  AbstractRuntimeExecCallsWindows()
  {
    super();
  }

  /**
   * Creates a new instance with the given {@link RuntimeExecDelegate}.
   * 
   * @param delegate the RuntimeExecDelegate to use
   */
  AbstractRuntimeExecCallsWindows(RuntimeExecDelegate delegate)
  {
    super(delegate);
  }

  @Override
  String[] getCommandForStartFileManager(File file, boolean selectFile)
  {
    if (selectFile && file.isFile())
    {
      return new String[] { "Explorer.exe /select," + getPath(file) };
    }
    else
    {
      return new String[] { "Explorer.exe /e," + getPath(file) };
    }
  }

  @Override
  String[] getCommandForStartFileManager(URL url)
  {
    return new String[] { "Explorer.exe /e," + url.toString() };
  }

  @Override
  File getWorkingDirectoryForStartFileManager(File file)
  {
    return null;
  }

  @Override
  File getWorkingDirectoryForForStartShell(File file)
  {
    return null;
  }

  @Override
  String[] getCommandForStartSystemApplication(File file)
  {
    return new String[] { "cmd.exe /c " + getPath(file) };
  }

  @Override
  File getWorkingDirectoryForForStartSystemApplication(File file)
  {
    return null;
  }

  @Override
  File getWorkingDirectoryForCustomCommand(File file)
  {
    return null;
  }

  @Override
  public Capabilities getCapabilities()
  {
    return WINDOWS_CAPABILITIES;
  }

  @Override
  boolean doFilePartsWantWrapping()
  {
    return true;
  }

  @Override
  boolean doFilePartsWantEscaping()
  {
    return false;
  }

  @Override
  boolean isWindows()
  {
    return true;
  }
}
