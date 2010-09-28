package de.bastiankrol.startexplorer.preferences;

import static de.bastiankrol.startexplorer.preferences.PreferenceConstantsAndDefaults.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;

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

  /**
   * Constructor
   */
  public PreferenceModel()
  {
    this.commandConfigList = new ArrayList<CommandConfig>();
    this.separatorData = new SeparatorData();
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

  void initializeFromDefaults()
  {
    // create a new list by Arrays.asList(DEF..), otherwise changes to the
    // list would write through to the default array, which is not what we want.
    this.commandConfigList =
        new ArrayList<CommandConfig>(Arrays.asList(DEFAULT_CUSTOM_COMMANDS));
    this.separatorData.initializeFromDefaults();
  }

  /**
   * Store the values to <code>store</code>.
   * 
   * @param store the {@link IPreferenceStore} to store the preferences in.
   */
  public void storeValues(IPreferenceStore store)
  {
    store
        .setValue(KEY_NUMBER_OF_CUSTOM_COMMANDS, this.commandConfigList.size());
    for (int i = 0; i < this.commandConfigList.size(); i++)
    {
      CommandConfig commandConfig = this.commandConfigList.get(i);
      store.setValue(getCommandKey(i), commandConfig.getCommand());
      store.setValue(getCommandEnabledForResourcesMenuKey(i), commandConfig
          .isEnabledForResourcesMenu());
      store.setValue(getCommandNameForResourcesMenuKey(i), commandConfig
          .getNameForResourcesMenu());
      store.setValue(getCommandEnabledForTextSelectionMenuKey(i), commandConfig
          .isEnabledForTextSelectionMenu());
      store.setValue(getCommandNameForTextSelectionMenuKey(i), commandConfig
          .getNameForTextSelectionMenu());
      store.setValue(getPassSelectedTextKey(i), commandConfig
          .isPassSelectedText());
    }
    this.separatorData.storeValues(store);
  }

  /**
   * Loads the preferences from the eclipse preference store into this model
   * 
   * @param preferenceUtil the {@link PreferenceUtil} object that will be used
   *          to load the preferences from the store
   */
  public void loadPreferencesFromStore(PreferenceUtil preferenceUtil)
  {
    preferenceUtil.loadPreferencesFromStoreIntoPreferenceModel(this);
  }
}
