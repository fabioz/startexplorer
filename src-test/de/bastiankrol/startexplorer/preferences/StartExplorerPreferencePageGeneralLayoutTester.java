package de.bastiankrol.startexplorer.preferences;

import org.eclipse.swt.widgets.Shell;

public class StartExplorerPreferencePageGeneralLayoutTester extends
    StartExplorerPreferencePageGeneral
{
  StartExplorerPreferencePageGeneralLayoutTester(Shell shell)
  {
    this.init(null);
    this.createContents(shell);
    this.initializeDefaults();
  }

  public static void main(String[] args)
  {
    LayoutTesterDelegate
        .openPage(new StartExplorerPreferencePageGeneralLayoutTester(
            LayoutTesterDelegate.shell));
  }
}
