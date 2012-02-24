package de.bastiankrol.startexplorer;

import org.eclipse.jface.preference.IPreferenceStore;

import de.bastiankrol.startexplorer.crossplatform.DesktopEnvironment;
import de.bastiankrol.startexplorer.crossplatform.DesktopEnvironmentAutoDetecter;
import de.bastiankrol.startexplorer.crossplatform.IRuntimeExecCalls;
import de.bastiankrol.startexplorer.crossplatform.RuntimeExecCallsFactory;
import de.bastiankrol.startexplorer.customcommands.CustomCommandEditorFactory;
import de.bastiankrol.startexplorer.customcommands.CustomCommandResourceViewFactory;
import de.bastiankrol.startexplorer.preferences.PreferenceModel;
import de.bastiankrol.startexplorer.preferences.PreferenceUtil;
import de.bastiankrol.startexplorer.util.PathChecker;

/**
 * Container for some singletons that are used by this plug-in.
 * 
 * @author Bastian Krol
 */
public class PluginContext
{
  private IRuntimeExecCalls runtimeExecCalls;
  private CustomCommandResourceViewFactory customCommandResourceViewFactory;
  private CustomCommandEditorFactory customCommandEditorFactory;
  private PathChecker pathChecker;
  PreferenceModel preferenceModel;
  private PreferenceUtil preferenceUtil;

  void init()
  {
    this.pathChecker = new PathChecker();
    this.customCommandResourceViewFactory = new CustomCommandResourceViewFactory();
    this.customCommandEditorFactory = new CustomCommandEditorFactory();
    this.preferenceUtil = new PreferenceUtil();
  }

  void stop()
  {
    this.preferenceModel = null;
    this.customCommandResourceViewFactory.doCleanupAtPluginStop();
    this.customCommandResourceViewFactory = null;
    this.customCommandEditorFactory.doCleanupAtPluginStop();
    this.customCommandEditorFactory = null;
    this.pathChecker = null;
    this.runtimeExecCalls = null;
  }

  /**
   * This resets the (possibly) already chosen IRuntimeExecCalls instance to
   * {@code null}. The next time, the instance is queried via
   * {@link #getRuntimeExecCalls()}, it is chosen again by inspecting the
   * preference store.
   */
  public void resetRuntimeExecCalls()
  {
    this.runtimeExecCalls = null;
  }

  /**
   * Returns the shared instance of RuntimeExecCalls
   * 
   * @return the shared instance of RuntimeExecCalls
   */
  public IRuntimeExecCalls getRuntimeExecCalls()
  {
    if (this.runtimeExecCalls == null)
    {
      this.chooseRuntimeExecCalls();
    }
    return this.runtimeExecCalls;
  }

  /**
   * Selects the runtime exec calls implementation depending on the selected
   * desktop environment from the preferences.
   */
  private void chooseRuntimeExecCalls()
  {
    if (this.preferenceUtil.getAutoDetectDesktopEnvironment())
    {
      this.runtimeExecCalls = chooseRuntimeExecCalls(DesktopEnvironmentAutoDetecter
          .findDesktopEnvironment());
    }
    else if (this.preferenceUtil.getUseCustomDesktopEnvironment())
    {
      this.runtimeExecCalls = RuntimeExecCallsFactory
          .custom(this.preferenceUtil
              .getCustomDesktopEnvironmentContainerFromPreferences());
    }
    else
    {
      this.runtimeExecCalls = chooseRuntimeExecCalls(this.preferenceUtil
          .getSelectedDesktopEnvironment());
    }
  }

  private IRuntimeExecCalls chooseRuntimeExecCalls(
      DesktopEnvironment desktopEnvironment)
  {
    switch (desktopEnvironment)
    {
      case WINDOWS:
        return RuntimeExecCallsFactory.windows();
      case LINUX_GNOME:
        return RuntimeExecCallsFactory.linuxGnome();
      case LINUX_KDE:
        return RuntimeExecCallsFactory.linuxKde();
      case LINUX_XFCE:
        return RuntimeExecCallsFactory.linuxXfce();
      case LINUX_LXDE:
        return RuntimeExecCallsFactory.linuxLxde();
      case MAC_OS:
        return RuntimeExecCallsFactory.macOs();
      case LINUX_UNKNOWN:
        // fall through
      case UNKNOWN:
        return RuntimeExecCallsFactory.unsupported();
      default:
        throw new IllegalArgumentException("Unknown desktop environment: "
            + desktopEnvironment);
    }
  }

  /**
   * @return {@code true} if and only if the current operating system's/desktop
   *         manager's file manager supports selecting files (as opposed to just
   *         opening a certain directory) on startup
   */
  public boolean isFileSelectionSupportedByFileManager()
  {
    return this.getRuntimeExecCalls().isFileSelectionSupportedByFileManager();
  }

  /**
   * Returns the shared instance of the PathChecker
   * 
   * @return the shared instance of the PathChecker
   */
  public PathChecker getPathChecker()
  {
    return this.pathChecker;
  }

  public CustomCommandResourceViewFactory getCustomCommandResourceViewFactory()
  {
    return customCommandResourceViewFactory;
  }

  public CustomCommandEditorFactory getCustomCommandEditorFactory()
  {
    return customCommandEditorFactory;
  }

  public void ensurePreferencesHaveBeenLoadedFromStore()
  {
    if (this.preferenceModel == null)
    {
      this.preferenceModel = new PreferenceModel();
      this.loadPreferencesFromEclipseStore();
    }
  }

  void loadPreferencesFromEclipseStore()
  {
    this.preferenceModel.loadPreferencesFromStore(this.preferenceUtil);
  }

  public void savePreferencesToStore(IPreferenceStore store)
  {
    this.checkModelHasBeenLoaded();
    this.preferenceModel.storeValues(store);
  }

  private void checkModelHasBeenLoaded()
  {
    if (this.preferenceModel == null)
    {
      throw new IllegalStateException(
          "preferenceModel has not been initialized and loaded yet.");
    }
  }

  public void initializePreferencesFromDefault()
  {
    this.checkModelHasBeenLoaded();
    this.preferenceModel.initializeFromDefaults();
  }

  public PreferenceModel getPreferenceModel()
  {
    this.checkModelHasBeenLoaded();
    return this.preferenceModel;
  }
}
