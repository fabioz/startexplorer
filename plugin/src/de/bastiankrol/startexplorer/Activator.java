package de.bastiankrol.startexplorer;

import static de.bastiankrol.startexplorer.preferences.PreferenceConstantsAndDefaults.*;

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

  /** The shared instance */
  private static Activator defaultInstance;

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

  public static LogFacility getLogFacility()
  {
    if (defaultInstance != null && defaultInstance.getContext() != null)
    {
      return defaultInstance.getContext().getLogFacility();
    }
    else
    {
      return new LogFacility();
    }
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

  static void injectDefaultInstanceForTest(Activator instance)
  {
    defaultInstance = instance;
  }
}
