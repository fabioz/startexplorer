package de.bastiankrol.startexplorer.preferences;

import static de.bastiankrol.startexplorer.preferences.PreferenceConstants.*;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.bastiankrol.startexplorer.Activator;

public class StartExplorerPreferencePage extends PreferencePage implements
    IWorkbenchPreferencePage, SelectionListener, ModifyListener
{

  private Text textField01;
  private Text textField02;

  // TODO
  // http://help.eclipse.org/help33/topic/org.eclipse.platform.doc.isv/guide/preferences_prefs_implement.htm

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
   */
  public void init(IWorkbench workbench)
  {
    // do nothing
  }
  
  
  protected IPreferenceStore doGetPreferenceStore()
  {
    return Activator.getDefault().getPreferenceStore();
  }


  private void initializeValues()
  {
    IPreferenceStore store = getPreferenceStore();
    this.textField01.setText(store.getString(COMMAND_01));
    this.textField02.setText(store.getString(COMMAND_02));
  }

  private void storeValues()
  {
    IPreferenceStore store = getPreferenceStore();
    store.setValue(COMMAND_01, this.textField01.getText());
    store.setValue(COMMAND_02, this.textField02.getText());
  }

  private void initializeDefaults()
  {
    IPreferenceStore store = getPreferenceStore();
    this.textField01.setText(store.getDefaultString(COMMAND_01));
    this.textField02.setText(store.getDefaultString(COMMAND_02));
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
    initializeDefaults();
  }

/**
 * {@inheritDoc}
 *
 * @see org.eclipse.jface.preference.PreferencePage#performOk()
 */
  public boolean performOk()
  {
    storeValues();
    Activator.getDefault().savePluginPreferences();
    return true;
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createContents(Composite parent)
  {
    Composite compositeTextField01 = createComposite(parent, 2);
    Label labelTextField01 = createLabel(compositeTextField01, "Command 1");
    this.textField01 = createTextField(compositeTextField01);
    Button buttonTextField01 = createPushButton(compositeTextField01, "Change");

    Composite compositeTextField02 = createComposite(parent, 2);
    Label labelTextField02 = createLabel(compositeTextField02, "Command 2");
    this.textField02 = createTextField(compositeTextField02);
    Button buttonTextField02 = createPushButton(compositeTextField02, "Change");

    this.initializeValues();

    return new Composite(parent, SWT.NULL);
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.jface.preference.PreferencePage#doComputeSize()
   */
  @Override
  public Point doComputeSize()
  {
    return super.doComputeSize();
  }

  /**
   * Creates composite control and sets the default layout data.
   * 
   * @param parent
   *          the parent of the new composite
   * @param numColumns
   *          the number of columns for the new composite
   * @return the newly-created coposite
   */
  private Composite createComposite(Composite parent, int numColumns)
  {
    Composite composite = new Composite(parent, SWT.NULL);

    // GridLayout
    GridLayout layout = new GridLayout();
    layout.numColumns = numColumns;
    composite.setLayout(layout);

    // GridData
    GridData data = new GridData();
    data.verticalAlignment = GridData.FILL;
    data.horizontalAlignment = GridData.FILL;
    composite.setLayoutData(data);
    return composite;
  }

  /**
   * Create a text field specific for this application
   * 
   * @param parent
   *          the parent of the new text field
   * @return the new text field
   */
  private Text createTextField(Composite parent)
  {
    Text text = new Text(parent, SWT.SINGLE | SWT.BORDER);
    text.addModifyListener(this);
    GridData data = new GridData();
    data.horizontalAlignment = GridData.FILL;
    data.grabExcessHorizontalSpace = true;
    data.verticalAlignment = GridData.CENTER;
    data.grabExcessVerticalSpace = false;
    text.setLayoutData(data);
    return text;
  }

  /**
   * Utility method that creates a label instance and sets the default layout
   * data.
   * 
   * @param parent
   *          the parent for the new label
   * @param text
   *          the text for the new label
   * @return the new label
   */
  private Label createLabel(Composite parent, String text)
  {
    Label label = new Label(parent, SWT.LEFT);
    label.setText(text);
    GridData data = new GridData();
    data.horizontalSpan = 2;
    data.horizontalAlignment = GridData.FILL;
    label.setLayoutData(data);
    return label;
  }

  /**
   * Utility method that creates a push button instance and sets the default
   * layout data.
   * 
   * @param parent
   *          the parent for the new button
   * @param label
   *          the label for the new button
   * @return the newly-created button
   */
  private Button createPushButton(Composite parent, String label)
  {
    Button button = new Button(parent, SWT.PUSH);
    button.setText(label);
    button.addSelectionListener(this);
    GridData data = new GridData();
    data.horizontalAlignment = GridData.FILL;
    button.setLayoutData(data);
    return button;
  }

   public void widgetDefaultSelected(SelectionEvent event)
  {
    // TODO Auto-generated method stub

  }

  public void widgetSelected(SelectionEvent event)
  {
    // TODO Auto-generated method stub

  }

  public void modifyText(ModifyEvent event)
  {
    // TODO Auto-generated method stub

  }
}
