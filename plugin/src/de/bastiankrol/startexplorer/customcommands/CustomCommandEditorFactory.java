package de.bastiankrol.startexplorer.customcommands;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.handlers.IHandlerActivation;

import de.bastiankrol.startexplorer.handlers.CustomCommandForEditorHandler;

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

  @Override
  IHandlerActivation getHandlerActivationFromCommandConfig(
      CommandConfig commandConfig)
  {
    return commandConfig.getHandlerActivationForEditor();
  }

  @Override
  void setHandlerActivationInCommandConfig(CommandConfig commandConfig,
      IHandlerActivation handlerActivation)
  {
    commandConfig.setHandlerActivationForEditor(handlerActivation);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.customcommands.AbstractCustomCommandFactory#createHandlerForCustomCommand(de.bastiankrol.startexplorer.customcommands.CommandConfig)
   */
  @Override
  protected IHandler createHandlerForCustomCommand(CommandConfig commandConfig)
  {
    return new CustomCommandForEditorHandler(commandConfig);
  }
}
