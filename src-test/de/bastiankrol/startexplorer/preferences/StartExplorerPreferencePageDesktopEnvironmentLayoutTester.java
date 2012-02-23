package de.bastiankrol.startexplorer.preferences;

import org.eclipse.swt.widgets.Shell;

import de.bastiankrol.startexplorer.ActivatorForDesktopEnvironmentLayoutTests;

public class StartExplorerPreferencePageDesktopEnvironmentLayoutTester extends
    StartExplorerPreferencePageDesktopEnvironment
{
  StartExplorerPreferencePageDesktopEnvironmentLayoutTester(Shell shell)
  {
    this.init(null);
    this.createContents(shell);
    this.initializeDefaults();
  }

  public static void main(String[] args)
  {
    LayoutTesterDelegate
        .replaceActivator(new ActivatorForDesktopEnvironmentLayoutTests());
    LayoutTesterDelegate
        .openPage(new StartExplorerPreferencePageDesktopEnvironmentLayoutTester(
            LayoutTesterDelegate.shell));
  }
}
