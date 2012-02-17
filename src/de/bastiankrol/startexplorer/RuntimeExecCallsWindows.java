package de.bastiankrol.startexplorer;

import java.io.File;

/**
 * Runtime exec calls for Windows.
 * 
 * @author Bastian Krol
 */
class RuntimeExecCallsWindows extends AbstractRuntimeExecCalls
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
  String getCommandForStartFileManager(File file, boolean selectFile)
  {
    if (selectFile && file.isFile())
    {
      return "Explorer.exe /select," + getPath(file, true);
    }
    else
    {
      return "Explorer.exe /e," + getPath(file, true);
    }
  }

  @Override
  File getWorkingDirectoryForStartFileManager(File file)
  {
    return null;
  }

  @Override
  String getCommandForStartShell(File file)
  {
    return "cmd.exe /c start /d " + getPath(file, true);
  }

  @Override
  File getWorkingDirectoryForForStartShell(File file)
  {
    return null;
  }

  @Override
  String getCommandForStartSystemApplication(File file)
  {
    return "cmd.exe /c " + getPath(file, true);
  }

  @Override
  File getWorkingDirectoryForForStartSystemApplication(File file)
  {
    return null;
  }

  @Override
  File getWorkingDirectoryForCustomCommand(File file)
  {
    throw new UnsupportedOperationException(
    "This feature is currently not supported for Windows.");
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
}
