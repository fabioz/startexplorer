package de.bastiankrol.startexplorer.popup.actions;

import static de.bastiankrol.startexplorer.Activator.*;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

import de.bastiankrol.startexplorer.ResourceType;
import de.bastiankrol.startexplorer.crossplatform.IRuntimeExecCalls;
import de.bastiankrol.startexplorer.customcommands.CommandConfig;
import de.bastiankrol.startexplorer.preferences.PreferenceModel;
import de.bastiankrol.startexplorer.util.PathChecker;

/**
 * Common base class for all handlers of this plug-in.
 */
public abstract class AbstractStartExplorerHandler extends AbstractHandler
{

  private PathChecker pathChecker;

  /**
   * Constructor
   */
  AbstractStartExplorerHandler()
  {
    this.pathChecker = getPluginContext().getPathChecker();
  }

  /**
   * Returns the RuntimeExecCalls instance.
   */
  IRuntimeExecCalls getRuntimeExecCalls()
  {
    return getPluginContext().getRuntimeExecCalls();
  }

  /**
   * Returns the PathChecker instance.
   */
  PathChecker getPathChecker()
  {
    return this.pathChecker;
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
    File file = this.getPathChecker()
        .checkPath(pathString, resourceType, event);
    return file;
  }

  static interface CommandRetriever
  {
    Command getCommandFromConfig(CommandConfig commandConfig);
  }

  @SuppressWarnings("unchecked")
  protected static <T extends AbstractStartExplorerHandler> T getCorrespondingHandlerForCustomCommand(
      CommandConfig commandConfig, CommandRetriever commandRetriever)
  {
    if (commandConfig != null)
    {
      Command correspondingEclipseCommand = commandRetriever
          .getCommandFromConfig(commandConfig);
      if (correspondingEclipseCommand != null)
      {
        IHandler handler = correspondingEclipseCommand.getHandler();
        if (handler instanceof AbstractStartFromResourceHandler
            || handler instanceof AbstractStartFromEditorHandler)
        {
          return (T) handler;
        }
      }
    }
    return null;
  }
}
