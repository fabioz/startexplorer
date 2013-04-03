package de.bastiankrol.startexplorer.handlers;

import de.bastiankrol.startexplorer.handlers.delegates.StartSystemApplicationFromResourceHandlerDelegate;

/**
 * Handler for the command start system application from resource handler
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$
 */
public class StartSystemApplicationFromResourceHandler extends
    AbstractStartFromResourceHandler
{
  @Override
  StartSystemApplicationFromResourceHandlerDelegate getDelegate()
  {
    return new StartSystemApplicationFromResourceHandlerDelegate();
  }
}
