package de.bastiankrol.startexplorer.preferences;

import static de.bastiankrol.startexplorer.preferences.PreferenceConstantsAndDefaults.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;

import de.bastiankrol.startexplorer.Activator;
import de.bastiankrol.startexplorer.ResourceType;
import de.bastiankrol.startexplorer.customcommands.CommandConfig;

/**
 * Utility class for accessing the eclipse preference store
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$
 */
public class PreferenceUtil
{

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
    this.loadCustomCommandsFromStore(store,
        preferenceModel.getCommandConfigList());
    SeparatorData separatorData = this
        .loadCopyResourcePathSeparatorFromStore(store);
    preferenceModel.setSeparatorData(separatorData);
    preferenceModel.setSelectFileInExplorer(this
        .loadSelectFileInExplorer(store));
  }

  private void loadCustomCommandsFromStore(IPreferenceStore store,
      List<CommandConfig> commandConfigList)
  {
    int numberOfCustomCommands = store.getInt(KEY_NUMBER_OF_CUSTOM_COMMANDS);
    for (int i = 0; i < numberOfCustomCommands; i++)
    {
      this.migrateCustomCommandFromOlderVersions(store, i);
      String command = store.getString(getCommandKey(i));
      String resourceTypeAsString = store.getString(getCommandResourceTypeKey(i));
      ResourceType resourceType = ResourceType.fromString(resourceTypeAsString);
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
  }

  private SeparatorData loadCopyResourcePathSeparatorFromStore(
      IPreferenceStore store)
  {

    this.migrateFromOlderVersions(store);
    SeparatorData separatorData = new SeparatorData(
        store.getBoolean(KEY_COPY_RESOURCE_PATH_SEPARATOR_IS_CUSTOM),
        store.getString(KEY_COPY_RESOURCE_PATH_SEPARATOR_STANDARD),
        store.getString(KEY_COPY_RESOURCE_PATH_SEPARATOR_CUSTOM_STRING));
    return separatorData;
  }

  private boolean loadSelectFileInExplorer(IPreferenceStore store)
  {
    this.migrateFromOlderVersions(store);
    return store.getBoolean(KEY_SELECT_FILE_IN_EXPLORER);
  }

  /**
   * Migrates preferences from older versions (which didn't have this values) to
   * more recent versions.
   */
  private void migrateFromOlderVersions(IPreferenceStore store)
  {
    if (!store.contains(KEY_COPY_RESOURCE_PATH_SEPARATOR_IS_CUSTOM))
    {
      store.setValue(KEY_COPY_RESOURCE_PATH_SEPARATOR_IS_CUSTOM, false);
    }
    if (!store.contains(KEY_COPY_RESOURCE_PATH_SEPARATOR_STANDARD))
    {
      store.setValue(KEY_COPY_RESOURCE_PATH_SEPARATOR_STANDARD,
          DEFAULT_COPY_RESOURCE_PATH_SEPARATOR
              .getStringRepresentationForStandardSeparator());
    }
    if (!store.contains(KEY_COPY_RESOURCE_PATH_SEPARATOR_CUSTOM_STRING))
    {
      store.setValue(KEY_COPY_RESOURCE_PATH_SEPARATOR_CUSTOM_STRING,
          DEFAULT_CUSTOM_COPY_RESOURCE_PATH_SEPARATOR_STRING);
    }
    if (!store.contains(KEY_SELECT_FILE_IN_EXPLORER))
    {
      store.setValue(KEY_SELECT_FILE_IN_EXPLORER,
          DEFAULT_SELECT_FILE_IN_EXPLORER);
    }
  }

  /**
   * Migrates preferences from older versions (which didn't have this values) to
   * more recent versions.
   */
  private void migrateCustomCommandFromOlderVersions(IPreferenceStore store, int i)
  {
    if (!store.contains(getCommandResourceTypeKey(i)))
    {
      store.setValue(getCommandResourceTypeKey(i), ResourceType.BOTH.name());
    }
  }
  
  /**
   * Retrieves the command config list from the preference store
   * 
   * @return the list of custom commands
   */
  public List<CommandConfig> getCommandConfigListFromPreferences()
  {
    List<CommandConfig> commandConfigList = new ArrayList<CommandConfig>();
    this.loadCustomCommandsFromStore(this.retrievePreferenceStore(),
        commandConfigList);
    return commandConfigList;
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
}
