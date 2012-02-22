package de.bastiankrol.startexplorer.crossplatform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;

import de.bastiankrol.startexplorer.Activator;

public class CustomDesktopEnvironmentContainer
{
  private String commandForStartFileManager;
  private String commandForStartFileManagerAndSelectFile;
  private WorkingDirectoryMode workingDirectoryModeForStartFileManager;
  private String commandForStartShell;
  private WorkingDirectoryMode workingDirectoryModeForStartShell;
  private String commandForStartSystemApplication;
  private WorkingDirectoryMode workingDirectoryModeForStartSystemApplication;
  private WorkingDirectoryMode workingDirectoryModeForCustomCommands;
  private boolean fileSelectionSupported;
  private boolean filePartsWantWrapping;

  private static final Map<OperatingSystem, Map<String, Object>> DEFAULTS;

  public static final String KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER = "de.bastiankrol.startexplorer.custom_desktop_environment_command_start_file_manager";
  public static final String KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER_AND_SELECT_FILE = "de.bastiankrol.startexplorer.custom_desktop_environment_command_start_file_manager_and_select_file";
  public static final String KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_FILE_MANAGER = "de.bastiankrol.startexplorer.custom_desktop_environment_working_directory_mode_start_file_manager";
  public static final String KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SHELL = "de.bastiankrol.startexplorer.custom_desktop_environment_command_start_shell";
  public static final String KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SHELL = "de.bastiankrol.startexplorer.custom_desktop_environment_working_directory_mode_start_shell";
  public static final String KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SYSTEM_APPLICATION = "de.bastiankrol.startexplorer.custom_desktop_environment_command_start_system_application";
  public static final String KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SYSTEM_APPLICATION = "de.bastiankrol.startexplorer.custom_desktop_environment_working_directory_mode_start_system_application";
  public static final String KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_CUSTOM_COMMANDS = "de.bastiankrol.startexplorer.custom_desktop_environment_working_directory_mode_custom_commands";
  public static final String KEY_CUSTOM_DESKTOP_ENVIRONMENT_FILE_SELECTION_SUPPORTED = "de.bastiankrol.startexplorer.custom_desktop_environment_file_selection_supported";
  public static final String KEY_CUSTOM_DESKTOP_ENVIRONMENT_FILE_PARTS_WANT_WRAPPING = "de.bastiankrol.startexplorer.custom_desktop_environment_file_parts_want_wrapping";

  public static final String DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER = "explorer.exe /e,${resource_path}";
  public static final String DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER_AND_SELECT_FILE = "explorer.exe /select,${resource_path}";
  public static final WorkingDirectoryMode DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_FILE_MANAGER = WorkingDirectoryMode.NONE;
  public static final String DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SHELL = "cmd.exe /c start /d ${resource_path}";
  public static final WorkingDirectoryMode DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SHELL = WorkingDirectoryMode.NONE;
  public static final String DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SYSTEM_APPLICATION = "cmd.exe /c ${resource_path}";
  public static final WorkingDirectoryMode DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SYSTEM_APPLICATION = WorkingDirectoryMode.NONE;
  public static final WorkingDirectoryMode DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_CUSTOM_COMMANDS = WorkingDirectoryMode.NONE;
  public static final boolean DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_FILE_SELECTION_SUPPORTED = true;
  public static final boolean DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_FILE_PARTS_WANT_WRAPPING = true;

  public static final String DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER = "nautilus ${resource_path}";
  public static final String DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER_AND_SELECT_FILE = "nautilus ${resource_path}";
  public static final WorkingDirectoryMode DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_FILE_MANAGER = WorkingDirectoryMode.NONE;
  public static final String DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SHELL = "gnome-terminal";
  public static final WorkingDirectoryMode DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SHELL = WorkingDirectoryMode.RESOURCE;
  public static final String DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SYSTEM_APPLICATION = "xdg-open ${resource_path}";
  public static final WorkingDirectoryMode DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SYSTEM_APPLICATION = WorkingDirectoryMode.PARENT;
  public static final WorkingDirectoryMode DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_CUSTOM_COMMANDS = WorkingDirectoryMode.NONE;
  public static final boolean DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_FILE_SELECTION_SUPPORTED = false;
  public static final boolean DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_FILE_PARTS_WANT_WRAPPING = false;

  public static final String DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER = "start-file-manager ${resource_path}";
  public static final String DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER_AND_SELECT_FILE = "start-file-manager ${resource_path}";
  public static final WorkingDirectoryMode DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_FILE_MANAGER = WorkingDirectoryMode.NONE;
  public static final String DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SHELL = "start-shell ${resource_path}";
  public static final WorkingDirectoryMode DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SHELL = WorkingDirectoryMode.NONE;
  public static final String DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SYSTEM_APPLICATION = "open-document ${resource_path}";
  public static final WorkingDirectoryMode DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SYSTEM_APPLICATION = WorkingDirectoryMode.NONE;
  public static final WorkingDirectoryMode DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_CUSTOM_COMMANDS = WorkingDirectoryMode.NONE;
  public static final boolean DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_FILE_SELECTION_SUPPORTED = false;
  public static final boolean DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_FILE_PARTS_WANT_WRAPPING = false;

  static
  {
    Map<String, Object> windowsDefaults = new HashMap<String, Object>();
    windowsDefaults.put(
        KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER,
        DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER);
    windowsDefaults
        .put(
            KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER_AND_SELECT_FILE,
            DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER_AND_SELECT_FILE);
    windowsDefaults
        .put(
            KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_FILE_MANAGER,
            DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_FILE_MANAGER);
    windowsDefaults.put(KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SHELL,
        DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SHELL);
    windowsDefaults
        .put(
            KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SHELL,
            DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SHELL);
    windowsDefaults
        .put(KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SYSTEM_APPLICATION,
            DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SYSTEM_APPLICATION);
    windowsDefaults
        .put(
            KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SYSTEM_APPLICATION,
            DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SYSTEM_APPLICATION);
    windowsDefaults
        .put(
            KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_CUSTOM_COMMANDS,
            DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_CUSTOM_COMMANDS);
    windowsDefaults.put(
        KEY_CUSTOM_DESKTOP_ENVIRONMENT_FILE_SELECTION_SUPPORTED,
        DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_FILE_SELECTION_SUPPORTED);
    windowsDefaults.put(
        KEY_CUSTOM_DESKTOP_ENVIRONMENT_FILE_PARTS_WANT_WRAPPING,
        DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_FILE_PARTS_WANT_WRAPPING);

    Map<String, Object> linuxDefaults = new HashMap<String, Object>();
    linuxDefaults.put(
        KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER,
        DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER);
    linuxDefaults
        .put(
            KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER_AND_SELECT_FILE,
            DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER_AND_SELECT_FILE);
    linuxDefaults
        .put(
            KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_FILE_MANAGER,
            DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_FILE_MANAGER);
    linuxDefaults.put(KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SHELL,
        DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SHELL);
    linuxDefaults
        .put(
            KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SHELL,
            DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SHELL);
    linuxDefaults
        .put(KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SYSTEM_APPLICATION,
            DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SYSTEM_APPLICATION);
    linuxDefaults
        .put(
            KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SYSTEM_APPLICATION,
            DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SYSTEM_APPLICATION);
    linuxDefaults
        .put(
            KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_CUSTOM_COMMANDS,
            DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_CUSTOM_COMMANDS);
    linuxDefaults.put(KEY_CUSTOM_DESKTOP_ENVIRONMENT_FILE_SELECTION_SUPPORTED,
        DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_FILE_SELECTION_SUPPORTED);
    linuxDefaults.put(KEY_CUSTOM_DESKTOP_ENVIRONMENT_FILE_PARTS_WANT_WRAPPING,
        DEFAULT_LINUX_CUSTOM_DESKTOP_ENVIRONMENT_FILE_PARTS_WANT_WRAPPING);

    Map<String, Object> otherDefaults = new HashMap<String, Object>();
    otherDefaults.put(
        KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER,
        DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER);
    otherDefaults
        .put(
            KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER_AND_SELECT_FILE,
            DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER_AND_SELECT_FILE);
    otherDefaults
        .put(
            KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_FILE_MANAGER,
            DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_FILE_MANAGER);
    otherDefaults.put(KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SHELL,
        DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SHELL);
    otherDefaults
        .put(
            KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SHELL,
            DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SHELL);
    otherDefaults
        .put(KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SYSTEM_APPLICATION,
            DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SYSTEM_APPLICATION);
    otherDefaults
        .put(
            KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SYSTEM_APPLICATION,
            DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SYSTEM_APPLICATION);
    otherDefaults
        .put(
            KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_CUSTOM_COMMANDS,
            DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_CUSTOM_COMMANDS);
    otherDefaults.put(KEY_CUSTOM_DESKTOP_ENVIRONMENT_FILE_SELECTION_SUPPORTED,
        DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_FILE_SELECTION_SUPPORTED);
    otherDefaults.put(KEY_CUSTOM_DESKTOP_ENVIRONMENT_FILE_PARTS_WANT_WRAPPING,
        DEFAULT_OTHER_CUSTOM_DESKTOP_ENVIRONMENT_FILE_PARTS_WANT_WRAPPING);

    DEFAULTS = new HashMap<OperatingSystem, Map<String, Object>>();
    DEFAULTS.put(OperatingSystem.WINDOWS, windowsDefaults);
    DEFAULTS.put(OperatingSystem.LINUX, linuxDefaults);
    DEFAULTS.put(OperatingSystem.UNKNOWN, otherDefaults);
  }

  /**
   * Creates a new instance without the values.
   */
  public CustomDesktopEnvironmentContainer()
  {
    super();
  }

  /**
   * Creates a new instance with the given values.
   */
  public CustomDesktopEnvironmentContainer(String commandForStartFileManager,
      String commandForStartFileManagerAndSelectFile,
      WorkingDirectoryMode workingDirectoryModeForStartFileManager,
      String commandForStartShell,
      WorkingDirectoryMode workingDirectoryModeForStartShell,
      String commandForStartSystemApplication,
      WorkingDirectoryMode workingDirectoryModeForStartSystemApplication,
      WorkingDirectoryMode workingDirectoryModeForCustomCommands,
      boolean fileSelectionSupported, boolean filePartsWantWrapping)
  {
    super();
    this.commandForStartFileManager = commandForStartFileManager;
    this.commandForStartFileManagerAndSelectFile = commandForStartFileManagerAndSelectFile;
    this.workingDirectoryModeForStartFileManager = workingDirectoryModeForStartFileManager;
    this.commandForStartShell = commandForStartShell;
    this.workingDirectoryModeForStartShell = workingDirectoryModeForStartShell;
    this.commandForStartSystemApplication = commandForStartSystemApplication;
    this.workingDirectoryModeForStartSystemApplication = workingDirectoryModeForStartSystemApplication;
    this.workingDirectoryModeForCustomCommands = workingDirectoryModeForCustomCommands;
    this.fileSelectionSupported = fileSelectionSupported;
    this.filePartsWantWrapping = filePartsWantWrapping;
    this.validate();
  }

  void validate()
  {
    List<String> validationErrors = new ArrayList<String>();
    this.validateString(this.commandForStartFileManager,
        "commandForStartFileManager", validationErrors);
    this.validateString(this.commandForStartFileManagerAndSelectFile,
        "commandForStartFileManagerAndSelectFile", validationErrors);
    this.validateWorkingDirectory(this.workingDirectoryModeForStartFileManager,
        "workingDirectoryModeForStartFileManager", validationErrors);
    this.validateString(this.commandForStartShell, "commandForStartShell",
        validationErrors);
    this.validateWorkingDirectory(this.workingDirectoryModeForStartShell,
        "workingDirectoryModeForStartShell", validationErrors);
    this.validateString(this.commandForStartSystemApplication,
        "commandForStartSystemApplication", validationErrors);
    this.validateWorkingDirectory(
        this.workingDirectoryModeForStartSystemApplication,
        "workingDirectoryModeForStartSystemApplication", validationErrors);
    this.validateWorkingDirectory(this.workingDirectoryModeForCustomCommands,
        "workingDirectoryModeForCustomCommands", validationErrors);
    for (String validationError : validationErrors)
    {
      Activator.logWarning(validationError);
    }
  }

  private void validateString(String variable, String variableName,
      List<String> validationErrors)
  {
    if (variable == null || variable.trim().equals(""))
    {
      validationErrors.add("Command for custom definition " + variableName
          + " has not been set.");
    }
  }

  private void validateWorkingDirectory(WorkingDirectoryMode workingDirectory,
      String workingDirectoryName, List<String> validationErrors)
  {
    if (workingDirectory == null)
    {
      validationErrors.add("Working directory for custom definition "
          + workingDirectoryName + " has not been set.");
    }
  }

  public void initializeFromDefaults(OperatingSystem operatingSystem)
  {
    Map<String, Object> defaults = DEFAULTS.get(operatingSystem);
    if (defaults == null)
    {
      defaults = DEFAULTS.get(OperatingSystem.UNKNOWN);
    }
    this.commandForStartFileManager = (String) defaults
        .get(KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER);
    this.commandForStartFileManagerAndSelectFile = (String) defaults
        .get(KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER_AND_SELECT_FILE);
    this.workingDirectoryModeForStartFileManager = (WorkingDirectoryMode) defaults
        .get(KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_FILE_MANAGER);
    this.commandForStartShell = (String) defaults
        .get(KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SHELL);
    this.workingDirectoryModeForStartShell = (WorkingDirectoryMode) defaults
        .get(KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SHELL);
    this.commandForStartSystemApplication = (String) defaults
        .get(KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SYSTEM_APPLICATION);
    this.workingDirectoryModeForStartSystemApplication = (WorkingDirectoryMode) defaults
        .get(KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SYSTEM_APPLICATION);
    this.workingDirectoryModeForCustomCommands = (WorkingDirectoryMode) defaults
        .get(KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_CUSTOM_COMMANDS);
    this.fileSelectionSupported = (Boolean) defaults
        .get(KEY_CUSTOM_DESKTOP_ENVIRONMENT_FILE_SELECTION_SUPPORTED);
    this.filePartsWantWrapping = (Boolean) defaults
        .get(KEY_CUSTOM_DESKTOP_ENVIRONMENT_FILE_PARTS_WANT_WRAPPING);
  }

  public void storeValues(IPreferenceStore store)
  {
    store.setValue(KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER,
        this.commandForStartFileManager);
    store
        .setValue(
            KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER_AND_SELECT_FILE,
            this.commandForStartFileManagerAndSelectFile);
    store
        .setValue(
            KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_FILE_MANAGER,
            this.workingDirectoryModeForStartFileManager.name());
    store.setValue(KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SHELL,
        this.commandForStartShell);
    store.setValue(
        KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SHELL,
        this.workingDirectoryModeForStartShell.name());
    store.setValue(
        KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SYSTEM_APPLICATION,
        this.commandForStartSystemApplication);
    store
        .setValue(
            KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SYSTEM_APPLICATION,
            this.workingDirectoryModeForStartSystemApplication.name());
    store
        .setValue(
            KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_CUSTOM_COMMANDS,
            this.workingDirectoryModeForCustomCommands.name());
    store.setValue(KEY_CUSTOM_DESKTOP_ENVIRONMENT_FILE_SELECTION_SUPPORTED,
        this.fileSelectionSupported);
    store.setValue(KEY_CUSTOM_DESKTOP_ENVIRONMENT_FILE_PARTS_WANT_WRAPPING,
        this.filePartsWantWrapping);
  }

  public String getCommandForStartFileManager()
  {
    return commandForStartFileManager;
  }

  public void setCommandForStartFileManager(String commandForStartFileManager)
  {
    this.commandForStartFileManager = commandForStartFileManager;
  }

  public String getCommandForStartFileManagerAndSelectFile()
  {
    return commandForStartFileManagerAndSelectFile;
  }

  public void setCommandForStartFileManagerAndSelectFile(
      String commandForStartFileManagerAndSelectFile)
  {
    this.commandForStartFileManagerAndSelectFile = commandForStartFileManagerAndSelectFile;
  }

  public WorkingDirectoryMode getWorkingDirectoryModeForStartFileManager()
  {
    return workingDirectoryModeForStartFileManager;
  }

  public void setWorkingDirectoryModeForStartFileManager(
      WorkingDirectoryMode workingDirectoryModeForStartFileManager)
  {
    this.workingDirectoryModeForStartFileManager = workingDirectoryModeForStartFileManager;
  }

  public String getCommandForStartShell()
  {
    return commandForStartShell;
  }

  public void setCommandForStartShell(String commandForStartShell)
  {
    this.commandForStartShell = commandForStartShell;
  }

  public WorkingDirectoryMode getWorkingDirectoryModeForStartShell()
  {
    return workingDirectoryModeForStartShell;
  }

  public void setWorkingDirectoryModeForStartShell(
      WorkingDirectoryMode workingDirectoryModeForStartShell)
  {
    this.workingDirectoryModeForStartShell = workingDirectoryModeForStartShell;
  }

  public String getCommandForStartSystemApplication()
  {
    return commandForStartSystemApplication;
  }

  public void setCommandForStartSystemApplication(
      String commandForStartSystemApplication)
  {
    this.commandForStartSystemApplication = commandForStartSystemApplication;
  }

  public WorkingDirectoryMode getWorkingDirectoryModeForStartSystemApplication()
  {
    return workingDirectoryModeForStartSystemApplication;
  }

  public void setWorkingDirectoryModeForStartSystemApplication(
      WorkingDirectoryMode workingDirectoryModeForStartSystemApplication)
  {
    this.workingDirectoryModeForStartSystemApplication = workingDirectoryModeForStartSystemApplication;
  }

  public WorkingDirectoryMode getWorkingDirectoryModeForCustomCommands()
  {
    return workingDirectoryModeForCustomCommands;
  }

  public void setWorkingDirectoryModeForCustomCommands(
      WorkingDirectoryMode workingDirectoryModeForCustomCommands)
  {
    this.workingDirectoryModeForCustomCommands = workingDirectoryModeForCustomCommands;
  }

  public boolean isFileSelectionSupportedByFileManager()
  {
    return fileSelectionSupported;
  }

  public void setFileSelectionSupportedByFileManager(
      boolean fileSelectionSupported)
  {
    this.fileSelectionSupported = fileSelectionSupported;
  }

  public boolean doFilePartsWantWrapping()
  {
    return filePartsWantWrapping;
  }

  public void setFilePartsWantWrapping(boolean filePartsWantWrapping)
  {
    this.filePartsWantWrapping = filePartsWantWrapping;
  }
}
