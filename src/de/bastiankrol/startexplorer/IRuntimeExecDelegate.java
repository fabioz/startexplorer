package de.bastiankrol.startexplorer;

/**
 * A wrapper for the call to {@link Runtime#exec(String)}.
 */
public interface IRuntimeExecDelegate
{

  /**
   * Executes the <code>execCommandString</code> via Runtime.exec(String).
   * 
   * @param execCommandString
   *          the shell command to execute
   */
  public void exec(String execCommandString);

}
