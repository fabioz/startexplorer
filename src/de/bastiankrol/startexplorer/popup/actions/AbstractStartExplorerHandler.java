package de.bastiankrol.startexplorer.popup.actions;

import org.eclipse.core.commands.AbstractHandler;

import de.bastiankrol.startexplorer.Activator;
import de.bastiankrol.startexplorer.IRuntimeExecCalls;
import de.bastiankrol.startexplorer.util.PathChecker;

/**
 * Common base class for all handlers of this plug-in.
 */
public abstract class AbstractStartExplorerHandler extends AbstractHandler
{

  private IRuntimeExecCalls runtimeExecCalls;
  private PathChecker pathChecker;

  /**
   * Constructor
   */
  AbstractStartExplorerHandler()
  {
    this.runtimeExecCalls = Activator.getDefault().getRuntimeExecCalls();
    this.pathChecker = Activator.getDefault().getPathChecker();
  }

  /**
   * Returns the RuntimeExecCalls instance.
   */
  IRuntimeExecCalls getRuntimeExecCalls()
  {
    return this.runtimeExecCalls;
  }

  /**
   * Returns the PathChecker instance.
   */
  PathChecker getPathChecker()
  {
    return this.pathChecker;
  }
}
