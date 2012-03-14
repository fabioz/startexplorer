package de.bastiankrol.startexplorer.customcommands;

import static de.bastiankrol.startexplorer.Activator.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.json.simple.parser.ParseException;

import de.bastiankrol.startexplorer.util.Util;

class SharedFileFinderJob extends Job
{
  private static final String JOB_NAME = "Searching for StartExplorer custom command definitions";
  private static final String BEGIN_TASK_MESSAGE = "Searching projects: ";

  private volatile List<CommandConfig> commandConfigs = new ArrayList<CommandConfig>();

  SharedFileFinderJob()
  {
    super(JOB_NAME);
  }

  @Override
  protected IStatus run(IProgressMonitor monitor)
  {
    try
    {
      List<IProject> projects = new ArrayList<IProject>();
      IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
      IResource[] projectResources = workspaceRoot.members();
      for (IResource projectResource : projectResources)
      {
        if (projectResource instanceof IProject)
        {
          IProject project = (IProject) projectResource;
          if (project.isOpen() && project.isAccessible())
          {
            projects.add(project);
          }
        }
      }

      monitor.beginTask(BEGIN_TASK_MESSAGE, projects.size());
      for (IProject project : projects)
      {
        monitor.subTask(project.getName());
        this.search(project);
        monitor.worked(1);
      }
      return Status.OK_STATUS;
    }
    catch (CoreException e)
    {
      getPluginContext()
          .getLogFacility()
          .logException(
              "A problem occured while searching for StartExplorer custom command definitions.",
              e);
      return Status.CANCEL_STATUS;
    }
  }

  private void search(IResource resource) throws CoreException
  {
    resource.accept(new IResourceVisitor()
    {
      @Override
      public boolean visit(IResource resource) throws CoreException
      {
        if (resource.getType() == IResource.FILE)
        {
          if (resource.getFileExtension() != null
              && resource.getFileExtension().equals("startexplorer"))
          {
            CommandConfig commandConfig = SharedFileFinderJob.this
                .loadCommandConfigFromSharedFile(resource);
            if (commandConfig != null)
            {
              SharedFileFinderJob.this.commandConfigs.add(commandConfig);
            }
          }
          return false;
        }
        else
        {
          return true;
        }
      }
    });
  }

  private CommandConfig loadCommandConfigFromSharedFile(IResource resource)
  {
    try
    {
      return new SharedFileManager().importCommandConfigFromFile(Util
          .getIFileInWorkspace(resource));
    }
    catch (CoreException e)
    {
      getLogFacility().logException(
          "Could not import custom command from " + resource.getFullPath()
              + " due to an unexpected CoreException.", e);
      return null;
    }
    catch (IOException e)
    {
      getLogFacility().logException(
          "Could not import custom command from " + resource.getFullPath()
              + " due to an IOException.", e);
      return null;
    }
    catch (ParseException e)
    {
      getLogFacility().logException(
          "Could not import custom command from " + resource.getFullPath()
              + " because the content is not valid JSON.", e);
      return null;
    }
  }

  List<CommandConfig> getCommandConfigs()
  {
    return this.commandConfigs;
  }
}
