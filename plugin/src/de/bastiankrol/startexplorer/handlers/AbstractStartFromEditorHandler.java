package de.bastiankrol.startexplorer.handlers;

import de.bastiankrol.startexplorer.handlers.delegates.AbstractStartFromEditorHandlerDelegate;

/**
 * Examines the selected region in a text file, tries to interpret it as a
 * Windows path and
 * <ul>
 * <li>opens a Windows Explorer for the directory in the location described by
 * that path or</li>
 * <li>starts a system application for that file.</li>
 * </ul>
 * 
 * @author Bastian Krol
 */
public abstract class AbstractStartFromEditorHandler extends
    AbstractStartExplorerHandler
{
  abstract AbstractStartFromEditorHandlerDelegate getDelegate();
}
