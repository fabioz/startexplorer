package de.bastiankrol.startexplorer.handlers;

import de.bastiankrol.startexplorer.handlers.delegates.CopyResourcePathToClipboardResourceViewHandlerDelegate;

/**
 * Handler for the command copy resource path to clipboard
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$
 */
public class CopyResourcePathToClipboardResourceViewHandler extends
    AbstractStartFromResourceHandler
{

  @Override
  CopyResourcePathToClipboardResourceViewHandlerDelegate getDelegate()
  {
    return new CopyResourcePathToClipboardResourceViewHandlerDelegate();
  }
}
