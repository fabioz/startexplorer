package de.bastiankrol.startexplorer.preferences;

import static de.bastiankrol.startexplorer.crossplatform.CustomDesktopEnvironmentContainer.*;
import static de.bastiankrol.startexplorer.preferences.PreferenceConstantsAndDefaults.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;

import de.bastiankrol.startexplorer.Activator;
import de.bastiankrol.startexplorer.ResourceType;
import de.bastiankrol.startexplorer.crossplatform.CustomDesktopEnvironmentContainer;
import de.bastiankrol.startexplorer.crossplatform.DesktopEnvironment;
import de.bastiankrol.startexplorer.crossplatform.WorkingDirectoryMode;
import de.bastiankrol.startexplorer.customcommands.CommandConfig;

/**
 * Utility class for accessing the eclipse preference store
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$
 */
public class PreferenceUtil
{

  // TODO This class is ugly and duplicates a lot of stuff from PreferenceModel.
  // Should be refactored.

  IPreferenceStore retrievePreferenceStore()
  {
    return Activator.getDefault().getPreferenceStore();
  }

  /**
   * Loads the preferences from the eclipse preference store into the given
   * preference model
   * 
   * @param preferenceModel the preference model to fill with the values from
   *          the eclipse preference store
   */
  void loadPreferencesFromStoreIntoPreferenceModel(
      PreferenceModel preferenceModel)
  {
    IPreferenceStore store = this.retrievePreferenceStore();
    preferenceModel.setCommandConfigList(this
        .loadCustomCommandsFromStore(store));
    SeparatorData separatorData = this
        .loadCopyResourcePathSeparatorFromStore(store);
    preferenceModel.setSeparatorData(separatorData);
    preferenceModel.setSelectFileInExplorer(this
        .loadSelectFileInExplorer(store));
    preferenceModel.setAutoDetectDesktopEnvironment(this
        .loadAutoDetectDesktopEnvironment(store));
    preferenceModel.setUseCustomeDesktopEnvironment(this
        .loadUseCustomDesktopEnvironment(store));
    preferenceModel.setSelectedDesktopEnvironment(this
        .loadSelectedDesktopEnvironment(store));
    CustomDesktopEnvironmentContainer customDesktopEnvironmentContainer = this
        .loadCustomDesktopEnvironmentContainerFromStore(store);
    preferenceModel
        .setCustomDesktopEnvironmentContainer(customDesktopEnvironmentContainer);
  }

  private List<CommandConfig> loadCustomCommandsFromStore(IPreferenceStore store)
  {
    List<CommandConfig> commandConfigList = new ArrayList<CommandConfig>();
    int numberOfCustomCommands = store.getInt(KEY_NUMBER_OF_CUSTOM_COMMANDS);
    for (int i = 0; i < numberOfCustomCommands; i++)
    {
      this.migrateCustomCommandFromOlderVersions(store, i);
      String command = store.getString(getCommandKey(i));
      String resourceTypeAsString = store
          .getString(getCommandResourceTypeKey(i));
      ResourceType resourceType = ResourceType.fromName(resourceTypeAsString);
      boolean enabledForResourcesMenu = store
          .getBoolean(getCommandEnabledForResourcesMenuKey(i));
      String nameForResourcesMenu = store
          .getString(getCommandNameForResourcesMenuKey(i));
      boolean enabledForTextSelectionMenu = store
          .getBoolean(getCommandEnabledForTextSelectionMenuKey(i));
      String nameForTextSelectionMenu = store
          .getString(getCommandNameForTextSelectionMenuKey(i));
      boolean passSelectedText = store.getBoolean(getPassSelectedTextKey(i));
      CommandConfig commandConfig = new CommandConfig(command, resourceType,
          enabledForResourcesMenu, nameForResourcesMenu,
          enabledForTextSelectionMenu, nameForTextSelectionMenu,
          passSelectedText);
      commandConfigList.add(commandConfig);
    }
    return commandConfigList;
  }

  private SeparatorData loadCopyResourcePathSeparatorFromStore(
      IPreferenceStore store)
  {
    this.migrateFromOlderVersions(store);
    return new SeparatorData(
        store.getBoolean(KEY_COPY_RESOURCE_PATH_SEPARATOR_IS_CUSTOM),
        store.getString(KEY_COPY_RESOURCE_PATH_SEPARATOR_STANDARD),
        store.getString(KEY_COPY_RESOURCE_PATH_SEPARATOR_CUSTOM_STRING));
  }

  private CustomDesktopEnvironmentContainer loadCustomDesktopEnvironmentContainerFromStore(
      IPreferenceStore store)
  {
    this.migrateFromOlderVersions(store);
    return new CustomDesktopEnvironmentContainer(
        store
            .getString(KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER),
        store
            .getString(KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER_AND_SELECT_FILE),
        WorkingDirectoryMode.valueOf(store
            .getString(KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_FILE_MANAGER)),
        store.getString(KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SHELL),
        WorkingDirectoryMode.valueOf(store
            .getString(KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SHELL)),
        store
            .getString(KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SYSTEM_APPLICATION),
        WorkingDirectoryMode.valueOf(store
            .getString(KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SYSTEM_APPLICATION)),
        WorkingDirectoryMode.valueOf(store
            .getString(KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_CUSTOM_COMMANDS)),
        store
            .getBoolean(KEY_CUSTOM_DESKTOP_ENVIRONMENT_FILE_SELECTION_SUPPORTED),
        store
            .getBoolean(KEY_CUSTOM_DESKTOP_ENVIRONMENT_FILE_PARTS_WANT_WRAPPING));
  }

  private boolean loadSelectFileInExplorer(IPreferenceStore store)
  {
    this.migrateFromOlderVersions(store);
    return store.getBoolean(KEY_SELECT_FILE_IN_EXPLORER);
  }

  private boolean loadAutoDetectDesktopEnvironment(IPreferenceStore store)
  {
    this.migrateFromOlderVersions(store);
    return store.getBoolean(KEY_AUTO_DETECT_DESKTOP_ENVIRONMENT);
  }

  private boolean loadUseCustomDesktopEnvironment(IPreferenceStore store)
  {
    this.migrateFromOlderVersions(store);
    return store.getBoolean(KEY_USE_CUSTOM_DESKTOP_ENVIRONMENT);
  }

  private DesktopEnvironment loadSelectedDesktopEnvironment(
      IPreferenceStore store)
  {
    this.migrateFromOlderVersions(store);
    String selectedDesktopEnvironmentName = store
        .getString(KEY_SELECTED_DESKTOP_ENVIRONMENT);
    return DesktopEnvironment.valueOf(selectedDesktopEnvironmentName);
  }

  /**
   * Migrates preferences from older versions (which didn't have this values) to
   * more recent versions.
   */
  private void migrateFromOlderVersions(IPreferenceStore store)
  {
    // TODO Write a hidden version property to the preference store and read it
    // before migration. Only migrate needed properties depending on the version
    // number read.
    assertBoolean(store, KEY_COPY_RESOURCE_PATH_SEPARATOR_IS_CUSTOM,
        DEFAULT_COPY_RESOURCE_PATH_SEPARATOR_IS_CUSTOM);
    assertString(store, KEY_COPY_RESOURCE_PATH_SEPARATOR_STANDARD,
        DEFAULT_COPY_RESOURCE_PATH_SEPARATOR
            .getStringRepresentationForStandardSeparator());
    assertString(store, KEY_COPY_RESOURCE_PATH_SEPARATOR_CUSTOM_STRING,
        DEFAULT_COPY_RESOURCE_PATH_SEPARATOR_CUSTOM_STRING);

    // Since 0.8
    assertBoolean(store, KEY_SELECT_FILE_IN_EXPLORER,
        DEFAULT_SELECT_FILE_IN_EXPLORER);

    // Since 1.0
    assertBoolean(store, KEY_AUTO_DETECT_DESKTOP_ENVIRONMENT,
        DEFAULT_AUTO_DETECT_DESKTOP_ENVIRONMENT);
    assertBoolean(store, KEY_USE_CUSTOM_DESKTOP_ENVIRONMENT,
        DEFAULT_USE_CUSTOM_DESKTOP_ENVIRONMENT);
    assertString(store, KEY_SELECTED_DESKTOP_ENVIRONMENT,
        DEFAULT_SELECTED_DESKTOP_ENVIRONMENT.name());
    assertString(store,
        KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER,
        DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER);
    assertString(
        store,
        KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER_AND_SELECT_FILE,
        DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_FILE_MANAGER_AND_SELECT_FILE);
    assertString(
        store,
        KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_FILE_MANAGER,
        DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_FILE_MANAGER
            .name());
    assertString(store, KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SHELL,
        DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SHELL);
    assertString(
        store,
        KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SHELL,
        DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SHELL
            .name());
    assertString(store,
        KEY_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SYSTEM_APPLICATION,
        DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_COMMAND_START_SYSTEM_APPLICATION);
    assertString(
        store,
        KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SYSTEM_APPLICATION,
        DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_START_SYSTEM_APPLICATION
            .name());
    assertString(
        store,
        KEY_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_CUSTOM_COMMANDS,
        DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_WORKING_DIRECTORY_MODE_FOR_CUSTOM_COMMANDS
            .name());
    assertBoolean(store,
        KEY_CUSTOM_DESKTOP_ENVIRONMENT_FILE_SELECTION_SUPPORTED,
        DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_FILE_SELECTION_SUPPORTED);
    assertBoolean(store,
        KEY_CUSTOM_DESKTOP_ENVIRONMENT_FILE_PARTS_WANT_WRAPPING,
        DEFAULT_WINDOWS_CUSTOM_DESKTOP_ENVIRONMENT_FILE_PARTS_WANT_WRAPPING);
  }

  /**
   * Migrates preferences from older versions (which didn't have this values) to
   * more recent versions.
   */
  private void migrateCustomCommandFromOlderVersions(IPreferenceStore store,
      int i)
  {
    assertString(store, getCommandResourceTypeKey(i), ResourceType.BOTH.name());
  }

  private void assertBoolean(IPreferenceStore store, String key,
      boolean defaultValue)
  {
    if (!store.contains(key))
    {
      store.setDefault(key, defaultValue);
      store.setValue(key, defaultValue);
    }
  }

  private void assertString(IPreferenceStore store, String key,
      String defaultValue)
  {
    if (!store.contains(key))
    {
      store.setValue(key, defaultValue);
    }
  }

  /**
   * Retrieves the command config list from the preference store
   * 
   * @return the list of custom commands
   */
  public List<CommandConfig> getCommandConfigListFromPreferences()
  {
    return this.loadCustomCommandsFromStore(this.retrievePreferenceStore());
  }

  /**
   * Retrieves the separator for the copy resource path command.
   * 
   * @return the separator for the copy resource path command.
   */
  public String getCopyResourcePathSeparatorStringFromPreferences()
  {
    SeparatorData separatorData = this
        .loadCopyResourcePathSeparatorFromStore(this.retrievePreferenceStore());
    return separatorData.getStringRepresentation();
  }

  public boolean getSelectFileInExplorer()
  {
    return this.loadSelectFileInExplorer(this.retrievePreferenceStore());
  }

  public boolean getAutoDetectDesktopEnvironment()
  {
    return this
        .loadAutoDetectDesktopEnvironment(this.retrievePreferenceStore());
  }

  public boolean getUseCustomDesktopEnvironment()
  {
    return this
        .loadUseCustomDesktopEnvironment(this.retrievePreferenceStore());
  }

  public DesktopEnvironment getSelectedDesktopEnvironment()
  {
    return this.loadSelectedDesktopEnvironment(this.retrievePreferenceStore());
  }

  public CustomDesktopEnvironmentContainer getCustomDesktopEnvironmentContainerFromPreferences()
  {
    return this.loadCustomDesktopEnvironmentContainerFromStore(this
        .retrievePreferenceStore());
  }
}
