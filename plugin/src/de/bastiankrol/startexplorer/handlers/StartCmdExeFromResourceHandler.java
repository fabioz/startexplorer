package de.bastiankrol.startexplorer.handlers;

import de.bastiankrol.startexplorer.handlers.delegates.StartCmdExeFromResourceHandlerDelegate;

/**
 * Handler for the command start cmd.exe from resource
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$
 */
public class StartCmdExeFromResourceHandler extends
    AbstractStartFromResourceHandler
{
  @Override
  StartCmdExeFromResourceHandlerDelegate getDelegate()
  {
    return new StartCmdExeFromResourceHandlerDelegate();
  }
}
