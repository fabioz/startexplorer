package de.bastiankrol.startexplorer.crossplatform;

import java.io.File;

/**
 * Custom/configurable runtime exec calls.
 * 
 * @author Bastian Krol
 */
class RuntimeExecCallsCustom extends AbstractRuntimeExecCalls
{

  private CustomDesktopEnvironmentContainer container;

  /**
   * Creates a new instance and initializes the {@link RuntimeExecDelegate}.
   */
  RuntimeExecCallsCustom(CustomDesktopEnvironmentContainer container)
  {
    super();
    this.container = container;
  }

  /**
   * Creates a new instance with the given {@link RuntimeExecDelegate}.
   * 
   * @param delegate the RuntimeExecDelegate to use
   */
  RuntimeExecCallsCustom(RuntimeExecDelegate delegate,
      CustomDesktopEnvironmentContainer container)
  {
    super(delegate);
    this.container = container;
  }

  @Override
  String getCommandForStartFileManager(File file, boolean selectFile)
  {
    if (this.container.isFileSelectionSupportedByFileManager() && selectFile
        && file.isFile())
    {
      return replaceAllVariablesInCommand(
          this.container.getCommandForStartFileManagerAndSelectFile(), file,
          this.container.doFilePartsWantWrapping());
    }
    else
    {
      return replaceAllVariablesInCommand(
          this.container.getCommandForStartFileManager(), file,
          this.container.doFilePartsWantWrapping());
    }
  }

  @Override
  File getWorkingDirectoryForStartFileManager(File file)
  {
    return this.returnWorkingDir(
        this.container.getWorkingDirectoryModeForStartFileManager(), file);
  }

  @Override
  String getCommandForStartShell(File file)
  {
    return replaceAllVariablesInCommand(
        this.container.getCommandForStartShell(), file,
        this.container.doFilePartsWantWrapping());
  }

  @Override
  File getWorkingDirectoryForForStartShell(File file)
  {
    return this.returnWorkingDir(
        this.container.getWorkingDirectoryModeForStartShell(), file);
  }

  @Override
  String getCommandForStartSystemApplication(File file)
  {
    return replaceAllVariablesInCommand(
        this.container.getCommandForStartSystemApplication(), file,
        this.container.doFilePartsWantWrapping());
  }

  @Override
  File getWorkingDirectoryForForStartSystemApplication(File file)
  {
    return this
        .returnWorkingDir(
            this.container.getWorkingDirectoryModeForStartSystemApplication(),
            file);
  }

  @Override
  File getWorkingDirectoryForCustomCommand(File file)
  {
    return this.returnWorkingDir(
        this.container.getWorkingDirectoryModeForCustomCommands(), file);
  }

  private File returnWorkingDir(WorkingDirectoryMode workingDirectoryMode,
      File file)
  {
    if (workingDirectoryMode == null)
    {
      return null;
    }
    switch (workingDirectoryMode)
    {
      case NONE:
        return null;
      case RESOURCE:
        return file;
      case PARENT:
        return file.getParentFile() != null ? file.getParentFile() : null;
      default:
        throw new RuntimeException("Unknown working directory mode: "
            + workingDirectoryMode);
    }
  }

  public boolean isFileSelectionSupportedByFileManager()
  {
    return this.container.isFileSelectionSupportedByFileManager();
  }

  @Override
  boolean doFilePartsWantWrapping()
  {
    return this.container.doFilePartsWantWrapping();
  }
}