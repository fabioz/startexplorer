package de.bastiankrol.startexplorer.preferences;

import static de.bastiankrol.startexplorer.preferences.PreferenceConstantsAndDefaults.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;

import de.bastiankrol.startexplorer.Activator;

public class PreferenceUtil
{

  static IPreferenceStore retrievePreferenceStore()
  {
    return Activator.getDefault().getPreferenceStore();
  }

  static void fillCommandConfigListFromPreferences(
      List<CommandConfig> commandConfigList)
  {
    IPreferenceStore store = retrievePreferenceStore();
    int numberOfCustomCommands = store.getInt(KEY_NUMBER_OF_CUSTOM_COMMANDS);
    for (int i = 0; i < numberOfCustomCommands; i++)
    {
      String command = store.getString(getCommandKey(i));
      boolean enabledForResourcesMenu = store
          .getBoolean(getCommandEnabledForResourcesMenuKey(i));
      String nameForResourcesMenu = store
          .getString(getCommandNameForResourcesMenuKey(i));
      boolean enabledForTextSelectionMenu = store
          .getBoolean(getCommandEnabledForTextSelectionMenuKey(i));
      String nameForTextSelectionMenu = store
          .getString(getCommandNameForTextSelectionMenuKey(i));
      CommandConfig commandConfig = new CommandConfig(command,
          enabledForResourcesMenu, nameForResourcesMenu,
          enabledForTextSelectionMenu, nameForTextSelectionMenu);
      commandConfigList.add(commandConfig);
    }
  }

  public static List<CommandConfig> getCommandConfigListFromPreferences()
  {
    List<CommandConfig> commandConfigList = new ArrayList<CommandConfig>();
    fillCommandConfigListFromPreferences(commandConfigList);
    return commandConfigList;
  }
}
