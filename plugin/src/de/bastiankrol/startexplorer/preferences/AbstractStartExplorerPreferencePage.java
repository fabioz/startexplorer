package de.bastiankrol.startexplorer.preferences;

import static de.bastiankrol.startexplorer.Activator.*;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.bastiankrol.startexplorer.Activator;

abstract class AbstractStartExplorerPreferencePage extends PreferencePage
    implements IWorkbenchPreferencePage
{
  private Composite parent;
  private PreferenceUtil preferenceUtil;

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
   */
  public void init(IWorkbench workbench)
  {
    this.preferenceUtil = new PreferenceUtil();
    getPluginContext().ensurePreferencesHaveBeenLoadedFromStore();
  }

  void createPanel(Composite parent)
  {
    Composite panel = new Composite(parent, parent.getStyle());
    GridData gridDataForPanel = new GridData(SWT.FILL, SWT.FILL, true, true);
    panel.setLayoutData(gridDataForPanel);
    this.setPanel(panel);
  }

  Composite getPanel()
  {
    return this.parent;
  }

  void setPanel(Composite parent)
  {
    this.parent = parent;
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.jface.preference.PreferencePage#doGetPreferenceStore()
   */
  @Override
  protected IPreferenceStore doGetPreferenceStore()
  {
    return this.getPreferenceUtil().retrievePreferenceStore();
  }

  PreferenceUtil getPreferenceUtil()
  {
    // mostly provided so that layout can be easily tested in
    // StartExplorerPreferencePageGeneralLayoutTester, ...
    return this.preferenceUtil;
  }

  abstract void refreshViewFromModel();

  /**
   * Store values to preference store
   */
  private void storeValues()
  {
    IPreferenceStore store = getPreferenceStore();
    getPluginContext().savePreferencesToStore(store);
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
   */
  @Override
  protected void performDefaults()
  {
    super.performDefaults();
    this.initializeDefaults();
  }

  /**
   * Initializes the preference model with the defaults, if no preferences have
   * been set by the user yet.
   */
  protected void initializeDefaults()
  {
    getPluginContext().initializePreferencesFromDefault();
    this.refreshViewFromModel();
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.jface.preference.PreferencePage#performOk()
   */
  @Override
  public boolean performOk()
  {
    this.storeValues();
    Activator.getDefault().savePluginPreferences();
    return true;
  }

  PreferenceModel getModel()
  {
    return getPluginContext().getPreferenceModel();
  }

  void setGridLayoutWithTwoColumns(Composite composite)
  {
    GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns = 2;
    composite.setLayout(gridLayout);
  }

  Composite createCompositeWithGridLayoutWithTwoColumns(Composite parent)
  {
    Composite composite = new Composite(parent, SWT.NULL);
    setGridLayoutWithTwoColumns(composite);
    return composite;
  }
}
