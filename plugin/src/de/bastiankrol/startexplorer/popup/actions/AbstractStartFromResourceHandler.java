package de.bastiankrol.startexplorer.popup.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISources;

import de.bastiankrol.startexplorer.Activator;
import de.bastiankrol.startexplorer.ResourceType;

/**
 * Examines the selection in the package explorer/navigator and executes an
 * action for the selected files/folders.
 * 
 * @author Bastian Krol
 */
public abstract class AbstractStartFromResourceHandler extends
    AbstractStartExplorerHandler
{

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
   */
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
    ISelection selection = (ISelection) appContext
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
      // I don't know how to define keyboard bindings so that the keyboad
      // binding triggers a different command for resources (navigator, package
      // explorer, project explorer, ...) versus text selections in an editor
      // view. That's why the resource commands are executed for text selections
      // if invoked by a keyboard shortcut. We delegate the call to the
      // appropriate handler for the text selection based command.
      if (selection instanceof ITextSelection)
      {
        // IHandlerService handlerService = (IHandlerService) PlatformUI
        // .getWorkbench().getService(IHandlerService.class);
        // return handlerService.executeCommand(this.getTextBasedCommand(),
        // event);
        AbstractStartFromEditorHandler startFromStringHandler = this
            .getAppropriateStartFromStringHandler();
        if (startFromStringHandler != null)
        {
          return startFromStringHandler.executeForSelection(event, selection,
              appContext);
        }
        else
        {
          Activator
              .logMessage(
                  org.eclipse.core.runtime.IStatus.WARNING,
                  "The current selection is a text selection but there is no text selection handler for this command.");
          return null;

        }
      }
      else
      {
        Activator
            .logMessage(
                org.eclipse.core.runtime.IStatus.WARNING,
                "Current selection is not an resource selection (IStructuredSelection) nor a text selection (ITextSelection), [selection.getClass(): "
                    + selection.getClass()
                    + ", selection.toString(): "
                    + selection.toString() + "]");
        return null;

      }
    }
    IStructuredSelection structuredSelection = (IStructuredSelection) selection;
    List<File> fileList = this.structuredSelectionToOsPathList(
        structuredSelection, event);
    this.doActionForFileList(fileList);
    return null;
  }

  /**
   * Returns a StartFromStringHandler object that handles the same action as
   * this handler for text selection (ITextSelection).
   * 
   * @return an AbstractStartFromStringHandler
   */
  protected abstract AbstractStartFromEditorHandler getAppropriateStartFromStringHandler();

  /**
   * Returns the resource type appropriate for this handler.
   * 
   * @return the resource type appropriate for this handler.
   */
  protected abstract ResourceType getResourceType();

  /**
   * Executes the appropriate action for the given <code>pathList</code>
   * 
   * @param fileList the list of File objects to do something with
   */
  protected abstract void doActionForFileList(List<File> fileList);

  /**
   * Transforms a structured selection into a list of java.io.File objects
   * representing the absolute path of the selected resource in their
   * os-specific form.
   * 
   * @return a list of os-specific paths
   * @throws ExecutionException
   */
  private List<File> structuredSelectionToOsPathList(
      IStructuredSelection structuredSelection, ExecutionEvent event)
      throws ExecutionException
  {
    List<File> fileList = new ArrayList<File>();
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
        resource = (IResource) ((IAdaptable) selectedObject)
            .getAdapter(IResource.class);
        assert resource != null;
      }
      File file = this.resourceToFile(resource, this.getResourceType(), event);
      if (file != null)
      {
        fileList.add(file);
      }
    }
    return fileList;
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
