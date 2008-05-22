package de.bastiankrol.startexplorer.popup.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISources;

import de.bastiankrol.startexplorer.Activator;
import de.bastiankrol.startexplorer.RuntimeExecWrapper;

/**
 * Examines the selection in the package explorer/navigator and opens a Windows
 * Explorer for all selected files/folders.
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$ $Author:$
 */
public class StartExplorerFromResourceHandler extends AbstractHandler
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
            .getVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME);
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
    if (!(selection instanceof IStructuredSelection))
    {
      Activator.logMessage(org.eclipse.core.runtime.IStatus.WARNING,
          "Current selection is not an IStructuredSelection.");
      return null;
    }
    IStructuredSelection structuredSelection = (IStructuredSelection) selection;
    List<String> pathList =
        this.structuredSelectionToOsPathList(structuredSelection, event);
    RuntimeExecWrapper.startWindowsExplorerForPathList(pathList);
    return null;
  }

  /**
   * Transforms a structured selection into a list of strings representing the
   * absolute path of the selected resource in their os-specific form.
   * 
   * @return a list of os-specific paths
   * @throws ExecutionException
   */
  private List<String> structuredSelectionToOsPathList(
      IStructuredSelection structuredSelection, ExecutionEvent event)
      throws ExecutionException
  {
    List<String> pathList = new ArrayList<String>();
    for (Iterator<Object> i = this.getIterator(structuredSelection); i
        .hasNext();)
    {
      Object selectedObject = i.next();
      if (!(selectedObject instanceof IResource || (selectedObject instanceof IAdaptable && ((IAdaptable) selectedObject)
          .getAdapter(IResource.class) != null)))
      {
        Activator
            .logMessage(
                IStatus.WARNING,
                "Current selection contains an object that is not an IResource and is not adaptable to IResource: "
                    + selectedObject);
        continue;
      }
      IResource resource;
      if (selectedObject instanceof IResource)
      {
        resource = (IResource) selectedObject;
      }
      else
      {
        resource =
            (IResource) ((IAdaptable) selectedObject)
                .getAdapter(IResource.class);
        assert resource != null;
      }
      IPath path = resource.getLocation();
      if (path == null)
      {
        Activator.logMessage(IStatus.WARNING,
            "Current selection contains a resource object with null-location: "
                + resource);
        continue;
      }
      String pathString = path.toOSString();
      pathString =
          PathCheck.checkPath(pathString, event,
              PathCheck.ResourceType.DIRECTORY);
      if (pathString != null)
      {
        pathList.add(pathString);
      }
    }
    return pathList;
  }

  /**
   * Calls iterator() on the pre-Java-5 class IStructuredSelection, this call is
   * isolated because it has unchecked warnings.
   * 
   * @param structuredSelection an IStructuredSelection
   * @return structuredSelection.iterator()
   */
  @SuppressWarnings("unchecked")
  private Iterator<Object> getIterator(IStructuredSelection structuredSelection)
  {
    return structuredSelection.iterator();
  }
}
