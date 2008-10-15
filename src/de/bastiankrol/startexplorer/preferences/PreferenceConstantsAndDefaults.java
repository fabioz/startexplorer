package de.bastiankrol.startexplorer.preferences;

import static de.bastiankrol.startexplorer.util.Util.*;

public class PreferenceConstantsAndDefaults
{

  public static final String KEY_NUMBER_OF_CUSTOM_COMMANDS = "de.bastiankrol.startexplorer.bastiankrol.number_of_commands";
  private static final String KEY_CUSTOM_COMMAND = "de.bastiankrol.startexplorer.command_";
  private static final String KEY_CUSTOM_COMMAND_ENABLED_FOR_RESOURCES = "de.bastiankrol.startexplorer.command_enabled_for_resources_";
  private static final String KEY_CUSTOM_COMMAND_NAME_FOR_RESOURCES = "de.bastiankrol.startexplorer.command_name_resources_";
  private static final String KEY_CUSTOM_COMMAND_ENABLED_FOR_TEXT_SELECTION= "de.bastiankrol.startexplorer.command_enabled_for_text_selection_";
  private static final String KEY_CUSTOM_COMMAND_NAME_FOR_TEXT_SELECTION= "de.bastiankrol.startexplorer.command_name_text_selection_";

  public static final CommandConfig[] DEFAULT_CUSTOM_COMMANDS = new CommandConfig[] {
      new CommandConfig("uedit32 ${resource_path}", true, "UltraEdit", true, "UltraEdit"),
      new CommandConfig("notepad ${resource_path}", true, "Notepad", true, "Notepad"),
  };

  public static String getCommandKey(int i)
  {
    return getCommandKey(KEY_CUSTOM_COMMAND, i);
  }

  public static String getCommandEnabledForResourcesMenuKey(int i)
  {
    return getCommandKey(KEY_CUSTOM_COMMAND_ENABLED_FOR_RESOURCES, i);
  }

  public static String getCommandNameForResourcesMenuKey(int i)
  {
    return getCommandKey(KEY_CUSTOM_COMMAND_NAME_FOR_RESOURCES, i);
  }

  public static String getCommandEnabledForTextSelectionMenuKey(int i)
  {
    return getCommandKey(KEY_CUSTOM_COMMAND_ENABLED_FOR_TEXT_SELECTION, i);
  }

  public static String getCommandNameForTextSelectionMenuKey(int i)
  {
    return getCommandKey(KEY_CUSTOM_COMMAND_NAME_FOR_TEXT_SELECTION, i);
  }

  private static String getCommandKey(String keyPrefix, int i)
  {
    return keyPrefix + intToString(i);
  }

}
