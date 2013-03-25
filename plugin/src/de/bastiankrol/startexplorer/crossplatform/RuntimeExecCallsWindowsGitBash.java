package de.bastiankrol.startexplorer.crossplatform;

import java.io.File;

/**
 * Runtime exec calls for Windows with Cygwin.
 * 
 * @author Bastian Krol
 */
class RuntimeExecCallsWindowsGitBash extends AbstractRuntimeExecCallsWindows
{

  /**
   * Creates a new instance and initializes the {@link RuntimeExecDelegate}.
   */
  RuntimeExecCallsWindowsGitBash()
  {
    super();
  }

  /**
   * Creates a new instance with the given {@link RuntimeExecDelegate}.
   * 
   * @param delegate the RuntimeExecDelegate to use
   */
  RuntimeExecCallsWindowsGitBash(RuntimeExecDelegate delegate)
  {
    super(delegate);
  }

  @Override
  String[] getCommandForStartShell(File file)
  {
    return new String[] { "cmd.exe /c start /d " + getPath(file)
        + " sh.exe --login -i" };
  }
}
