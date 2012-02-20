package de.bastiankrol.startexplorer;

import java.io.File;

/**
 * Runtime exec calls for Gnome.
 * 
 * @author Bastian Krol
 */
class RuntimeExecCallsGnome extends AbstractRuntimeExecCalls
{
  /**
   * Creates a new instance and initializes the {@link RuntimeExecDelegate}.
   */
  RuntimeExecCallsGnome()
  {
    super();
  }

  /**
   * Creates a new instance with the given {@link RuntimeExecDelegate}.
   * 
   * @param delegate the RuntimeExecDelegate to use
   */
  RuntimeExecCallsGnome(RuntimeExecDelegate delegate)
  {
    super(delegate);
  }

  @Override
  String getCommandForStartFileManager(File file, boolean selectFile)
  {
    return "nautilus " + getPath(file);
  }

  @Override
  File getWorkingDirectoryForStartFileManager(File file)
  {
    return null;
  }

  @Override
  String getCommandForStartShell(File file)
  {
    return "gnome-terminal";
  }

  @Override
  File getWorkingDirectoryForForStartShell(File file)
  {
    return file;
  }

  @Override
  String getCommandForStartSystemApplication(File file)
  {
    // If GNOME is your window manager, use the gnome-open command as follow:
    // $ gnome-open cisco.doc
    // For KDE users, use kde-open instead.
    // Alternatively, you can run the window-manager-neutral program called
    // xdg-open. xdg-open is part of the xdg-utils package.
    // $ xdg-open cisco.doc
    return "gnome-open " + getPath(file);
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
