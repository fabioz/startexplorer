package de.bastiankrol.startexplorer.customcommands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.bastiankrol.startexplorer.Activator;
import de.bastiankrol.startexplorer.ResourceType;

public class JsonConverter
{

  static final String KEY_VERSION = "version";
  static final String VERSION = "1.1.1";
  static final String KEY_COMMAND = "command";
  static final String KEY_RESOURCE_TYPE = "resource type";
  static final String KEY_ENABLED_FOR_RESOURCE_VIEW = "enabled for resource view";
  static final String KEY_NAME_FOR_RESOURCE_VIEW = "name for resource view";
  static final String KEY_ENABLED_FOR_EDITOR = "enabled for editor";
  static final String KEY_NAME_FOR_EDITOR = "name for editor";
  static final String KEY_PASS_SELECTED_TEXT = "pass selected text";

  private JSONParser parser;

  public JsonConverter()
  {
    this.parser = new JSONParser();
  }

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
        Activator.logException("IOException while closing export writer.", e);
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
    return export;
  }

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
        Activator.logException("IOException while closing import reader.", e);
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
    return new CommandConfig((String) jsonObject.get(KEY_COMMAND),
        ResourceType.fromName((String) jsonObject.get(KEY_RESOURCE_TYPE)),
        parseBoolean(jsonObject, KEY_ENABLED_FOR_RESOURCE_VIEW, true),
        (String) jsonObject.get(KEY_NAME_FOR_RESOURCE_VIEW), parseBoolean(
            jsonObject, KEY_ENABLED_FOR_EDITOR, true),
        (String) jsonObject.get(KEY_NAME_FOR_EDITOR), parseBoolean(jsonObject,
            KEY_PASS_SELECTED_TEXT, false));
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
}
