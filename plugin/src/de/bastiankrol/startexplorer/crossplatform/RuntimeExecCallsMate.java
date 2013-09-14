package de.bastiankrol.startexplorer.crossplatform;

import java.io.File;

/**
 * Runtime exec calls for Mate.
 * 
 * @author Bastian Krol
 */
class RuntimeExecCallsMate extends AbstractRuntimeExecCalls
{
  /**
   * Creates a new instance and initializes the {@link RuntimeExecDelegate}.
   */
  RuntimeExecCallsMate()
  {
    super();
  }

  /**
   * Creates a new instance with the given {@link RuntimeExecDelegate}.
   * 
   * @param delegate the RuntimeExecDelegate to use
   */
  RuntimeExecCallsMate(RuntimeExecDelegate delegate)
  {
    super(delegate);
  }

  @Override
  String[] getCommandForStartFileManager(File file, boolean selectFile)
  {
    return new String[] { "caja", getPath(file) };
  }

  @Override
  File getWorkingDirectoryForStartFileManager(File file)
  {
    return file;
  }

  @Override
  String[] getCommandForStartShell(File file)
  {
    return new String[] { "mate-terminal" };
  }

  @Override
  File getWorkingDirectoryForForStartShell(File file)
  {
    return file;
  }

  @Override
  String[] getCommandForStartSystemApplication(File file)
  {
    return new String[] { "mate-terminal", getPath(file) };
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
    return false;
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

  @Override
  boolean isWindows()
  {
    return false;
  }
}
