package de.bastiankrol.startexplorer.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;
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
      }
    };
  }

  public static void main(String[] args)
  {
    LayoutTesterDelegate
        .openPage(new StartExplorerPreferencePageGeneralLayoutTester(
            LayoutTesterDelegate.shell));
  }
}
