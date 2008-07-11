package de.bastiankrol.startexplorer.preferences;

public class CommandConfig
{
  private String command;
  private boolean enabledForResourcesMenu;
  private String nameForResourcesMenu;
  private boolean enabledForTextSelectionMenu;
  private String nameForTextSelectionMenu;

  /**
   * Creates an empty CommandConfig.
   */
  public CommandConfig()
  {
    super();
    this.command = "";
    this.enabledForResourcesMenu = true;
    this.nameForResourcesMenu = "";
    this.enabledForTextSelectionMenu = true;
    this.nameForTextSelectionMenu = "";
  }
  
  /**
   * Creates a CommandConfig.
   * 
   * @param command
   *          the command line to execute
   * @param enabledForResourcesMenu
   *          for resources menu?
   * @param nameForResourcesMenu
   *          name for resources menu. Will be changed to &quot;&quot; if
   *          <code>null</code>.
   * @param enabledForTextSelectionMenu
   *          for text selection menu?
   * @param nameForTextSelectionMenu
   *          name for text selection menu. Will be changed to &quot;&quot; if
   *          <code>null</code>.
   */
  public CommandConfig(String command, boolean enabledForResourcesMenu,
      String nameForResourcesMenu, boolean enabledForTextSelectionMenu,
      String nameForTextSelectionMenu)
  {
    super();
    this.command = command;
    this.enabledForResourcesMenu = enabledForResourcesMenu;
    this.nameForResourcesMenu = nameForResourcesMenu != null ? nameForResourcesMenu
        : "";
    this.enabledForTextSelectionMenu = enabledForTextSelectionMenu;
    this.nameForTextSelectionMenu = nameForTextSelectionMenu != null ? nameForTextSelectionMenu
        : "";
  }

  public String getCommand()
  {
    return this.command;
  }

  public boolean isEnabledForResourcesMenu()
  {
    return this.enabledForResourcesMenu;
  }

  public String getNameForResourcesMenu()
  {
    return this.nameForResourcesMenu;
  }

  public boolean isEnabledForTextSelectionMenu()
  {
    return this.enabledForTextSelectionMenu;
  }

  public String getNameForTextSelectionMenu()
  {
    return this.nameForTextSelectionMenu;
  }

  public void setCommand(String command)
  {
    this.command = command;
  }

  public void setEnabledForResourcesMenu(boolean enabledForResourcesMenu)
  {
    this.enabledForResourcesMenu = enabledForResourcesMenu;
  }

  public void setNameForResourcesMenu(String nameForResourcesMenu)
  {
    this.nameForResourcesMenu = nameForResourcesMenu;
  }

  public void setEnabledForTextSelectionMenu(boolean enabledForTextSelectionMenu)
  {
    this.enabledForTextSelectionMenu = enabledForTextSelectionMenu;
  }

  public void setNameForTextSelectionMenu(String nameForTextSelectionMenu)
  {
    this.nameForTextSelectionMenu = nameForTextSelectionMenu;
  }
  
  
}