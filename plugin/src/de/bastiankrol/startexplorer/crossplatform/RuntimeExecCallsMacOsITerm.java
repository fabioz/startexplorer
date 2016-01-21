package de.bastiankrol.startexplorer.crossplatform;

import java.io.File;

/**
 * Runtime exec calls for Mac OS. Thanks to Yevgeniy M.
 *
 * @author Bastian Krol
 */
class RuntimeExecCallsMacOsITerm extends RuntimeExecCallsMacOs
{

  /**
   * Creates a new instance and initializes the {@link RuntimeExecDelegate}.
   */
  RuntimeExecCallsMacOsITerm()
  {
    super();
  }

  /**
   * Creates a new instance with the given {@link RuntimeExecDelegate}.
   *
   * @param delegate the RuntimeExecDelegate to use
   */
  RuntimeExecCallsMacOsITerm(RuntimeExecDelegate delegate)
  {
    super(delegate);
  }

  @Override
  String[] getCommandForStartShell(File file)
  {
    return new String[] { "open", "-a", "/Applications/iTerm.app",
        getPath(file) };
  }
}
