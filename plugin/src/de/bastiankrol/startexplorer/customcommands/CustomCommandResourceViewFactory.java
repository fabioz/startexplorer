package de.bastiankrol.startexplorer.customcommands;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.handlers.IHandlerActivation;

import de.bastiankrol.startexplorer.handlers.CustomCommandForResourceHandler;

public class CustomCommandResourceViewFactory extends
    AbstractCustomCommandFactory
{
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.AbstractCustomCommandMenuProvider#isEnabled(de.bastiankrol.startexplorer.customcommands.CommandConfig)
   */
  @Override
  protected boolean isEnabled(CommandConfig commandConfig)
  {
    return commandConfig.isEnabledForResourcesMenu();
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.AbstractCustomCommandMenuProvider#getNameFromCommandConfig(de.bastiankrol.startexplorer.customcommands.CommandConfig)
   */
  @Override
  protected String getNameFromCommandConfig(CommandConfig commandConfig)
  {
    return commandConfig.getNameForResourcesMenu();
  }

  @Override
  Command getCommandFromCommandConfig(CommandConfig commandConfig)
  {
    return commandConfig.getEclipseCommandForResourceView(this);
  }

  @Override
  IHandlerActivation getHandlerActivationFromCommandConfig(
      CommandConfig commandConfig)
  {
    return commandConfig.getHandlerActivationForResourceView();
  }

  @Override
  void setHandlerActivationInCommandConfig(CommandConfig commandConfig,
      IHandlerActivation handlerActivation)
  {
    commandConfig.setHandlerActivationForResourceView(handlerActivation);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.AbstractCustomCommandMenuProvider#createHandlerForCustomCommand(de.bastiankrol.startexplorer.customcommands.CommandConfig)
   */
  @Override
  protected IHandler createHandlerForCustomCommand(CommandConfig commandConfig)
  {
    return new CustomCommandForResourceHandler(commandConfig);
  }
}
