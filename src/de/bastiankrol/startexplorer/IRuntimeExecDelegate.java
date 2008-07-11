package de.bastiankrol.startexplorer;

/**
 * A wrapper for the call to {@link Runtime#exec(String)}.
 */
public interface IRuntimeExecDelegate
{

  public void exec(String execCommandString);

}
