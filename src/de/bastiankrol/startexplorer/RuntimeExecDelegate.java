package de.bastiankrol.startexplorer;

import java.io.IOException;

/**
 * A wrapper for the call to {@link Runtime#exec(String)}.
 */
public class RuntimeExecDelegate implements IRuntimeExecDelegate
{
  private static final Runtime RUNTIME = Runtime.getRuntime();

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
      Activator.logException(e);
    }
  }

}
