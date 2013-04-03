package de.bastiankrol.startexplorer.handlers;

import de.bastiankrol.startexplorer.handlers.delegates.AbstractStartFromResourceHandlerDelegate;

/**
 * Examines the selection in the package explorer/navigator and executes an
 * action for the selected files/folders.
 * 
 * @author Bastian Krol
 */
public abstract class AbstractStartFromResourceHandler extends
    AbstractStartExplorerHandler
{
  abstract AbstractStartFromResourceHandlerDelegate getDelegate();
}
