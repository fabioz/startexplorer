package de.bastiankrol.startexplorer;

import java.io.File;

/**
 * A wrapper for the call to {@link Runtime#exec(String)}.
 */
public interface IRuntimeExecDelegate
{

  /**
   * Executes the <code>execCommandString</code> via Runtime.exec(String).
   * 
   * @param execCommandString the shell command to execute
   * @param workingDirectory the working directory for the command, can be null
   */
  public void exec(String execCommandString, File workingDirectory);

}
