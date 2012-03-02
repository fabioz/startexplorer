package de.bastiankrol.startexplorer.customcommands;

import static de.bastiankrol.startexplorer.Activator.*;

import org.eclipse.core.commands.Command;

import de.bastiankrol.startexplorer.ResourceType;

/**
 * Configuration for a custom command
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$
 */
public class CommandConfig
{

  private String command;
  private ResourceType resourceType;
  private boolean enabledForResourcesMenu;
  private String nameForResourcesMenu;
  private boolean enabledForTextSelectionMenu;
  private String nameForTextSelectionMenu;
  private boolean passSelectedText;

  /**
   * Stores the Eclipse command object for the resource view, once it has been
   * created.
   */
  private Command eclipseCommandForResourceView;

  /**
   * Stores the Eclipse command object for the editor, once it has been created.
   */
  private Command eclipseCommandForEditor;

  /**
   * Creates an empty CommandConfig.
   */
  public CommandConfig()
  {
    super();
    this.command = "";
    this.resourceType = ResourceType.BOTH;
    this.enabledForResourcesMenu = true;
    this.nameForResourcesMenu = "";
    this.enabledForTextSelectionMenu = true;
    this.nameForTextSelectionMenu = "";
  }

  /**
   * Creates a CommandConfig.
   * 
   * @param command the command line to execute
   * @param resourceType the type of resource for which this command is intended
   * @param enabledForResourcesMenu for resources menu?
   * @param nameForResourcesMenu name for resources menu. Will be changed to
   *          &quot;&quot; if <code>null</code>.
   * @param enabledForTextSelectionMenu for text selection menu?
   * @param nameForTextSelectionMenu name for text selection menu. Will be
   *          changed to &quot;&quot; if <code>null</code>.
   */
  public CommandConfig(String command, ResourceType resourceType,
      boolean enabledForResourcesMenu, String nameForResourcesMenu,
      boolean enabledForTextSelectionMenu, String nameForTextSelectionMenu,
      boolean passSelectedText)
  {
    super();
    this.command = command != null ? command : "";
    this.resourceType = resourceType;
    this.enabledForResourcesMenu = enabledForResourcesMenu;
    this.nameForResourcesMenu = nameForResourcesMenu != null ? nameForResourcesMenu
        : "";
    this.enabledForTextSelectionMenu = enabledForTextSelectionMenu;
    this.nameForTextSelectionMenu = nameForTextSelectionMenu != null ? nameForTextSelectionMenu
        : "";
    this.passSelectedText = passSelectedText;
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

  public ResourceType getResourceType()
  {
    return resourceType;
  }

  public void setResourceType(ResourceType resourceType)
  {
    this.resourceType = resourceType;
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
   * If set to <code>true</code>, this command is enabled for the text selection
   * context menu
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
   * If <code>true</code>, this command will pass the selected text instead of
   * interpreting it as a file path
   * 
   * @return <code>true</code>, if this command is set to pass the selected text
   *         instead of interpreting it as a file path
   */
  public boolean isPassSelectedText()
  {
    return this.passSelectedText;
  }

  /**
   * If set to <code>true</code>, this command will pass the selected text
   * instead of interpreting it as a file path
   * 
   * @param passSelectedText if <code>true</code>, this command will pass the
   *          selected text instead of interpreting it as a file path
   */
  public void setPassSelectedText(boolean passSelectedText)
  {
    this.passSelectedText = passSelectedText;
  }

  /**
   * Returns the Eclipse command object for the resource view; if it has not
   * been created yet, it will be created on demand.
   * 
   * @return the Eclipse command object
   */
  public Command getEclipseCommandForResourceView()
  {
    return this.getEclipseCommandForResourceView(getPluginContext()
        .getCustomCommandResourceViewFactory());
  }

  /**
   * Returns the Eclipse command object for the editor; if it has not been
   * created yet, it will be created on demand.
   * 
   * @return the Eclipse command object
   */
  public Command getEclipseCommandForEditor()
  {
    return this.getEclipseCommandForEditor(getPluginContext()
        .getCustomCommandEditorFactory());
  }

  /**
   * Returns the Eclipse command object for the resource view; if it has not
   * been created yet, it will be created on demand.
   * 
   * @param customCommandFactory the factory to use to create the command
   * @return the Eclipse command object
   */
  Command getEclipseCommandForResourceView(
      CustomCommandResourceViewFactory customCommandFactory)
  {
    if (!this.isEnabledForResourcesMenu())
    {
      return null;
    }
    else if (eclipseCommandForResourceView == null)
    {
      this.eclipseCommandForResourceView = customCommandFactory
          .createCommand(this);
    }
    return this.eclipseCommandForResourceView;
  }

  /**
   * Returns the Eclipse command object for the editor; if it has not been
   * created yet, it will be created on demand.
   * 
   * @param customCommandFactory the factory to use to create the command
   * @return the Eclipse command object
   */
  Command getEclipseCommandForEditor(
      CustomCommandEditorFactory customCommandFactory)
  {
    if (!this.isEnabledForTextSelectionMenu())
    {
      return null;
    }
    else if (this.eclipseCommandForEditor == null)
    {
      this.eclipseCommandForEditor = customCommandFactory.createCommand(this);
    }
    return eclipseCommandForEditor;
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