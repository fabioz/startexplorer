package de.bastiankrol.startexplorer;

import de.bastiankrol.startexplorer.crossplatform.CustomDesktopEnvironmentContainer;
import de.bastiankrol.startexplorer.crossplatform.DesktopEnvironment;
import de.bastiankrol.startexplorer.crossplatform.WorkingDirectoryMode;

public class ActivatorForDesktopEnvironmentLayoutTests extends Activator
{
  @Override
  void initContext()
  {
    this.context = new PluginContext()
    {
      @Override
      void loadPreferencesFromEclipseStore()
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
}
