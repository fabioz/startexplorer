package de.bastiankrol.startexplorer.handlers.delegates;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import de.bastiankrol.startexplorer.ResourceType;
import de.bastiankrol.startexplorer.crossplatform.Capabilities;
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

  @Override
  protected boolean areUrlsSupported(Capabilities capabilities)
  {
    // TODO Support URLs for custom commands

    // Regardless of the platform's capabilities, a custom command might be able
    // to cope with URLs or not, we do not know. For now we do not support URLs
    // for custom commands - until a user asks for it. Reason: Replacing
    // variables in custom commands needs to be implemented slightly different
    // when we want to support URLs in custom commands.

    // We could make this configurable in the custom command configuration
    // dialog and store it in preferences like in
    // #shouldInterpretTextSelectionAsFileName. OTOH, this makes the dialog more
    // complicated. We could also always return true and let the custom command
    // call break if it does not support URLs.

    return false;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.delegates.AbstractStartFromEditorHandlerDelegate#getAppropriateStartFromResourceHandlerDelegate()
   */
  @Override
  protected CustomCommandForResourceHandlerDelegate getAppropriateStartFromResourceHandlerDelegate()
  {
    return new CustomCommandForResourceHandlerDelegate(this.getCommandConfig());
  }
}
