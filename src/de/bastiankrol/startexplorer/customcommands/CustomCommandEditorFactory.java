package de.bastiankrol.startexplorer.customcommands;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.IHandler;

import de.bastiankrol.startexplorer.popup.actions.CustomCommandForStringHandler;

public class CustomCommandEditorFactory extends AbstractCustomCommandFactory
{
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.AbstractCustomCommandMenuProvider#isEnabled(de.bastiankrol.startexplorer.customcommands.CommandConfig)
   */
  @Override
  protected boolean isEnabled(CommandConfig commandConfig)
  {
    return commandConfig.isEnabledForTextSelectionMenu();
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.AbstractCustomCommandMenuProvider#getNameFromCommandConfig(de.bastiankrol.startexplorer.customcommands.CommandConfig)
   */
  @Override
  protected String getNameFromCommandConfig(CommandConfig commandConfig)
  {
    return commandConfig.getNameForTextSelectionMenu();
  }

  @Override
  Command getCommandFromCommandConfig(CommandConfig commandConfig)
  {
    return commandConfig.getEclipseCommandForEditor(this);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.customcommands.AbstractCustomCommandFactory#createHandlerForCustomCommand(de.bastiankrol.startexplorer.customcommands.CommandConfig)
   */
  @Override
  protected IHandler createHandlerForCustomCommand(CommandConfig commandConfig)
  {
    return new CustomCommandForStringHandler(commandConfig);
  }
}
