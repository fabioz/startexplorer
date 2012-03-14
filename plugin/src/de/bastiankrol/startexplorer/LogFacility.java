package de.bastiankrol.startexplorer;

import static de.bastiankrol.startexplorer.Activator.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;

public class LogFacility
{
  private static final String DEBUG_SWITCH = PLUGIN_ID + "/debug";

  private static final boolean WRITE_DEBUG_LOG = "true"
      .equalsIgnoreCase(Platform.getDebugOption(DEBUG_SWITCH));

  private static final DateFormat DATE_FORMAT_DEBUG = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss:SSS");

  /**
   * In contrast to the other logXxx methods, this does not write to the log
   * file but to {@link System#out} as described in http://wiki.eclipse.org/
   * FAQ_How_do_I_use_the_platform_debug_tracing_facility%3F
   * 
   * This method only prints the log message if Eclipse was started with -debug
   * switch and if there is an .options file with
   * de.bastiankrol.startexplorer/debug = true
   * 
   * @param message the message to log
   */
  public void logDebug(String message)
  {
    if (WRITE_DEBUG_LOG && (getDefault() == null || getDefault().isDebugging()))
    {
      System.out.println(DATE_FORMAT_DEBUG.format(new Date()) + " ["
          + PLUGIN_ID + "] - " + message);
    }
  }

  /**
   * Writes a message to Eclipse's error log
   * 
   * @param status message status, use
   *          <ul>
   *          <li>org.eclipse.core.runtime.IStatus.ERROR</li>
   *          <li>org.eclipse.core.runtime.IStatus.INFO</li>
   *          <li>org.eclipse.core.runtime.IStatus.WARNING</li>
   *          </ul>
   * 
   * @param message the message to write to the error log
   */
  private void logMessage(int status, String message)
  {
    getDefault().getLog().log(createStatus(status, message, null));
  }

  public void logInfo(String message)
  {
    logMessage(IStatus.INFO, message);
  }

  public void logWarning(String message)
  {
    logMessage(IStatus.WARNING, message);
  }

  public void logError(String message)
  {
    logMessage(IStatus.ERROR, message);
  }

  /**
   * Writes an exception to Eclipse's error log.
   * 
   * @param t the Throwable to write to the log
   */
  public void logException(Throwable t)
  {
    getDefault().getLog().log(createStatus(IStatus.ERROR, null, t));
  }

  /**
   * Writes an exception to Eclipse's error log.
   * 
   * @param message the message to write to the error log
   * @param t the Throwable to write to the log
   */
  public void logException(String message, Throwable t)
  {
    getDefault().getLog().log(createStatus(IStatus.ERROR, message, t));
  }

  /**
   * Creates a Status object for the eclipse error log.
   * 
   * <ul>
   * <li>If <code>message</code> is <code>null</code> and <code>throwable</code>
   * is <code>null</code> the message of the status object will be the empty
   * string.</li>
   * <li>If <code>message</code> is not <code>null</code> and
   * <code>throwable</code> is <code>null</code> the message of the status
   * object will be <code>message</code>.</li>
   * <li>If <code>message</code> is <code>null</code> and <code>throwable</code>
   * is not <code>null</code> the message of the status object will be
   * <code>throwable.getLocalizedMessage()</code>, if available, otherwise the
   * empty string.</li>
   * </ul>
   * 
   * @param status the status code
   * @param message the message to display
   * @param throwable a throwable
   * @return the status object
   */
  private IStatus createStatus(int status, String message, Throwable throwable)
  {
    if (message == null && throwable != null
        && throwable.getLocalizedMessage() != null)
    {
      message = throwable.getLocalizedMessage();
    }
    else if (message == null)
    {
      message = "";
    }
    return new Status(status, getDefault().getBundle().getSymbolicName(),
        status, message, throwable);
  }

}
