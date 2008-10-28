package de.bastiankrol.startexplorer.preferences;

import org.eclipse.core.commands.Command;

/**
 * Configuration for a custom command
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$
 */
public class CommandConfig
{
  private String command;
  private boolean enabledForResourcesMenu;
  private String nameForResourcesMenu;
  private boolean enabledForTextSelectionMenu;
  private String nameForTextSelectionMenu;
  private Command eclipseCommand;

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
   * @param command the command line to execute
   * @param enabledForResourcesMenu for resources menu?
   * @param nameForResourcesMenu name for resources menu. Will be changed to
   *          &quot;&quot; if <code>null</code>.
   * @param enabledForTextSelectionMenu for text selection menu?
   * @param nameForTextSelectionMenu name for text selection menu. Will be
   *          changed to &quot;&quot; if <code>null</code>.
   */
  public CommandConfig(String command, boolean enabledForResourcesMenu,
      String nameForResourcesMenu, boolean enabledForTextSelectionMenu,
      String nameForTextSelectionMenu)
  {
    super();
    this.command = command;
    this.enabledForResourcesMenu = enabledForResourcesMenu;
    this.nameForResourcesMenu =
        nameForResourcesMenu != null ? nameForResourcesMenu : "";
    this.enabledForTextSelectionMenu = enabledForTextSelectionMenu;
    this.nameForTextSelectionMenu =
        nameForTextSelectionMenu != null ? nameForTextSelectionMenu : "";
  }

  /**
   * Returns the command string
   * 
   * @return the command string
   */
  public String getCommand()
  {
    return this.command;
  }

  /**
   * Sets the command string
   * 
   * @param command the command string
   */
  public void setCommand(String command)
  {
    this.command = command;
  }

  /**
   * Returns <code>true</code>, if this command is enabled for the resources
   * context menu
   * 
   * @return <code>true</code>, if this command is enabled for the resources
   *         context menu
   */
  public boolean isEnabledForResourcesMenu()
  {
    return this.enabledForResourcesMenu;
  }

  /**
   * If set to <code>true</code>, this command is enabled for the resources
   * context menu
   * 
   * @param enabledForResourcesMenu if <code>true</code>, this command is
   *          enabled for the resources context menu
   */
  public void setEnabledForResourcesMenu(boolean enabledForResourcesMenu)
  {
    this.enabledForResourcesMenu = enabledForResourcesMenu;
  }

  /**
   * Returns the name for the resource context menu
   * 
   * @return the name for the resource context menu
   */
  public String getNameForResourcesMenu()
  {
    return this.nameForResourcesMenu;
  }

  /**
   * Returns the name for the resource context menu
   * 
   * @param nameForResourcesMenu the name for the resource context menu
   */
  public void setNameForResourcesMenu(String nameForResourcesMenu)
  {
    this.nameForResourcesMenu = nameForResourcesMenu;
  }

  /**
   * Returns <code>true</code>, if this command is enabled for the text
   * selection context menu
   * 
   * @return <code>true</code>, if this command is enabled for the text
   *         selection context menu
   */
  public boolean isEnabledForTextSelectionMenu()
  {
    return this.enabledForTextSelectionMenu;
  }

  /**
   * If set to <code>true</code>, this command is enabled for the text
   * selection context menu
   * 
   * @param enabledForTextSelectionMenu if <code>true</code>, this command is
   *          enabled for the text selection context menu
   */
  public void setEnabledForTextSelectionMenu(boolean enabledForTextSelectionMenu)
  {
    this.enabledForTextSelectionMenu = enabledForTextSelectionMenu;
  }

  /**
   * Returns the name for the text selection context menu
   * 
   * @return the name for the text selection context menu
   */
  public String getNameForTextSelectionMenu()
  {
    return this.nameForTextSelectionMenu;
  }

  /**
   * Sets the name for the text selection context menu
   * 
   * @param nameForTextSelectionMenu the name for the text selection context
   *          menu
   */
  public void setNameForTextSelectionMenu(String nameForTextSelectionMenu)
  {
    this.nameForTextSelectionMenu = nameForTextSelectionMenu;
  }

  /**
   * Attaches an eclipse command object
   * 
   * @param eclipseCommand
   */
  public void attachEclipseCommand(Command eclipseCommand)
  {
    // TODO Do we need this? What for?
    this.eclipseCommand = eclipseCommand;
  }

  /**
   * Returns the attached eclipse command, if any.
   * 
   * @return the attached eclipse command, if any.
   */
  public Command getEclipseCommand()
  {
    // TODO Do we need this? What for?
    return this.eclipseCommand;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("CommandConfig [");
    builder.append(this.command);
    builder.append(": ");
    if (this.enabledForResourcesMenu)
    {
      builder.append(this.nameForResourcesMenu);
    }
    else
    {
      builder.append("-");
    }
    builder.append("/");
    if (this.enabledForTextSelectionMenu)
    {
      builder.append(this.nameForTextSelectionMenu);
    }
    else
    {
      builder.append("-");
    }
    builder.append("]");
    return builder.toString();
  }
}