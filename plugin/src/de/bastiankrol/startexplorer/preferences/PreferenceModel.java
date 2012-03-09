package de.bastiankrol.startexplorer.preferences;

import static de.bastiankrol.startexplorer.Activator.*;
import static de.bastiankrol.startexplorer.preferences.PreferenceConstantsAndDefaults.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;

import de.bastiankrol.startexplorer.Activator;
import de.bastiankrol.startexplorer.crossplatform.CustomDesktopEnvironmentContainer;
import de.bastiankrol.startexplorer.crossplatform.DesktopEnvironment;
import de.bastiankrol.startexplorer.customcommands.CommandConfig;
import de.bastiankrol.startexplorer.customcommands.SharedFileFinder;
import de.bastiankrol.startexplorer.customcommands.SharedFileManager;
import de.bastiankrol.startexplorer.util.Util;

/**
 * Represents the preferences for the StartExplorer plug-in. All access to the
 * preferences and the preference store goes through this class. In addition,
 * this class also offers access to the custom command definitions that are
 * stored as shared files in the workspace (although technically these are not
 * preferences).
 * 
 * This class makes no guarantee that the preferences actually have been loaded
 * from the preference store and it does not load the preferences at
 * construction time. Clients need to make sure that the preferences are loaded
 * by calling {@link #loadPreferencesFromStore()} before using this class.
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$
 */
public class PreferenceModel
{
  private PreferenceUtil preferenceUtil;

  /**
   * List of command configs
   */
  private List<CommandConfig> commandConfigList;
  private boolean customCommandsFromSharedFileHaveBeenAdded;

  private SeparatorData separatorData;

  private boolean selectFileInExplorer;

  private boolean autoDetectDesktopEnvironment;
  private boolean useCustomeDesktopEnvironment;
  private DesktopEnvironment selectedDesktopEnvironment;

  private CustomDesktopEnvironmentContainer customDesktopEnvironmentContainer;

  private SharedFileManager sharedFileManager;

  /**
   * Constructor
   */
  public PreferenceModel()
  {
    this.preferenceUtil = new PreferenceUtil();
    this.commandConfigList = new ArrayList<CommandConfig>();
    this.separatorData = new SeparatorData();
    this.customDesktopEnvironmentContainer = new CustomDesktopEnvironmentContainer();
    this.sharedFileManager = new SharedFileManager();
  }

  /**
   * Returns the command config list
   * 
   * @return the command config list
   */
  public List<CommandConfig> getCommandConfigList()
  {
    if (!this.customCommandsFromSharedFileHaveBeenAdded)
    {
      this.addCustomCommandsFromSharedFiles(this.commandConfigList);
    }
    return this.commandConfigList;
  }

  public boolean customCommandsFromSharedFileHaveBeenAdded()
  {
    return customCommandsFromSharedFileHaveBeenAdded;
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

  public String getCopyResourcePathSeparatorStringFromPreferences()
  {
    return this.separatorData.getStringRepresentation();
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
    this.autoDetectDesktopEnvironment = DEFAULT_AUTO_DETECT_DESKTOP_ENVIRONMENT;
    this.useCustomeDesktopEnvironment = DEFAULT_USE_CUSTOM_DESKTOP_ENVIRONMENT;
    this.selectedDesktopEnvironment = DEFAULT_SELECTED_DESKTOP_ENVIRONMENT;
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
    storeCustomCommands(store);

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

  public synchronized void storeCustomCommands(IPreferenceStore store)
  {
    int index = 0;
    for (CommandConfig commandConfig : this.commandConfigList)
    {
      if (commandConfig.isStoreInPreferences())
      {
        store.setValue(getCommandKey(index), commandConfig.getCommand());
        store.setValue(getCommandResourceTypeKey(index), commandConfig
            .getResourceType().name());
        store.setValue(getCommandEnabledForResourcesMenuKey(index),
            commandConfig.isEnabledForResourcesMenu());
        store.setValue(getCommandNameForResourcesMenuKey(index),
            commandConfig.getNameForResourcesMenu());
        store.setValue(getCommandEnabledForTextSelectionMenuKey(index),
            commandConfig.isEnabledForTextSelectionMenu());
        store.setValue(getCommandNameForTextSelectionMenuKey(index),
            commandConfig.getNameForTextSelectionMenu());
        store.setValue(getPassSelectedTextKey(index),
            commandConfig.isPassSelectedText());
        index++;
      }
      else
      {
        String sharedFilePathAsString = commandConfig.getSharedFilePath();
        IFile file = Util.getIFileInWorkspace(sharedFilePathAsString);
        try
        {
          this.sharedFileManager.exportCommandConfigToFile(commandConfig, file);
        }
        catch (CoreException e)
        {
          Activator.logException(
              "Shared custom command " + commandConfig.toString()
                  + " could not be exported to file "
                  + file.getLocation().toString() + ".", e);
        }
      }
    }
    store.setValue(KEY_NUMBER_OF_CUSTOM_COMMANDS, index);
  }

  /**
   * Loads the preferences from the Eclipse preference store into this model.
   * This method also adds the custom command that have been found in the
   * workspace as shared &quot;.startexplorer&quot; files to the list of command
   * definitions, although technically they have nothing to do with the
   * preference model.
   */
  public synchronized void loadPreferencesFromStore()
  {
    this.preferenceUtil.loadPreferencesFromStoreIntoPreferenceModel(this);
  }

  private void addCustomCommandsFromSharedFiles(
      List<CommandConfig> commandConfigList)
  {
    Activator.logDebug("addCustomCommandsFromSharedFiles - start");
    SharedFileFinder sharedFileFinder = getPluginContext()
        .getSharedFileFinder();
    if (sharedFileFinder.hasFinished())
    {
      List<CommandConfig> commandConfigsFromSharedFiles = sharedFileFinder
          .getResult();
      commandConfigList.addAll(commandConfigsFromSharedFiles);
      this.customCommandsFromSharedFileHaveBeenAdded = true;
      Activator.logDebug("Added custom command configs from shared files.");
    }
    else
    {
      this.customCommandsFromSharedFileHaveBeenAdded = false;
      Activator
          .logWarning("Could not add custom command configs from shared files because search job has not finished yet.");
    }
  }

  public IPreferenceStore getPreferenceStore()
  {
    return this.preferenceUtil.getPreferenceStore();
  }
}
