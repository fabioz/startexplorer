package de.bastiankrol.startexplorer.customcommands;

import java.util.Arrays;
import java.util.List;

import de.bastiankrol.startexplorer.ResourceType;

public class CommandConfigObjectMother
{
  public static CommandConfig createCommandConfigForBoth()
  {
    return new CommandConfig("cmd", ResourceType.BOTH, true,
        "command/both/resource", true, "command/both/editor", false);
  }

  public static CommandConfig createCommandConfigForResource()
  {
    return new CommandConfig("cmd", ResourceType.BOTH, true,
        "command/resource", false, "", false);
  }

  public static CommandConfig createCommandConfigForEditor()
  {
    return new CommandConfig("cmd", ResourceType.BOTH, false, "", true,
        "command/editor", false);
  }

  public static CommandConfig createCommandConfigPassingText()
  {
    return new CommandConfig("pass", ResourceType.BOTH, false, "", true,
        "command/pass/editor", true);
  }

  public static List<CommandConfig> oneForBoth()
  {
    return Arrays.asList(new CommandConfig[] { createCommandConfigForBoth() });
  }

  public static List<CommandConfig> oneForBothOneForResourceOneForEditor()
  {
    return Arrays.asList(new CommandConfig[] { createCommandConfigForBoth(),
        createCommandConfigForEditor(), createCommandConfigForResource() });
  }
}
