package de.bastiankrol.startexplorer;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IStatus;

import de.bastiankrol.startexplorer.util.IMessageDialogHelper;
import de.bastiankrol.startexplorer.util.MessageDialogHelper;

/**
 * A wrapper for the call to {@link Runtime#exec(String)}.
 */
class RuntimeExecDelegate implements IRuntimeExecDelegate
{
  private static final Runtime RUNTIME = Runtime.getRuntime();

  private IMessageDialogHelper messageDialogHelper;

  /**
   * Creates an instance.
   */
  RuntimeExecDelegate()
  {
    this.messageDialogHelper = new MessageDialogHelper();
  }

  /**
   * Do not use this constructor.
   */
  // Provided for RuntimeExecDelegateForTests
  RuntimeExecDelegate(boolean dontInitializeMessageHelper)
  {
    super();
    if (!dontInitializeMessageHelper)
    {
      // does not make sense, but... whatever
      this.messageDialogHelper = new MessageDialogHelper();
    }
  }

  Runtime getRuntime()
  {
    return RUNTIME;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.IRuntimeExecDelegate#exec(java.lang.String,
   *      java.io.File)
   */
  public void exec(String execCommandString, File workingDirectory)
  {
    // TODO Maybe make logging configurable in preferences
    // (Either enabled or disabled)
    Activator.logMessage(IStatus.OK, "Executing command <"
        + execCommandString
        + "> in working directory <"
        + (workingDirectory != null ? workingDirectory.getAbsolutePath()
            : "null") + ">.");
    try
    {
      this.getRuntime().exec(execCommandString, null, workingDirectory);
    }
    catch (IOException e)
    {
      StringBuilder builder = new StringBuilder();
      builder.append("The command could not be executed.");
      builder.append("\n");
      if (e.getMessage() != null)
      {
        builder.append(" Message: ");
        builder.append(e.getMessage());
        builder.append("\n");
      }
      this.messageDialogHelper.displayErrorMessage(
          "Command could not be executed", builder.toString());
    }
  }
}
