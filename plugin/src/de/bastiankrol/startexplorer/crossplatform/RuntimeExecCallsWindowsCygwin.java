package de.bastiankrol.startexplorer.crossplatform;

import java.io.File;

/**
 * Runtime exec calls for Windows with Cygwin.
 * 
 * @author Bastian Krol
 */
class RuntimeExecCallsWindowsCygwin extends AbstractRuntimeExecCalls
{

  /**
   * Creates a new instance and initializes the {@link RuntimeExecDelegate}.
   */
  RuntimeExecCallsWindowsCygwin()
  {
    super();
  }

  /**
   * Creates a new instance with the given {@link RuntimeExecDelegate}.
   * 
   * @param delegate the RuntimeExecDelegate to use
   */
  RuntimeExecCallsWindowsCygwin(RuntimeExecDelegate delegate)
  {
    super(delegate);
  }

  @Override
  String[] getCommandForStartFileManager(File file, boolean selectFile)
  {
    if (selectFile && file.isFile())
    {
      return new String[] { "Explorer.exe", "/select," + getPath(file) };
    }
    else
    {
      return new String[] { "Explorer.exe", "/e," + getPath(file) };
    }
  }

  @Override
  File getWorkingDirectoryForStartFileManager(File file)
  {
    return null;
  }

  @Override
  String[] getCommandForStartShell(File file)
  {
    return new String[] { "cmd.exe", "/c", "start", "/d", getPath(file),
        "bash.exe" };
  }

  @Override
  File getWorkingDirectoryForForStartShell(File file)
  {
    return null;
  }

  @Override
  String[] getCommandForStartSystemApplication(File file)
  {
    return new String[] { "cmd.exe", "/c", getPath(file) };
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

  public boolean isFileSelectionSupportedByFileManager()
  {
    return true;
  }

  @Override
  boolean doFilePartsWantWrapping()
  {
    return true;
  }

  @Override
  boolean doFilePartsWantEscaping()
  {
    // TODO doFilePartsWantEscaping()
    return false;
  }
}
