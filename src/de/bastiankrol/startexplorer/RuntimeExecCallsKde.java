package de.bastiankrol.startexplorer;

import java.io.File;

/**
 * Runtime exec calls for Gnome.
 * 
 * @author Bastian Krol
 */
class RuntimeExecCallsKde extends AbstractRuntimeExecCalls
{
  /**
   * Creates a new instance and initializes the {@link RuntimeExecDelegate}.
   */
  RuntimeExecCallsKde()
  {
    super();
  }

  /**
   * Creates a new instance with the given {@link RuntimeExecDelegate}.
   * 
   * @param delegate the RuntimeExecDelegate to use
   */
  RuntimeExecCallsKde(RuntimeExecDelegate delegate)
  {
    super(delegate);
  }

  @Override
  String getCommandForStartFileManager(File file, boolean selectFile)
  {
    throw new UnsupportedOperationException(
        "This feature is not yet supported for KDE.");
  }

  @Override
  File getWorkingDirectoryForStartFileManager(File file)
  {
    throw new UnsupportedOperationException(
        "This feature is not yet supported for KDE.");
  }

  @Override
  String getCommandForStartShell(File file)
  {
    throw new UnsupportedOperationException(
        "This feature is not yet supported for KDE.");
  }

  @Override
  File getWorkingDirectoryForForStartShell(File file)
  {
    throw new UnsupportedOperationException(
        "This feature is not yet supported for KDE.");
  }

  @Override
  String getCommandForStartSystemApplication(File file)
  {
    return "kde-open " + file.getAbsolutePath();
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
}
