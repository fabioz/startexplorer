package de.bastiankrol.startexplorer.customcommands;

import static de.bastiankrol.startexplorer.customcommands.JsonConverter.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import de.bastiankrol.startexplorer.ResourceType;

public class JsonConverterTest
{
  private JsonConverter converter;
  private CommandConfig commandConfig;

  @Before
  public void before()
  {
    this.converter = new JsonConverter();
    this.commandConfig = new CommandConfig("nautilus ${resource_path}",
        ResourceType.BOTH, true, "open nautilus", true,
        "open nautilus from editor", false);
  }

  @Test
  public void shouldExport()
  {
    String expected = "{"//
        + "\"" + KEY_VERSION + "\":\"" + VERSION + "\"," //
        + "\"" + KEY_COMMAND + "\":\"nautilus ${resource_path}\"," //
        + "\"" + KEY_RESOURCE_TYPE + "\":\"BOTH\"," //
        + "\"" + KEY_ENABLED_FOR_RESOURCE_VIEW + "\":true," //
        + "\"" + KEY_NAME_FOR_RESOURCE_VIEW + "\":\"open nautilus\"," //
        + "\"" + KEY_ENABLED_FOR_EDITOR + "\":true," //
        + "\"" + KEY_NAME_FOR_EDITOR + "\":\"open nautilus from editor\"," //
        + "\"" + KEY_PASS_SELECTED_TEXT + "\":false}"; //
    String actual = this.converter.convertToJsonString(this.commandConfig);
    assertEquals(expected, actual);
  }

  @Test
  public void shouldExportEmptyStrings()
  {
    this.commandConfig.setCommand("");
    this.commandConfig.setNameForResourcesMenu("");
    this.commandConfig.setNameForTextSelectionMenu("");
    String expected = "{"//
        + "\"" + KEY_VERSION + "\":\"" + VERSION + "\"," //
        + "\"" + KEY_COMMAND + "\":\"\"," //
        + "\"" + KEY_RESOURCE_TYPE + "\":\"BOTH\"," //
        + "\"" + KEY_ENABLED_FOR_RESOURCE_VIEW + "\":true," //
        + "\"" + KEY_NAME_FOR_RESOURCE_VIEW + "\":\"\"," //
        + "\"" + KEY_ENABLED_FOR_EDITOR + "\":true," //
        + "\"" + KEY_NAME_FOR_EDITOR + "\":\"\"," //
        + "\"" + KEY_PASS_SELECTED_TEXT + "\":false}"; //
    String actual = this.converter.convertToJsonString(this.commandConfig);
    assertEquals(expected, actual);
  }

  @Test
  public void shouldExportNullValues()
  {
    this.commandConfig.setCommand(null);
    this.commandConfig.setResourceType(null);
    this.commandConfig.setNameForResourcesMenu(null);
    this.commandConfig.setNameForTextSelectionMenu(null);
    String expected = "{"//
        + "\"" + KEY_VERSION + "\":\"" + VERSION + "\"," //
        + "\"" + KEY_COMMAND + "\":null," //
        + "\"" + KEY_RESOURCE_TYPE + "\":null," //
        + "\"" + KEY_ENABLED_FOR_RESOURCE_VIEW + "\":true," //
        + "\"" + KEY_NAME_FOR_RESOURCE_VIEW + "\":null," //
        + "\"" + KEY_ENABLED_FOR_EDITOR + "\":true," //
        + "\"" + KEY_NAME_FOR_EDITOR + "\":null," //
        + "\"" + KEY_PASS_SELECTED_TEXT + "\":false}"; //
    String actual = this.converter.convertToJsonString(this.commandConfig);
    assertEquals(expected, actual);
  }

  @Test
  public void shouldExportedEmptyCommandConfig()
  {
    this.commandConfig = new CommandConfig();
    String expected = "{"//
        + "\"" + KEY_VERSION + "\":\"" + VERSION + "\"," //
        + "\"" + KEY_COMMAND + "\":\"\"," //
        + "\"" + KEY_RESOURCE_TYPE + "\":\"BOTH\"," //
        + "\"" + KEY_ENABLED_FOR_RESOURCE_VIEW + "\":true," //
        + "\"" + KEY_NAME_FOR_RESOURCE_VIEW + "\":\"\"," //
        + "\"" + KEY_ENABLED_FOR_EDITOR + "\":true," //
        + "\"" + KEY_NAME_FOR_EDITOR + "\":\"\"," //
        + "\"" + KEY_PASS_SELECTED_TEXT + "\":false}"; //
    String actual = this.converter.convertToJsonString(this.commandConfig);
    assertEquals(expected, actual);
  }

  @Test
  public void shouldExportedQuotesAndBackslashesInCommand()
  {
    this.commandConfig
        .setCommand("command with \"quotes\"\" and back \\ slashes \\");
    String expected = "{"//
        + "\"" + KEY_VERSION + "\":\"" + VERSION
        + "\"," //
        + "\"" + KEY_COMMAND
        + "\":\"command with \\\"quotes\\\"\\\" and back \\\\ slashes \\\\\"," //
        + "\"" + KEY_RESOURCE_TYPE + "\":\"BOTH\"," //
        + "\"" + KEY_ENABLED_FOR_RESOURCE_VIEW + "\":true," //
        + "\"" + KEY_NAME_FOR_RESOURCE_VIEW + "\":\"open nautilus\"," //
        + "\"" + KEY_ENABLED_FOR_EDITOR + "\":true," //
        + "\"" + KEY_NAME_FOR_EDITOR + "\":\"open nautilus from editor\"," //
        + "\"" + KEY_PASS_SELECTED_TEXT + "\":false}"; //
    String actual = this.converter.convertToJsonString(this.commandConfig);
    assertEquals(expected, actual);
  }

  @Test
  public void shouldImport() throws Exception
  {
    String json = "{"//
        + "\"" + KEY_VERSION + "\":\"" + VERSION + "\"," //
        + "\"" + KEY_COMMAND + "\":\"nautilus ${resource_path}\"," //
        + "\"" + KEY_RESOURCE_TYPE + "\":\"BOTH\"" //
        + "\"" + KEY_ENABLED_FOR_RESOURCE_VIEW + "\":true," //
        + "\"" + KEY_NAME_FOR_RESOURCE_VIEW + "\":\"open nautilus\"," //
        + "\"" + KEY_ENABLED_FOR_EDITOR + "\":true," //
        + "\"" + KEY_NAME_FOR_EDITOR + "\":\"open nautilus from editor\"," //
        + "\"" + KEY_PASS_SELECTED_TEXT + "\":false" //
        + "}";
    this.commandConfig = this.converter.convertToCommandConfig(json);
    assertThat(this.commandConfig, notNullValue());
    assertThat(this.commandConfig.getCommand(),
        equalTo("nautilus ${resource_path}"));
    assertThat(this.commandConfig.getResourceType(), equalTo(ResourceType.BOTH));
    assertTrue(this.commandConfig.isEnabledForResourcesMenu());
    assertThat(this.commandConfig.getNameForResourcesMenu(),
        equalTo("open nautilus"));
    assertTrue(this.commandConfig.isEnabledForTextSelectionMenu());
    assertThat(this.commandConfig.getNameForTextSelectionMenu(),
        equalTo("open nautilus from editor"));
    assertFalse(this.commandConfig.isPassSelectedText());
  }

  @Test
  public void shouldImportWhenValuesAreMissing() throws Exception
  {
    String json = "{"//
        + "}";
    this.commandConfig = this.converter.convertToCommandConfig(json);
    assertThat(this.commandConfig, notNullValue());
    assertThat(this.commandConfig.getCommand(), equalTo(""));
    assertThat(this.commandConfig.getResourceType(), equalTo(ResourceType.BOTH));
    assertTrue(this.commandConfig.isEnabledForResourcesMenu());
    assertThat(this.commandConfig.getNameForResourcesMenu(), equalTo(""));
    assertTrue(this.commandConfig.isEnabledForTextSelectionMenu());
    assertThat(this.commandConfig.getNameForTextSelectionMenu(), equalTo(""));
    assertFalse(this.commandConfig.isPassSelectedText());
  }

  @Test
  public void shouldImportEmptyStrings() throws Exception
  {
    String json = "{"//
        + "\"" + KEY_VERSION + "\":\"" + VERSION + "\"," //
        + "\"" + KEY_COMMAND + "\":\"\"," //
        + "\"" + KEY_RESOURCE_TYPE + "\":\"BOTH\"," //
        + "\"" + KEY_ENABLED_FOR_RESOURCE_VIEW + "\":true," //
        + "\"" + KEY_NAME_FOR_RESOURCE_VIEW + "\":\"\"," //
        + "\"" + KEY_ENABLED_FOR_EDITOR + "\":true," //
        + "\"" + KEY_NAME_FOR_EDITOR + "\":\"\"," //
        + "\"" + KEY_PASS_SELECTED_TEXT + "\":false}"; //
    this.commandConfig = this.converter.convertToCommandConfig(json);
    assertThat(this.commandConfig, notNullValue());
    assertThat(this.commandConfig.getCommand(), equalTo(""));
    assertThat(this.commandConfig.getResourceType(), equalTo(ResourceType.BOTH));
    assertTrue(this.commandConfig.isEnabledForResourcesMenu());
    assertThat(this.commandConfig.getNameForResourcesMenu(), equalTo(""));
    assertTrue(this.commandConfig.isEnabledForTextSelectionMenu());
    assertThat(this.commandConfig.getNameForTextSelectionMenu(), equalTo(""));
    assertFalse(this.commandConfig.isPassSelectedText());
  }

  @Test
  public void shouldImportNullValues() throws Exception
  {
    String json = "{"//
        + "\"" + KEY_VERSION + "\":\"" + VERSION + "\"," //
        + "\"" + KEY_COMMAND + "\":null," //
        + "\"" + KEY_RESOURCE_TYPE + "\":null," //
        + "\"" + KEY_ENABLED_FOR_RESOURCE_VIEW + "\":true," //
        + "\"" + KEY_NAME_FOR_RESOURCE_VIEW + "\":null," //
        + "\"" + KEY_ENABLED_FOR_EDITOR + "\":true," //
        + "\"" + KEY_NAME_FOR_EDITOR + "\":null," //
        + "\"" + KEY_PASS_SELECTED_TEXT + "\":false}"; //
    this.commandConfig = this.converter.convertToCommandConfig(json);
    assertThat(this.commandConfig, notNullValue());
    assertThat(this.commandConfig.getCommand(), equalTo(""));
    assertThat(this.commandConfig.getResourceType(), equalTo(ResourceType.BOTH));
    assertTrue(this.commandConfig.isEnabledForResourcesMenu());
    assertThat(this.commandConfig.getNameForResourcesMenu(), equalTo(""));
    assertTrue(this.commandConfig.isEnabledForTextSelectionMenu());
    assertThat(this.commandConfig.getNameForTextSelectionMenu(), equalTo(""));
    assertFalse(this.commandConfig.isPassSelectedText());
  }

  @Test
  public void shouldImportedQuotesAndBackslashesInCommand() throws Exception
  {
    String json = "{"//
        + "\"" + KEY_VERSION + "\":\"" + VERSION
        + "\"," //
        + "\"" + KEY_COMMAND
        + "\":\"command with \\\"quotes\\\"\\\" and back \\\\ slashes \\\\\"," //
        + "\"" + KEY_RESOURCE_TYPE + "\":\"BOTH\"," //
        + "\"" + KEY_ENABLED_FOR_RESOURCE_VIEW + "\":true," //
        + "\"" + KEY_NAME_FOR_RESOURCE_VIEW + "\":\"open nautilus\"," //
        + "\"" + KEY_ENABLED_FOR_EDITOR + "\":true," //
        + "\"" + KEY_NAME_FOR_EDITOR + "\":\"open nautilus from editor\"," //
        + "\"" + KEY_PASS_SELECTED_TEXT + "\":false}"; //
    this.commandConfig = this.converter.convertToCommandConfig(json);
    assertThat(this.commandConfig, notNullValue());
    assertThat(this.commandConfig.getCommand(),
        equalTo("command with \"quotes\"\" and back \\ slashes \\"));
    assertThat(this.commandConfig.getResourceType(), equalTo(ResourceType.BOTH));
    assertTrue(this.commandConfig.isEnabledForResourcesMenu());
    assertThat(this.commandConfig.getNameForResourcesMenu(),
        equalTo("open nautilus"));
    assertTrue(this.commandConfig.isEnabledForTextSelectionMenu());
    assertThat(this.commandConfig.getNameForTextSelectionMenu(),
        equalTo("open nautilus from editor"));
    assertFalse(this.commandConfig.isPassSelectedText());
  }

  @Test
  public void shouldNotImportedInvalidJson() throws Exception
  {
    String json = "{\"" + KEY_VERSION + "\":\"" + VERSION
        + "\",abcdefg\"\\$(&%/";
    try
    {
      this.converter.convertToCommandConfig(json);
      fail("Exception should have been thrown.");
    }
    catch (ParseException e)
    {
      // expected
    }
  }

  @Test
  public void shouldNotImportedEmptyFile() throws Exception
  {
    String json = "";
    try
    {
      this.converter.convertToCommandConfig(json);
      fail("Exception should have been thrown.");
    }
    catch (ParseException e)
    {
      // expected
    }
  }

  @Test
  public void shouldImportWhenAdditionalUnknownValuesArePresent()
      throws Exception
  {
    String json = "{"//
        + "\"unknown key 1\":\"...\"" //
        + "\"" + KEY_VERSION + "\":\"" + VERSION + "\"," //
        + "\"" + KEY_COMMAND + "\":\"nautilus ${resource_path}\"," //
        + "\"" + KEY_RESOURCE_TYPE + "\":\"BOTH\"" //
        + "\"unknown key 2\":\"...\"" //
        + "\"" + KEY_ENABLED_FOR_RESOURCE_VIEW + "\":true," //
        + "\"" + KEY_NAME_FOR_RESOURCE_VIEW + "\":\"open nautilus\"," //
        + "\"unknown key 3\":\"...\"" //
        + "\"" + KEY_ENABLED_FOR_EDITOR + "\":true," //
        + "\"" + KEY_NAME_FOR_EDITOR + "\":\"open nautilus from editor\"," //
        + "\"" + KEY_PASS_SELECTED_TEXT + "\":false" //
        + "\"unknown key 4\":\"...\"" //
        + "}";
    this.commandConfig = this.converter.convertToCommandConfig(json);
    assertThat(this.commandConfig, notNullValue());
    assertThat(this.commandConfig.getCommand(),
        equalTo("nautilus ${resource_path}"));
    assertThat(this.commandConfig.getResourceType(), equalTo(ResourceType.BOTH));
    assertTrue(this.commandConfig.isEnabledForResourcesMenu());
    assertThat(this.commandConfig.getNameForResourcesMenu(),
        equalTo("open nautilus"));
    assertTrue(this.commandConfig.isEnabledForTextSelectionMenu());
    assertThat(this.commandConfig.getNameForTextSelectionMenu(),
        equalTo("open nautilus from editor"));
    assertFalse(this.commandConfig.isPassSelectedText());
  }

  /*
   * TODO IMPORT
   * 
   * - missing values
   */

}
