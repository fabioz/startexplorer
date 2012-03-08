package de.bastiankrol.startexplorer;

import static de.bastiankrol.startexplorer.preferences.PreferenceConstantsAndDefaults.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import de.bastiankrol.startexplorer.customcommands.CommandConfig;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin
{
  /** The plug-in ID */
  public static final String PLUGIN_ID = "de.bastiankrol.startexplorer";

  private static final boolean WRITE_DEBUG_LOG = "true"
      .equalsIgnoreCase(Platform.getDebugOption(PLUGIN_ID + "/debug"));

  /** The shared instance */
  private static Activator defaultInstance;

  private static final DateFormat DATE_FORMAT_DEBUG = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss:SSS");

  PluginContext pluginContext;

  /**
   * The constructor
   */
  public Activator()
  {
    super();
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  public void start(BundleContext context) throws Exception
  {
    super.start(context);
    init();
  }

  private void init()
  {
    this.initContext();
    defaultInstance = this;
    this.pluginContext.getSharedFileFinder().startSearch();
  }

  void initContext()
  {
    this.pluginContext = new PluginContext();
    this.pluginContext.init();
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  public void stop(BundleContext context) throws Exception
  {
    this.pluginContext.stop();
    defaultInstance = null;
    super.stop(context);
  }

  /**
   * Returns the shared instance of the Activator
   * 
   * @return the shared instance of the Activator
   */
  public static Activator getDefault()
  {
    return defaultInstance;
  }

  public static PluginContext getPluginContext()
  {
    return defaultInstance.getContext();
  }

  PluginContext getContext()
  {
    return this.pluginContext;
  }

  /**
   * Returns an image descriptor for the image file at the given plug-in
   * relative path
   * 
   * @param path the path
   * @return the image descriptor
   */
  public static ImageDescriptor getImageDescriptor(String path)
  {
    return imageDescriptorFromPlugin(PLUGIN_ID, path);
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#initializeDefaultPreferences(org.eclipse.jface.preference.IPreferenceStore)
   */
  @Override
  protected void initializeDefaultPreferences(IPreferenceStore store)
  {
    // These settings will show up when Preference dialog
    // opens up for the first time.
    store.setDefault(KEY_NUMBER_OF_CUSTOM_COMMANDS,
        DEFAULT_CUSTOM_COMMANDS.length);
    for (int i = 0; i < DEFAULT_CUSTOM_COMMANDS.length; i++)
    {
      CommandConfig commandConfig = DEFAULT_CUSTOM_COMMANDS[i];
      store.setDefault(getCommandEnabledForResourcesMenuKey(i),
          commandConfig.isEnabledForResourcesMenu());
      store.setDefault(getCommandNameForResourcesMenuKey(i),
          commandConfig.getNameForResourcesMenu());
      store.setDefault(getCommandEnabledForTextSelectionMenuKey(i),
          commandConfig.isEnabledForTextSelectionMenu());
      store.setDefault(getCommandNameForTextSelectionMenuKey(i),
          commandConfig.getNameForTextSelectionMenu());
      store.setDefault(getPassSelectedTextKey(i),
          commandConfig.isPassSelectedText());
      store.setDefault(getCommandKey(i), commandConfig.getCommand());
    }
  }

  /**
   * In contrast to the other logXxx methods, this does not write to the log
   * file but to {@link System#out} as described in http://wiki.eclipse.org/
   * FAQ_How_do_I_use_the_platform_debug_tracing_facility%3F
   * 
   * @param message
   */
  public static void logDebug(String message)
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
  private static void logMessage(int status, String message)
  {
    getDefault().getLog().log(createStatus(status, message, null));
  }

  public static void logInfo(String message)
  {
    logMessage(IStatus.INFO, message);
  }

  public static void logWarning(String message)
  {
    logMessage(IStatus.WARNING, message);
  }

  public static void logError(String message)
  {
    logMessage(IStatus.ERROR, message);
  }

  /**
   * Writes an exception to Eclipse's error log.
   * 
   * @param t the Throwable to write to the log
   */
  public static void logException(Throwable t)
  {
    getDefault().getLog().log(createStatus(IStatus.ERROR, null, t));
  }

  /**
   * Writes an exception to Eclipse's error log.
   * 
   * @param message the message to write to the error log
   * @param t the Throwable to write to the log
   */
  public static void logException(String message, Throwable t)
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
  private static IStatus createStatus(int status, String message,
      Throwable throwable)
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

  static void injectDefaultInstanceForTest(Activator instance)
  {
    defaultInstance = instance;
  }
}
