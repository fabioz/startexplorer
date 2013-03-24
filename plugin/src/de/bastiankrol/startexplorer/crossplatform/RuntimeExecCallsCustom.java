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
  String[] getCommandForStartFileManager(File file, boolean selectFile)
  {
    String[] cmdArray;
    if (this.container.isFileSelectionSupportedByFileManager() && selectFile
        && file.isFile())
    {
      cmdArray = new String[] { this.container
          .getCommandForStartFileManagerAndSelectFile() };
    }
    else
    {
      cmdArray = new String[] { this.container.getCommandForStartFileManager() };
    }
    this.getVariableManager().replaceAllVariablesInCommand(cmdArray, file,
        this.container.doFilePartsWantWrapping(),
        this.container.doFilePartsWantEscaping());
    return cmdArray;
  }

  @Override
  File getWorkingDirectoryForStartFileManager(File file)
  {
    return this.returnWorkingDir(
        this.container.getWorkingDirectoryModeForStartFileManager(), file);
  }

  @Override
  String[] getCommandForStartShell(File file)
  {
    String[] cmdArray = new String[] { this.container.getCommandForStartShell() };
    this.getVariableManager().replaceAllVariablesInCommand(cmdArray, file,
        this.container.doFilePartsWantWrapping(),
        this.container.doFilePartsWantEscaping());
    return cmdArray;
  }

  @Override
  File getWorkingDirectoryForForStartShell(File file)
  {
    return this.returnWorkingDir(
        this.container.getWorkingDirectoryModeForStartShell(), file);
  }

  @Override
  String[] getCommandForStartSystemApplication(File file)
  {
    String[] cmdArray = new String[] { this.container
        .getCommandForStartSystemApplication() };
    this.getVariableManager().replaceAllVariablesInCommand(cmdArray, file,
        this.container.doFilePartsWantWrapping(),
        this.container.doFilePartsWantEscaping());
    return cmdArray;
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

  @Override
  boolean doFilePartsWantEscaping()
  {
    // TODO doFilePartsWantEscaping()
    return false;
  }
}