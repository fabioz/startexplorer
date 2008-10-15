package de.bastiankrol.startexplorer;

import java.io.IOException;

import de.bastiankrol.startexplorer.util.IMessageDialogHelper;
import de.bastiankrol.startexplorer.util.MessageDialogHelper;

/**
 * A wrapper for the call to {@link Runtime#exec(String)}.
 */
public class RuntimeExecDelegate implements IRuntimeExecDelegate
{
  private static final Runtime RUNTIME = Runtime.getRuntime();

  private IMessageDialogHelper messageDialogHelper;

  /**
   * RuntimeExecDelegate Konstruktor.
   * 
   */
  public RuntimeExecDelegate()
  {
    this.messageDialogHelper = new MessageDialogHelper();
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.IRuntimeExecDelegate#exec(java.lang.String)
   */
  public void exec(String execCommandString)
  {
    try
    {
      RUNTIME.exec(execCommandString);
    }
    catch (IOException e)
    {
      StringBuffer buffer = new StringBuffer();
      buffer.append("The command could not be executed.");
      buffer.append("\n");
      if (e.getMessage() != null)
      {
        buffer.append(" Message: ");
        buffer.append(e.getMessage());
        buffer.append("\n");
      }
      this.messageDialogHelper.displayErrorMessage(
          "Command could not be executed", buffer.toString());
    }
  }
}
