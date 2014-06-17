package de.bastiankrol.startexplorer.crossplatform;

import java.io.File;

/**
 * Runtime exec calls for plain vanilla Windows.
 * 
 * @author Bastian Krol
 */
class RuntimeExecCallsWindows extends AbstractRuntimeExecCallsWindows
{

  /**
   * Creates a new instance and initializes the {@link RuntimeExecDelegate}.
   */
  RuntimeExecCallsWindows()
  {
    super();
  }

  /**
   * Creates a new instance with the given {@link RuntimeExecDelegate}.
   * 
   * @param delegate the RuntimeExecDelegate to use
   */
  RuntimeExecCallsWindows(RuntimeExecDelegate delegate)
  {
    super(delegate);
  }

  @Override
  String[] getCommandForStartShell(File file)
  {
    return new String[] { "cmd.exe /c start /d " + getPath(file) };
  }
}
