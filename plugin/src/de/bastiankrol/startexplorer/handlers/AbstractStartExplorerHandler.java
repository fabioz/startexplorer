package de.bastiankrol.startexplorer.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import de.bastiankrol.startexplorer.handlers.delegates.AbstractHandlerDelegate;

/**
 * Common base class for all handlers of this plug-in.
 */
public abstract class AbstractStartExplorerHandler extends AbstractHandler
{
  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
   */
  public final Object execute(ExecutionEvent event) throws ExecutionException
  {
    return getDelegate().execute(event);
  }

  abstract AbstractHandlerDelegate getDelegate();
}
