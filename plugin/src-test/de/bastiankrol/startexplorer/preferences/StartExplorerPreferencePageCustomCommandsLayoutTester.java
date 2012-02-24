package de.bastiankrol.startexplorer.preferences;

import org.eclipse.swt.widgets.Shell;

public class StartExplorerPreferencePageCustomCommandsLayoutTester extends
    StartExplorerPreferencePageCustomCommands
{
  StartExplorerPreferencePageCustomCommandsLayoutTester(Shell shell)
  {
    this.init(null);
    this.createContents(shell);
    this.initializeDefaults();
  }

  public static void main(String[] args)
  {
    LayoutTesterDelegate
        .openPage(new StartExplorerPreferencePageCustomCommandsLayoutTester(
            LayoutTesterDelegate.shell));
  }
}
