package de.bastiankrol.startexplorer.crossplatform;

import java.io.File;
import java.net.URL;

/**
 * Runtime exec calls for Mac OS. Thanks to Yevgeniy M.
 * 
 * @author Bastian Krol
 */
class RuntimeExecCallsMacOs extends AbstractRuntimeExecCalls
{

  private static final Capabilities MAC_OS_CAPABILITIES = Capabilities.create()
      .withFileSelectionSupport().build();

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
      return new String[] { "open", "-R", getPath(file) };
    }
    else
    {
      return new String[] { "open", getPath(file) };
    }
  }

  @Override
  String[] getCommandForStartFileManager(URL url)
  {
    return new String[] { "open", url.toString() };
  }

  @Override
  File getWorkingDirectoryForStartFileManager(File file)
  {
    return null;
  }

  @Override
  String[] getCommandForStartShell(File file)
  {
    return new String[] { "open", "-a", "/Applications/Utilities/Terminal.app",
        getPath(file) };
  }

  @Override
  File getWorkingDirectoryForForStartShell(File file)
  {
    return null;
  }

  @Override
  String[] getCommandForStartSystemApplication(File file)
  {
    return new String[] { "open", getPath(file) };
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

  @Override
  public Capabilities getCapabilities()
  {
    return MAC_OS_CAPABILITIES;
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
