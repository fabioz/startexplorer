package de.bastiankrol.startexplorer.preferences;

import static de.bastiankrol.startexplorer.preferences.PreferenceConstantsAndDefaults.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;

import de.bastiankrol.startexplorer.crossplatform.CustomDesktopEnvironmentContainer;
import de.bastiankrol.startexplorer.crossplatform.DesktopEnvironment;
import de.bastiankrol.startexplorer.customcommands.CommandConfig;

/**
 * Value class for all preferences
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$
 */
public class PreferenceModel
{

  /**
   * List of command configs
   */
  private List<CommandConfig> commandConfigList;

  private SeparatorData separatorData;

  private boolean selectFileInExplorer;

  private boolean autoDetectDesktopEnvironment;
  private boolean useCustomeDesktopEnvironment;
  private DesktopEnvironment selectedDesktopEnvironment;

  private CustomDesktopEnvironmentContainer customDesktopEnvironmentContainer;

  /**
   * Constructor
   */
  public PreferenceModel()
  {
    this.commandConfigList = new ArrayList<CommandConfig>();
    this.separatorData = new SeparatorData();
    this.customDesktopEnvironmentContainer = new CustomDesktopEnvironmentContainer();
  }

  /**
   * Returns the command config list
   * 
   * @return the command config list
   */
  public List<CommandConfig> getCommandConfigList()
  {
    return this.commandConfigList;
  }

  /**
   * Sets the command config list
   * 
   * @param commandConfigList the command config list
   */
  public void setCommandConfigList(List<CommandConfig> commandConfigList)
  {
    this.commandConfigList = commandConfigList;
  }

  public SeparatorData getSeparatorData()
  {
    return this.separatorData;
  }

  public void setSeparatorData(SeparatorData separatorData)
  {
    this.separatorData = separatorData;
  }

  public boolean isSelectFileInExplorer()
  {
    return this.selectFileInExplorer;
  }

  public void setSelectFileInExplorer(boolean selectFileInExplorer)
  {
    this.selectFileInExplorer = selectFileInExplorer;
  }

  public boolean isAutoDetectDesktopEnvironment()
  {
    return this.autoDetectDesktopEnvironment;
  }

  public void setAutoDetectDesktopEnvironment(
      boolean autoDetectDesktopEnvironment)
  {
    this.autoDetectDesktopEnvironment = autoDetectDesktopEnvironment;
  }

  public boolean isUseCustomeDesktopEnvironment()
  {
    return this.useCustomeDesktopEnvironment;
  }

  public void setUseCustomeDesktopEnvironment(
      boolean useCustomeDesktopEnvironment)
  {
    this.useCustomeDesktopEnvironment = useCustomeDesktopEnvironment;
  }

  public DesktopEnvironment getSelectedDesktopEnvironment()
  {
    return this.selectedDesktopEnvironment;
  }

  public void setSelectedDesktopEnvironment(
      DesktopEnvironment desktopEnvironment)
  {
    this.selectedDesktopEnvironment = desktopEnvironment;
  }

  public CustomDesktopEnvironmentContainer getCustomDesktopEnvironmentContainer()
  {
    return customDesktopEnvironmentContainer;
  }

  public void setCustomDesktopEnvironmentContainer(
      CustomDesktopEnvironmentContainer customDesktopEnvironmentContainer)
  {
    this.customDesktopEnvironmentContainer = customDesktopEnvironmentContainer;
  }

  public synchronized void initializeFromDefaults()
  {
    // create a new list by Arrays.asList(DEF..), otherwise changes to the
    // list would write through to the default array, which is not what we want.
    this.commandConfigList = new ArrayList<CommandConfig>(
        Arrays.asList(DEFAULT_CUSTOM_COMMANDS));
    this.separatorData.initializeFromDefaults();
    this.selectFileInExplorer = DEFAULT_SELECT_FILE_IN_EXPLORER;
    this.autoDetectDesktopEnvironment = DEFAULT_AUTO_DETECT_DESKTOP_ENVIRONMENT_FOR_NEW_USERS;
    this.useCustomeDesktopEnvironment = DEFAULT_USE_CUSTOM_DESKTOP_ENVIRONMENT;
    this.selectedDesktopEnvironment = DEFAULT_SELECTED_DESKTOP_ENVIRONMENT_FOR_NEW_USERS;
    this.customDesktopEnvironmentContainer
        .initializeFromDefaults(this.selectedDesktopEnvironment
            .getOperatingSystem());
  }

  /**
   * Store the values to <code>store</code>.
   * 
   * @param store the {@link IPreferenceStore} to store the preferences in.
   */
  public synchronized void storeValues(IPreferenceStore store)
  {
    store
        .setValue(KEY_NUMBER_OF_CUSTOM_COMMANDS, this.commandConfigList.size());
    for (int i = 0; i < this.commandConfigList.size(); i++)
    {
      CommandConfig commandConfig = this.commandConfigList.get(i);
      store.setValue(getCommandKey(i), commandConfig.getCommand());
      store.setValue(getCommandResourceTypeKey(i), commandConfig
          .getResourceType().name());
      store.setValue(getCommandEnabledForResourcesMenuKey(i),
          commandConfig.isEnabledForResourcesMenu());
      store.setValue(getCommandNameForResourcesMenuKey(i),
          commandConfig.getNameForResourcesMenu());
      store.setValue(getCommandEnabledForTextSelectionMenuKey(i),
          commandConfig.isEnabledForTextSelectionMenu());
      store.setValue(getCommandNameForTextSelectionMenuKey(i),
          commandConfig.getNameForTextSelectionMenu());
      store.setValue(getPassSelectedTextKey(i),
          commandConfig.isPassSelectedText());
    }
    this.separatorData.storeValues(store);
    store.setValue(KEY_SELECT_FILE_IN_EXPLORER, this.selectFileInExplorer);
    store.setValue(KEY_AUTO_DETECT_DESKTOP_ENVIRONMENT,
        this.autoDetectDesktopEnvironment);
    store.setValue(KEY_USE_CUSTOM_DESKTOP_ENVIRONMENT,
        this.useCustomeDesktopEnvironment);
    store.setValue(KEY_SELECTED_DESKTOP_ENVIRONMENT,
        this.selectedDesktopEnvironment.name());
    this.customDesktopEnvironmentContainer.storeValues(store);
  }

  /**
   * Loads the preferences from the eclipse preference store into this model
   * 
   * @param preferenceUtil the {@link PreferenceUtil} object that will be used
   *          to load the preferences from the store
   */
  public synchronized void loadPreferencesFromStore(
      PreferenceUtil preferenceUtil)
  {
    preferenceUtil.loadPreferencesFromStoreIntoPreferenceModel(this);
  }
}
