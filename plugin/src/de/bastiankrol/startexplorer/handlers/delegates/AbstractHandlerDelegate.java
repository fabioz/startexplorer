package de.bastiankrol.startexplorer.handlers.delegates;

import static de.bastiankrol.startexplorer.Activator.getLogFacility;
import static de.bastiankrol.startexplorer.Activator.getPluginContext;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

import de.bastiankrol.startexplorer.ResourceType;
import de.bastiankrol.startexplorer.crossplatform.IRuntimeExecCalls;
import de.bastiankrol.startexplorer.preferences.PreferenceModel;
import de.bastiankrol.startexplorer.util.Validator;

/**
 * Common base class for all handler delegates of this plug-in.
 */
public abstract class AbstractHandlerDelegate
{

  private Validator validator;

  /**
   * Constructor
   */
  AbstractHandlerDelegate()
  {
    this.validator = getPluginContext().getValidator();
  }

  /**
   * Executes the action requested by the user.
   * 
   * @param event the Eclipse event
   * @return null
   * @throws ExecutionException if something goes wrong
   */
  public abstract Object execute(ExecutionEvent event)
      throws ExecutionException;

  /**
   * Returns the RuntimeExecCalls instance.
   */
  IRuntimeExecCalls getRuntimeExecCalls()
  {
    return getPluginContext().getRuntimeExecCalls();
  }

  /**
   * Returns the Validator instance.
   */
  Validator getValidator()
  {
    return this.validator;
  }

  PreferenceModel getPreferenceModel()
  {
    return getPluginContext().getPreferenceModel();
  }

  File resourceToFile(IResource resource, ResourceType resourceType,
      ExecutionEvent event) throws ExecutionException
  {
    IPath path = resource.getLocation();
    if (path == null)
    {
      getLogFacility().logWarning(
          "Current selection contains a resource object with null-location: "
              + resource);
      return null;
    }
    String pathString = path.toOSString();
    return this.getValidator().checkPathAndShowMessage(pathString,
        resourceType, event);
  }
}
