package de.bastiankrol.startexplorer;

import java.io.File;

/**
 * Runtime exec calls for Gnome.
 * 
 * @author Bastian Krol
 */
class RuntimeExecCallsLxde extends AbstractRuntimeExecCalls
{
  /**
   * Creates a new instance and initializes the {@link RuntimeExecDelegate}.
   */
  RuntimeExecCallsLxde()
  {
    super();
  }

  /**
   * Creates a new instance with the given {@link RuntimeExecDelegate}.
   * 
   * @param delegate the RuntimeExecDelegate to use
   */
  RuntimeExecCallsLxde(RuntimeExecDelegate delegate)
  {
    super(delegate);
  }

  @Override
  String getCommandForStartFileManager(File file, boolean selectFile)
  {
    return "pcmanfm " + getPath(file);
  }

  @Override
  File getWorkingDirectoryForStartFileManager(File file)
  {
    return null;
  }

  @Override
  String getCommandForStartShell(File file)
  {
    return "lxterminal --working-directory=" + getPath(file);
  }

  @Override
  File getWorkingDirectoryForForStartShell(File file)
  {
    return file;
  }

  @Override
  String getCommandForStartSystemApplication(File file)
  {
    // I'm not sure if this works on every LXDE system. Is xdg-open always installed?
    // What is the correct way of doing this on LXDE?
    return "xdg-open " + getPath(file);
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
