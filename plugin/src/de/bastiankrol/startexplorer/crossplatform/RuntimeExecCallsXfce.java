package de.bastiankrol.startexplorer.crossplatform;

import java.io.File;

/**
 * Runtime exec calls for Xfce.
 * 
 * @author Bastian Krol
 */
class RuntimeExecCallsXfce extends AbstractRuntimeExecCalls
{
  /**
   * Creates a new instance and initializes the {@link RuntimeExecDelegate}.
   */
  RuntimeExecCallsXfce()
  {
    super();
  }

  /**
   * Creates a new instance with the given {@link RuntimeExecDelegate}.
   * 
   * @param delegate the RuntimeExecDelegate to use
   */
  RuntimeExecCallsXfce(RuntimeExecDelegate delegate)
  {
    super(delegate);
  }

  @Override
  String[] getCommandForStartFileManager(File file, boolean selectFile)
  {
    return new String[] { "thunar", getPath(file) };
  }

  @Override
  File getWorkingDirectoryForStartFileManager(File file)
  {
    return null;
  }

  @Override
  String[] getCommandForStartShell(File file)
  {
    return new String[] { "exo-open", "--launch", "TerminalEmulator",
        "--working-directory", getPath(file) };
  }

  @Override
  File getWorkingDirectoryForForStartShell(File file)
  {
    return null;
  }

  @Override
  String[] getCommandForStartSystemApplication(File file)
  {
    return new String[] { "exo-open", getPath(file) };
  }

  @Override
  File getWorkingDirectoryForForStartSystemApplication(File file)
  {
    return file.getParentFile() != null ? file.getParentFile() : null;
  }

  @Override
  File getWorkingDirectoryForCustomCommand(File file)
  {
    return null;
  }

  public boolean isFileSelectionSupportedByFileManager()
  {
    return false;
  }

  @Override
  boolean doFilePartsWantWrapping()
  {
    return false;
  }

  @Override
  boolean doFilePartsWantEscaping()
  {
    return false;
  }

  @Override
  boolean isWindows()
  {
    return false;
  }
}
