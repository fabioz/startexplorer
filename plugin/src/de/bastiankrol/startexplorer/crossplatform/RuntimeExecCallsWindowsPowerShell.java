package de.bastiankrol.startexplorer.crossplatform;

import java.io.File;

/**
 * Runtime exec calls for Windows with PowerShell.
 * 
 * @author Bastian Krol
 */
class RuntimeExecCallsWindowsPowerShell extends AbstractRuntimeExecCallsWindows
{

  /**
   * Creates a new instance and initializes the {@link RuntimeExecDelegate}.
   */
  RuntimeExecCallsWindowsPowerShell()
  {
    super();
  }

  /**
   * Creates a new instance with the given {@link RuntimeExecDelegate}.
   * 
   * @param delegate the RuntimeExecDelegate to use
   */
  RuntimeExecCallsWindowsPowerShell(RuntimeExecDelegate delegate)
  {
    super(delegate);
  }

  @Override
  String[] getCommandForStartShell(File file)
  {
    return new String[] { "cmd.exe /c start /d " + getPath(file)
        + " powershell.exe" };
  }

  @Override
  String[] getCommandForStartSystemApplication(File file)
  {
    return new String[] { "cmd.exe /c start /d " + getParentPath(file)
        + " powershell.exe -command " + getPath(file) };
  }
}
