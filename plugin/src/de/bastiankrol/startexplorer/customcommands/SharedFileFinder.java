package de.bastiankrol.startexplorer.customcommands;

import static de.bastiankrol.startexplorer.Activator.*;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

/**
 * Searches each project in the workspace for custom command definitions that
 * are stored as shared files.
 */
public class SharedFileFinder
{
  public static final String TITLE_SHARED_FILES_LATER = "Shared files will be added later...";

  public static final String MESSAGE_SHARED_FILES_LATER = "The job which scans the workspace has not yet finished, "
      + "therefore custom commands stored as shared files in the workspace "
      + "have not yet been added. Once it has finished, you will also find "
      + "these custom commands here.";

  private enum SharedFileFinderStatus
  {
    INIT, HAS_BEEN_STARTED, DONE;
  }

  /*
   * The internal state is comprised of status, searchJob and commandConfigs.
   * None of them must be read or written without acquiring the lock. Also, no
   * long running operations must be executed while holding the lock.
   */
  private Object lock = new Object();
  private SharedFileFinderStatus status;
  private SharedFileFinderJob searchJob;
  private List<CommandConfig> commandConfigs;

  public SharedFileFinder()
  {
    this.reset();
  }

  private void reset()
  {
    getLogFacility().logDebug("reset() waiting for lock");
    synchronized (this.lock)
    {
      getLogFacility().logDebug("reset() got lock");
      this.status = SharedFileFinderStatus.INIT;
      this.commandConfigs = null;
      this.searchJob = null;
    }
    getLogFacility().logDebug("reset() done");
  }

  /**
   * Forces a new search for in the workspace for custom command definitions
   * that are stored as shared files, no matter if the search job has been
   * executed before.
   */
  public void forceRefreshCustomCommandDefinitions()
  {
    getLogFacility().logDebug(
        "forceRefreshCustomCommandDefinitions() waiting for lock");
    synchronized (this.lock)
    {
      getLogFacility().logDebug(
          "forceRefreshCustomCommandDefinitions() got lock");
      this.reset();
      this.startSearch();
    }
    getLogFacility().logDebug(
        "forceRefreshCustomCommandDefinitions() done");
  }

  /**
   * Starts the search job asynchronously, the search job will search each
   * project in the workspace for custom command definitions that are stored as
   * shared files. If the workspace has been searched before, the cached result
   * will be returned.
   * 
   * @see SharedFileFinder#forceRefreshCustomCommandDefinitions()
   */
  public void startSearch()
  {
    getLogFacility().logDebug(
        "startSearch() waiting for lock");
    synchronized (this.lock)
    {
      getLogFacility().logDebug("startSearch() got lock");
      if (this.status == SharedFileFinderStatus.DONE)
      {
        return;
      }
      this.status = SharedFileFinderStatus.HAS_BEEN_STARTED;
      this.searchJob = new SharedFileFinderJob();
      this.registerJobChangeListener();
      this.searchJob.schedule();
      getPluginContext()
          .getLogFacility()
          .logDebug(
              "StartExplorer search job for custom command config stored as shared files has been started asynchronously.");
    }
    getLogFacility().logDebug("startSearch() done");
  }

  private void registerJobChangeListener()
  {
    this.searchJob.addJobChangeListener(new JobChangeAdapter()
    {
      @Override
      public void done(IJobChangeEvent event)
      {
        IStatus result = event.getResult();
        if (result.isOK())
        {
          onFinished();
        }
        else
        {
          onTerminatedAbruptly();
        }
      }

      private void onFinished()
      {
        getLogFacility().logDebug(
            "onFinished() waiting for lock");
        synchronized (lock)
        {
          getLogFacility().logDebug("onFinished() got lock");
          status = SharedFileFinderStatus.DONE;
          commandConfigs = searchJob.getCommandConfigs();
          searchJob = null;
          getPluginContext()
              .getLogFacility()
              .logDebug(
                  "StartExplorer search job for custom command config stored as shared files has finished successfully.");
        }
      }

      private void onTerminatedAbruptly()
      {
        getPluginContext()
            .getLogFacility()
            .logDebug(
                "StartExplorer search job for custom command config stored as shared files has terminated with non-OK state.");
        reset();
      }

    });
  }

  public boolean hasFinished()
  {
    getLogFacility().logDebug(
        "hasFinished() waiting for lock");
    synchronized (this.lock)
    {
      getLogFacility().logDebug("hasFinished() got lock");
      getLogFacility().logDebug("hasFinished() done");
      return this.status == SharedFileFinderStatus.DONE;
    }
  }

  public List<CommandConfig> getResult()
  {
    getLogFacility()
        .logDebug("getResult() waiting for lock");
    synchronized (this.lock)
    {
      getLogFacility().logDebug("getResult() got lock");
      if (this.hasFinished())
      {
        return this.commandConfigs;
      }
      else
      {
        return null;
      }
    }
  }
}
