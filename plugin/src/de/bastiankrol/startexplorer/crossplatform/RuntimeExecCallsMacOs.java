package de.bastiankrol.startexplorer.crossplatform;

import java.io.File;

/**
 * Runtime exec calls for Mac OS. Thanks to Yevgeniy M.
 * 
 * @author Bastian Krol
 */
class RuntimeExecCallsMacOs extends AbstractRuntimeExecCalls
{
  /**
   * Creates a new instance and initializes the {@link RuntimeExecDelegate}.
   */
  RuntimeExecCallsMacOs()
  {
    super();
  }

  /**
   * Creates a new instance with the given {@link RuntimeExecDelegate}.
   * 
   * @param delegate the RuntimeExecDelegate to use
   */
  RuntimeExecCallsMacOs(RuntimeExecDelegate delegate)
  {
    super(delegate);
  }

  @Override
  String[] getCommandForStartFileManager(File file, boolean selectFile)
  {
    if (selectFile && file.isFile())
    {
      return new String[]{"open", "-R", getPath(file)};
    }
    else
    {
      return new String[]{"open", getPath(file)};
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
    return new String[]{ "open", "-a", "/Applications/Utilities/Terminal.app", getPath(file)};
  }

  @Override
  File getWorkingDirectoryForForStartShell(File file)
  {
    return null;
  }

  @Override
  String[] getCommandForStartSystemApplication(File file)
  {
    return new String[]{"open", getPath(file)};
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
    return true;
  }

  @Override
  boolean doFilePartsWantWrapping()
  {
    return false;
  }

  @Override
  boolean doFilePartsWantEscaping()
  {
    // TODO doFilePartsWantEscaping()
    return false;
  }
}
