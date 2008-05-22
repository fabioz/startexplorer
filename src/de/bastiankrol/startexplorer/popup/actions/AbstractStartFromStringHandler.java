package de.bastiankrol.startexplorer.popup.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

import de.bastiankrol.startexplorer.Activator;

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
 * @version $Revision:$ $Date:$ $Author:$
 */
public abstract class AbstractStartFromStringHandler extends AbstractHandler
{
  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException
  {
    Object applicationContext = event.getApplicationContext();
    if (!(applicationContext instanceof IEvaluationContext))
    {
      Activator.logMessage(org.eclipse.core.runtime.IStatus.WARNING,
          "Current application context is not an IEvaluationContext.");
      return null;
    }
    IEvaluationContext appContext = (IEvaluationContext) applicationContext;
    ISelection selection =
        (ISelection) appContext
            .getVariable(ISources.ACTIVE_MENU_SELECTION_NAME);
    if (selection == null)
    {
      Activator.logMessage(org.eclipse.core.runtime.IStatus.WARNING,
          "Current selection is null, no action is taken.");
      return null;
    }
    if (selection.isEmpty())
    {
      Activator.logMessage(org.eclipse.core.runtime.IStatus.WARNING,
          "Current selection is empty, no action is taken.");
      return null;
    }
    String pathString;
    try
    {
      pathString = this.extractStringFromSelection(selection);
    }
    catch (IllegalArgumentException e)
    {
      return null;
    }
    if (pathString == null)
    {
      Activator.logMessage(org.eclipse.core.runtime.IStatus.WARNING,
          "Current selection's text is null.");
      return null;
    }
    if (pathString.equals(""))
    {
      MessageDialog
          .openError(
              HandlerUtil.getActiveShellChecked(event),
              "Empty text selection",
              "Current text selection is empty. For this function to work you need to select a path-like string in your file.");
      return null;
    }
    pathString = pathString.trim();
    pathString = PathCheck.checkPath(pathString, event, this.getResourceType());
    if (pathString != null)
    {
      this.doActionForPath(pathString);
    }
    return null;
  }

  /**
   * Returns the resource type appropriate for this handler.
   * 
   * @return the resource type appropriate for this handler.
   */
  protected abstract PathCheck.ResourceType getResourceType();

  /**
   * Executes the appropriate action for the given <code>pathString</code>
   * 
   * @param pathString the path to do something with
   */
  protected abstract void doActionForPath(String pathString);

  private String extractStringFromSelection(ISelection selection)
  {
    if (!(selection instanceof ITextSelection))
    {
      String message =
          "Current selection is not an ITextSelection, [selection.getClass(): "
              + selection.getClass() + ", selection.toString(): "
              + selection.toString() + "]";
      Activator.logMessage(org.eclipse.core.runtime.IStatus.WARNING, message);
      throw new IllegalArgumentException(message);
    }
    else
    {
      ITextSelection textSelection = (ITextSelection) selection;
      String pathString = textSelection.getText();
      return pathString;
    }
  }
}
