package de.bastiankrol.startexplorer.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.widgets.Shell;

import de.bastiankrol.startexplorer.crossplatform.CustomDesktopEnvironmentContainer;
import de.bastiankrol.startexplorer.crossplatform.DesktopEnvironment;
import de.bastiankrol.startexplorer.crossplatform.WorkingDirectoryMode;

public class StartExplorerPreferencePageDesktopEnvironmentLayoutTester extends
    StartExplorerPreferencePageDesktopEnvironment
{
  StartExplorerPreferencePageDesktopEnvironmentLayoutTester(Shell shell)
  {
    this.init(null);
    this.createContents(shell);
    this.initializeDefaults();
  }

  protected IPreferenceStore doGetPreferenceStore()
  {
    IPreferenceStore preferenceStore = new PreferenceStore();
    return preferenceStore;
  }

  @Override
  PreferenceUtil getPreferenceUtil()
  {
    return new PreferenceUtil()
    {
      @Override
      void loadPreferencesFromStoreIntoPreferenceModel(
          PreferenceModel preferenceModel)
      {
        // This values will be overwritten by initializeDefaults()
        // but there need to be some values there during createContents ->
        // initializeValues
        preferenceModel.setAutoDetectDesktopEnvironment(false);
        preferenceModel.setUseCustomeDesktopEnvironment(false);
        preferenceModel
            .setSelectedDesktopEnvironment(DesktopEnvironment.UNKNOWN);
        preferenceModel
            .setCustomDesktopEnvironmentContainer(new CustomDesktopEnvironmentContainer(
                "commandForStartFileManager",
                "commandForStartFileManagerAndSelectFile",
                WorkingDirectoryMode.NONE, "commandForStartShell",
                WorkingDirectoryMode.NONE, "commandForStartSystemApplication",
                WorkingDirectoryMode.NONE, WorkingDirectoryMode.NONE, false,
                false));
      }
    };
  }

  public static void main(String[] args)
  {
    LayoutTesterDelegate
        .openPage(new StartExplorerPreferencePageDesktopEnvironmentLayoutTester(
            LayoutTesterDelegate.shell));
  }
}
