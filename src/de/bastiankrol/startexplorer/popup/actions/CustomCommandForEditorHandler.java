package de.bastiankrol.startexplorer.popup.actions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.commands.Command;

import de.bastiankrol.startexplorer.ResourceType;
import de.bastiankrol.startexplorer.customcommands.CommandConfig;

/**
 * @author Bastian Krol
 */
public class CustomCommandForEditorHandler extends
    AbstractStartFromEditorHandler
{

  private CommandConfig commandConfig;

  /**
   * Creates a CustomCommandForResourceHandler for the given CommandConfig
   * 
   * @param commandConfig the command configuration which, among other things,
   *          contains the command line string to execute by this handler
   */
  public CustomCommandForEditorHandler(CommandConfig commandConfig)
  {
    this.commandConfig = commandConfig;
  }

  /**
   * Returns the Command Config for this handler
   * 
   * @return the Command Config for this handler
   */
  protected CommandConfig getCommandConfig()
  {
    return this.commandConfig;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromEditorHandler#getResourceType()
   */
  protected ResourceType getResourceType()
  {
    return this.getCommandConfig().getResourceType();
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromEditorHandler#doActionForFile(java.io.File)
   */
  @Override
  protected void doActionForFile(File file)
  {
    this.getRuntimeExecCalls().startCustomCommandForFile(
        this.getCommandConfig().getCommand(), file);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromEditorHandler#shouldInterpretTextSelectionAsFileName()
   */
  @Override
  protected boolean shouldInterpretTextSelectionAsFileName()
  {
    return !this.getCommandConfig().isPassSelectedText();
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromEditorHandler#doActionForSelectedText(java.lang.String)
   */
  @Override
  protected void doActionForSelectedText(String selectedText)
      throws IOException
  {
    File tempFile = File.createTempFile("startexplorer_", null);
    FileWriter writer = new FileWriter(tempFile);
    writer.write(selectedText);
    writer.close();
    this.doActionForFile(tempFile);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.popup.actions.AbstractStartFromEditorHandler#getAppropriateStartFromResourceHandler()
   */
  @Override
  protected AbstractStartFromResourceHandler getAppropriateStartFromResourceHandler()
  {
    return getCorrespondingHandlerForCustomCommand(this.getCommandConfig(),
        new CommandRetriever()
        {
          public Command getCommandFromConfig(CommandConfig commandConfig)
          {
            return commandConfig.getEclipseCommandForResourceView();
          }
        });
  }
}
