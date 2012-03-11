package de.bastiankrol.startexplorer.customcommands;

import static de.bastiankrol.startexplorer.Activator.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.bastiankrol.startexplorer.ResourceType;
import de.bastiankrol.startexplorer.customcommands.CommandConfig.StorageMode;
import de.bastiankrol.startexplorer.util.Util;

/**
 * Imports/exports custom command config definitions from/to shared files. Also
 * deletes shared files, if they are no longer needed.
 * 
 * @author Bastian Krol
 */
public class SharedFileManager
{
  static final String KEY_VERSION = "version";
  static final String VERSION = "1.2.0";
  static final String KEY_COMMAND = "command";
  static final String KEY_RESOURCE_TYPE = "resource type";
  static final String KEY_ENABLED_FOR_RESOURCE_VIEW = "enabled for resource view";
  static final String KEY_NAME_FOR_RESOURCE_VIEW = "name for resource view";
  static final String KEY_ENABLED_FOR_EDITOR = "enabled for editor";
  static final String KEY_NAME_FOR_EDITOR = "name for editor";
  static final String KEY_PASS_SELECTED_TEXT = "pass selected text";
  static final String KEY_STORAGE_OPTION = "store as";
  static final String KEY_SHARED_FILE = "shared file";

  private JSONParser parser;

  public SharedFileManager()
  {
    this.parser = new JSONParser();
  }

  /**
   * Exports {@code commandConfig} to a file in the workpace. The workspace
   * automatically recongnizes the new file.
   * 
   * @param commandConfig the {@link CommandConfig} to export
   * @param file the {@link IFile} to write to
   * @throws CoreException if something goes wrong while exporting
   */
  public void exportCommandConfigToFile(CommandConfig commandConfig, IFile file)
      throws CoreException
  {
    String jsonString = JSONValue
        .toJSONString(commandConfigToJsonObject(commandConfig));
    ByteArrayInputStream inputStream = new ByteArrayInputStream(
        jsonString.getBytes());
    if (file.exists())
    {
      file.setContents(inputStream, true, false, null);
    }
    else
    {
      file.create(inputStream, true, null);
    }
  }

  /**
   * Exports {@code commandConfig} to a file in the file system. Even if that
   * file happens to be in the workspace, the workspace does not automatically
   * recongnizes the new file.
   * 
   * @param commandConfig the {@link CommandConfig} to export
   * @param file the {@link File} to write to
   * @throws IOException if something goes wrong while exporting
   */
  public void exportCommandConfigToFile(CommandConfig commandConfig, File file)
      throws IOException
  {
    BufferedWriter writer = null;
    try
    {
      writer = new BufferedWriter(new FileWriter(file));
      JSONValue.writeJSONString(commandConfigToJsonObject(commandConfig),
          writer);
    }
    finally
    {
      try
      {
        writer.close();
      }
      catch (IOException e)
      {
        getLogFacility().logException(
            "IOException while closing export writer.", e);
      }
    }
  }

  String convertToJsonString(CommandConfig commandConfig)
  {
    Map<String, Object> export = commandConfigToJsonObject(commandConfig);
    return JSONValue.toJSONString(export);
  }

  private Map<String, Object> commandConfigToJsonObject(
      CommandConfig commandConfig)
  {
    Map<String, Object> export = new LinkedHashMap<String, Object>();
    export.put(KEY_VERSION, VERSION);
    export.put(KEY_COMMAND, commandConfig.getCommand());
    ResourceType resourceType = commandConfig.getResourceType();
    if (resourceType != null)
    {
      export.put(KEY_RESOURCE_TYPE, resourceType.name());
    }
    else
    {
      export.put(KEY_RESOURCE_TYPE, null);
    }
    export.put(KEY_ENABLED_FOR_RESOURCE_VIEW,
        commandConfig.isEnabledForResourcesMenu());
    export.put(KEY_NAME_FOR_RESOURCE_VIEW,
        commandConfig.getNameForResourcesMenu());
    export.put(KEY_ENABLED_FOR_EDITOR,
        commandConfig.isEnabledForTextSelectionMenu());
    export
        .put(KEY_NAME_FOR_EDITOR, commandConfig.getNameForTextSelectionMenu());
    export.put(KEY_PASS_SELECTED_TEXT, commandConfig.isPassSelectedText());
    export.put(KEY_STORAGE_OPTION, commandConfig.getStorageMode().name());
    export.put(KEY_SHARED_FILE, commandConfig.getSharedFilePath());
    return export;
  }

  /**
   * Imports a {@link CommandConfig} from a file in the file system.
   * 
   * @param file the {@link File} to read from
   * @return the imported commandConfig
   * @throws IOException if something goes wrong while importing
   * @throws ParseException if something goes wrong while importing
   */
  public CommandConfig importCommandConfigFromFile(File file)
      throws IOException, ParseException
  {
    BufferedReader reader = new BufferedReader(new FileReader(file));
    try
    {
      return toCommandConfig(this.parser.parse(reader));

    }
    finally
    {
      try
      {
        reader.close();
      }
      catch (IOException e)
      {
        getLogFacility().logException(
            "IOException while closing import reader.", e);
      }
    }
  }

  public CommandConfig convertToCommandConfig(String json)
      throws ParseException
  {
    return toCommandConfig(this.parser.parse(json));
  }

  private CommandConfig toCommandConfig(Object parsingResult)
  {
    if (!(parsingResult instanceof JSONObject))
    {
      throw new IllegalArgumentException(
          "Input was not parsed to a JSONObject, but to a "
              + parsingResult.getClass().getName() + ".");
    }
    JSONObject jsonObject = (JSONObject) parsingResult;
    CommandConfig commandConfig = new CommandConfig(
        (String) jsonObject.get(KEY_COMMAND),
        ResourceType.fromName((String) jsonObject.get(KEY_RESOURCE_TYPE)),
        parseBoolean(jsonObject, KEY_ENABLED_FOR_RESOURCE_VIEW, true),
        (String) jsonObject.get(KEY_NAME_FOR_RESOURCE_VIEW), parseBoolean(
            jsonObject, KEY_ENABLED_FOR_EDITOR, true),
        (String) jsonObject.get(KEY_NAME_FOR_EDITOR), parseBoolean(jsonObject,
            KEY_PASS_SELECTED_TEXT, false));

    convertStorageMode(jsonObject, commandConfig);
    return commandConfig;
  }

  private void convertStorageMode(JSONObject jsonObject,
      CommandConfig commandConfig)
  {
    String storageModeAsString = (String) jsonObject.get(KEY_STORAGE_OPTION);
    if (storageModeAsString != null)
    {
      try
      {
        StorageMode storageMode = StorageMode.valueOf(storageModeAsString);
        commandConfig.setStorageMode(storageMode);
      }
      catch (IllegalArgumentException e)
      {
        getLogFacility().logException(
            "Unknown storage mode: " + storageModeAsString, e);
      }
    }
    String sharedFilePath = (String) jsonObject.get(KEY_SHARED_FILE);
    if (sharedFilePath != null)
    {
      commandConfig.setSharedFilePath(sharedFilePath);
    }
  }

  private boolean parseBoolean(JSONObject jsonObject, String key,
      boolean defaultValue)
  {
    Boolean value = (Boolean) jsonObject.get(key);
    if (value != null)
    {
      return value;
    }
    else
    {
      return defaultValue;
    }
  }

  /**
   * Deletes the command config (which must be stored as a shared file) from the
   * file system.
   * 
   * @param commandConfig
   * @throws IllegalArgumentException if the commandConfig is not stored as a
   *           shared file
   */
  public void delete(CommandConfig commandConfig)
  {
    if (!commandConfig.isStoreAsSharedFile())
    {
      throw new IllegalArgumentException(
          "Can't delete a custom config which is not stored as a shared file from the file system.");
    }
    String sharedFilePath = commandConfig.getSharedFilePath();
    IFile file = Util.getIFileInWorkspace(sharedFilePath);
    try
    {
      file.delete(true, null);
    }
    catch (CoreException e)
    {
      getPluginContext()
          .getLogFacility()
          .logException(
              "A CoreException occured while trying to delete a shared command config.",
              e);
    }
  }
}
