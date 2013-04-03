package de.bastiankrol.startexplorer.handlers.delegates;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.commands.Command;

import de.bastiankrol.startexplorer.ResourceType;
import de.bastiankrol.startexplorer.customcommands.CommandConfig;

/**
 * @author Bastian Krol
 */
public class CustomCommandForEditorHandlerDelegate extends
    AbstractStartFromEditorHandlerDelegate
{

  private CommandConfig commandConfig;

  /**
   * Creates a CustomCommandForResourceHandler for the given CommandConfig
   * 
   * @param commandConfig the command configuration which, among other things,
   *          contains the command line string to execute by this handler
   */
  public CustomCommandForEditorHandlerDelegate(CommandConfig commandConfig)
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
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromEditorHandler#getResourceType()
   */
  protected ResourceType getResourceType()
  {
    return this.getCommandConfig().getResourceType();
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromEditorHandler#doActionForFile(java.io.File)
   */
  @Override
  protected void doActionForFile(File file)
  {
    // TODO Maybe we need make it possible to define custom commands as an array
    // of Strings instead of one String?
    this.getRuntimeExecCalls().startCustomCommandForFile(
        new String[] { this.getCommandConfig().getCommand() }, file);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromEditorHandler#shouldInterpretTextSelectionAsFileName()
   */
  @Override
  protected boolean shouldInterpretTextSelectionAsFileName()
  {
    return !this.getCommandConfig().isPassSelectedText();
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromEditorHandler#doActionForSelectedText(java.lang.String)
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
   * @see de.bastiankrol.startexplorer.handlers.delegate.AbstractStartFromEditorHandlerDelegate#getAppropriateStartFromResourceHandlerDelegate()
   */
  @Override
  protected AbstractStartFromResourceHandlerDelegate getAppropriateStartFromResourceHandlerDelegate()
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
