package de.bastiankrol.startexplorer.preferences;

import static de.bastiankrol.startexplorer.util.Util.*;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.bastiankrol.startexplorer.Activator;
import de.bastiankrol.startexplorer.preferences.SeparatorData.SeparatorType;

/**
 * Preference page for StartExplorer
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$ $Author:$
 */
public class StartExplorerPreferencePage extends PreferencePage implements
    IWorkbenchPreferencePage
{

  private PreferenceModel preferenceModel;
  private PreferenceUtil preferenceUtil;

  private Composite parent;
  private Table tableCommands;
  private Text textCustomCopyResourceSeparatorString;

  private Button radioButtonUnixLinebreak;
  private Button radioButtonWindowsLinebreak;
  private Button radioButtonMacLinebreak;
  private Button radioButtonTab;
  private Button radioButtonCustomSeparator;

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
   */
  public void init(IWorkbench workbench)
  {
    if (false)
    {
      workbench.getClass();
    }
    this.preferenceModel = new PreferenceModel();
    this.preferenceUtil = new PreferenceUtil();
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
    return this.preferenceUtil;
  }

  /**
   * Initializes the preference model by loading the stored preferences from the
   * preference store.
   */
  private void initializeValues()
  {
    this.preferenceModel.loadPreferencesFromStore(this.getPreferenceUtil());
    this.refreshViewFromModel();
  }

  /**
   * Store values to preference store
   */
  private void storeValues()
  {
    IPreferenceStore store = getPreferenceStore();
    this.storeValues(store);
  }

  /**
   * Store the values to <code>store</code>.
   * 
   * @param store the {@link IPreferenceStore} to store the preferences in.
   */
  protected void storeValues(IPreferenceStore store)
  {
    this.preferenceModel.storeValues(store);
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
    this.preferenceModel.initializeFromDefaults();
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

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createContents(Composite parent)
  {
    // ?: Do we need to create a new composite as a child of parent and return
    // this new composite instead of manipulating parent directly?

    // do basic layout for preference page
    this.parent = parent;
    GridLayout gridLayoutParent = new GridLayout();
    gridLayoutParent.numColumns = 2;
    this.parent.setLayout(gridLayoutParent);

    // Upper left part: command config table
    this.tableCommands = this.createTable(this.parent);

    // Upper right part: buttons to control the command config table (add, edit,
    // delete, up, down, ...)
    this.createControlButtonSection();

    // Lower part: section for configurable separator for the copy resource path
    // command
    this.createCopyResourcePathSeparatorSection();

    // fetch models from value and put them into the gui elements
    this.initializeValues();

    return this.parent;
  }

  /**
   * Creates the table for the preference page.
   * 
   * @param parent parent composite, the table will be a child of
   *          <code>parent</code>
   * @return
   */
  private Table createTable(Composite parent)
  {
    String[] titles = { "Command", "Name/Resources", "Name/Text Selection" };
    Table table =
        new Table(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
    GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
    table.setLayoutData(data);
    table.setLinesVisible(true);
    table.setHeaderVisible(true);
    for (int i = 0; i < titles.length; i++)
    {
      TableColumn column = new TableColumn(table, SWT.NONE);
      column.setText(titles[i]);
    }
    return table;
  }

  private void createControlButtonSection()
  {
    Composite compositeButtonColumn = new Composite(this.parent, SWT.NONE);
    compositeButtonColumn.setLayoutData(new GridData(
        GridData.VERTICAL_ALIGN_BEGINNING));
    RowLayout rowLayoutButtonColumn = new RowLayout();
    rowLayoutButtonColumn.type = SWT.VERTICAL;
    rowLayoutButtonColumn.pack = false;
    compositeButtonColumn.setLayout(rowLayoutButtonColumn);

    createButton(compositeButtonColumn, "Add").addSelectionListener(
        new EventlessSelectionAdapter()
        {
          public void widgetSelected()
          {
            StartExplorerPreferencePage.this.buttonAddPressed();
          }
        });
    createButton(compositeButtonColumn, "Edit").addSelectionListener(
        new EventlessSelectionAdapter()
        {
          public void widgetSelected()
          {
            StartExplorerPreferencePage.this.buttonEditPressed();
          }
        });
    createButton(compositeButtonColumn, "Remove").addSelectionListener(
        new EventlessSelectionAdapter()
        {
          public void widgetSelected()
          {
            StartExplorerPreferencePage.this.buttonRemovePressed();
          }
        });
    createButton(compositeButtonColumn, "Up").addSelectionListener(
        new EventlessSelectionAdapter()
        {
          public void widgetSelected()
          {
            StartExplorerPreferencePage.this.buttonUpPressed();
          }
        });
    createButton(compositeButtonColumn, "Down").addSelectionListener(
        new EventlessSelectionAdapter()
        {
          public void widgetSelected()
          {
            StartExplorerPreferencePage.this.buttonDownPressed();
          }
        });
  }

  private void createCopyResourcePathSeparatorSection()
  {
    Label labelSeparator =
        new Label(this.parent, SWT.SEPARATOR | SWT.HORIZONTAL);
    GridData gridDataSeparator = new GridData(SWT.FILL, SWT.FILL, true, false);
    gridDataSeparator.horizontalSpan = 2;
    labelSeparator.setLayoutData(gridDataSeparator);

    Composite compositeCopyResourcePathSeparator =
        new Composite(this.parent, SWT.NULL);
    GridData gridDataComposite = new GridData(SWT.FILL, SWT.FILL, true, false);
    gridDataComposite.horizontalSpan = 2;
    compositeCopyResourcePathSeparator.setLayoutData(gridDataComposite);

    GridLayout gridLayoutComposite = new GridLayout();
    gridLayoutComposite.numColumns = 2;
    compositeCopyResourcePathSeparator.setLayout(gridLayoutComposite);

    Label labelHeadline =
        new Label(compositeCopyResourcePathSeparator, SWT.NONE);
    labelHeadline.setText("Separator for \"Copy resource path to clipboard\"");
    GridData gridDataLabelHeadline =
        new GridData(SWT.FILL, SWT.FILL, true, false);
    gridDataLabelHeadline.horizontalSpan = 2;
    labelHeadline.setLayoutData(gridDataLabelHeadline);

    this.radioButtonUnixLinebreak =
        new Button(compositeCopyResourcePathSeparator, SWT.RADIO);
    this.radioButtonUnixLinebreak.setText("Unix Linebreak (LF)");
    this.radioButtonUnixLinebreak.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        preferenceModel.getSeparatorData().setSeparatorType(SeparatorType.LF);
        refreshSeparatorStuffFromModel();
      }
    });
    this.radioButtonWindowsLinebreak =
        new Button(compositeCopyResourcePathSeparator, SWT.RADIO);
    this.radioButtonWindowsLinebreak.setText("Windows Linebreak (CR+LF)");
    this.radioButtonWindowsLinebreak
        .addSelectionListener(new SelectionAdapter()
        {
          @Override
          public void widgetSelected(SelectionEvent e)
          {
            preferenceModel.getSeparatorData().setSeparatorType(
                SeparatorType.CRLF);
            refreshSeparatorStuffFromModel();
          }
        });
    this.radioButtonMacLinebreak =
        new Button(compositeCopyResourcePathSeparator, SWT.RADIO);
    this.radioButtonMacLinebreak.setText("Mac Linebreak (CR)");
    this.radioButtonMacLinebreak.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        preferenceModel.getSeparatorData().setSeparatorType(SeparatorType.CR);
        refreshSeparatorStuffFromModel();
      }
    });
    this.radioButtonTab =
        new Button(compositeCopyResourcePathSeparator, SWT.RADIO);
    this.radioButtonTab.setText("Tab");
    this.radioButtonTab.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        preferenceModel.getSeparatorData().setSeparatorType(SeparatorType.TAB);
        refreshSeparatorStuffFromModel();
      }
    });

    this.radioButtonCustomSeparator =
        new Button(compositeCopyResourcePathSeparator, SWT.RADIO);
    this.radioButtonCustomSeparator.setText("Custom String: ");
    this.radioButtonCustomSeparator.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        preferenceModel.getSeparatorData().setSeparatorType(
            SeparatorType.CUSTOM);
        refreshSeparatorStuffFromModel();
      }
    });

    this.textCustomCopyResourceSeparatorString =
        new Text(compositeCopyResourcePathSeparator, SWT.SINGLE | SWT.BORDER);
    GridData layoutDataText = new GridData();
    layoutDataText.widthHint = 50;
    this.textCustomCopyResourceSeparatorString.setLayoutData(layoutDataText);
    this.textCustomCopyResourceSeparatorString
        .addModifyListener(new ModifyListener()
        {
          public void modifyText(ModifyEvent event)
          {
            String content = textCustomCopyResourceSeparatorString.getText();
            preferenceModel.getSeparatorData()
                .setCustomSeparatorString(content);
          }
        });
  }

  /**
   * Refreshes the page from the preference model
   */
  private void refreshViewFromModel()
  {
    this.tableCommands.removeAll();
    for (CommandConfig commandConfig : this.preferenceModel
        .getCommandConfigList())
    {
      TableItem item = new TableItem(this.tableCommands, SWT.NONE);
      item.setText(0, commandConfig.getCommand());
      item.setText(1, commandConfig.getNameForResourcesMenu());
      if (!commandConfig.isEnabledForResourcesMenu())
      {
        item.setForeground(1, Display.getCurrent().getSystemColor(
            SWT.COLOR_GRAY));
      }
      item.setText(2, commandConfig.getNameForTextSelectionMenu());
      if (!commandConfig.isEnabledForTextSelectionMenu())
      {
        item.setForeground(2, Display.getCurrent().getSystemColor(
            SWT.COLOR_GRAY));
      }
    }
    for (int i = 0; i < this.tableCommands.getColumnCount(); i++)
    {
      this.tableCommands.getColumn(i).pack();
    }
    this.refreshSeparatorStuffFromModel();
  }

  private void refreshSeparatorStuffFromModel()
  {
    this.radioButtonUnixLinebreak.setSelection(false);
    this.radioButtonWindowsLinebreak.setSelection(false);
    this.radioButtonMacLinebreak.setSelection(false);
    this.radioButtonTab.setSelection(false);
    this.radioButtonCustomSeparator.setSelection(false);

    SeparatorType copyResourceSeparator =
        this.preferenceModel.getSeparatorData().getSeparatorType();
    switch (copyResourceSeparator)
    {
      case CR:
        this.radioButtonMacLinebreak.setSelection(true);
        this.textCustomCopyResourceSeparatorString.setEnabled(false);
        break;
      case CRLF:
        this.radioButtonWindowsLinebreak.setSelection(true);
        this.textCustomCopyResourceSeparatorString.setEnabled(false);
        break;
      case LF:
        this.radioButtonUnixLinebreak.setSelection(true);
        this.textCustomCopyResourceSeparatorString.setEnabled(false);
        break;
      case TAB:
        this.radioButtonTab.setSelection(true);
        this.textCustomCopyResourceSeparatorString.setEnabled(false);
        break;
      case CUSTOM:
        this.radioButtonCustomSeparator.setSelection(true);
        this.textCustomCopyResourceSeparatorString.setEnabled(true);
        break;
    }
    String customCopyResourceSeparatorString =
        this.preferenceModel.getSeparatorData().getCustomSeparatorString();
    // Won't happen when running in Eclipse, just for testing
    if (customCopyResourceSeparatorString == null)
    {
      customCopyResourceSeparatorString = "";
    }
    this.textCustomCopyResourceSeparatorString
        .setText(customCopyResourceSeparatorString);
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

  private Button createButton(Composite parent, String label)
  {
    Button button = new Button(parent, SWT.PUSH);
    button.setText(label);
    return button;
  }

  private void buttonAddPressed()
  {
    new EditCommandConfigPane(this.parent.getShell(), this.preferenceModel
        .getCommandConfigList()).open();
    this.refreshViewFromModel();
  }

  private void buttonEditPressed()
  {
    int selectionIndex = this.tableCommands.getSelectionIndex();
    if (selectionIndex != -1)
    {
      new EditCommandConfigPane(this.parent.getShell(), this.preferenceModel
          .getCommandConfigList().get(selectionIndex)).open();
    }
    this.refreshViewFromModel();
  }

  private void buttonRemovePressed()
  {
    int[] selectionIndices = this.tableCommands.getSelectionIndices();

    // remove multiple selected indices from end to start
    for (int i = selectionIndices.length - 1; i >= 0; i--)
    {
      int selectedIndex = selectionIndices[i];
      this.preferenceModel.getCommandConfigList().remove(selectedIndex);
    }
    this.refreshViewFromModel();
  }

  private void buttonUpPressed()
  {
    int[] selectionIndices = this.tableCommands.getSelectionIndices();
    boolean changed =
        moveUpInList(this.preferenceModel.getCommandConfigList(),
            selectionIndices);
    this.refreshViewFromModel();
    if (changed)
    {
      for (int i = 0; i < selectionIndices.length; i++)
      {
        selectionIndices[i] -= 1;
      }
    }
    this.tableCommands.setSelection(selectionIndices);
  }

  private void buttonDownPressed()
  {
    int[] selectionIndices = this.tableCommands.getSelectionIndices();
    boolean changed =
        moveDownInList(this.preferenceModel.getCommandConfigList(),
            selectionIndices);
    this.refreshViewFromModel();
    if (changed)
    {
      for (int i = 0; i < selectionIndices.length; i++)
      {
        selectionIndices[i] += 1;
      }
    }
    this.tableCommands.setSelection(selectionIndices);
  }

  private abstract class EventlessSelectionAdapter extends SelectionAdapter
  {
    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public final void widgetSelected(SelectionEvent event)
    {
      if (false)
      {
        event.getClass();
      }
      this.widgetSelected();
    }

    abstract void widgetSelected();
  }

  /**
   * Just for testing the page layout.
   * 
   * @param args ...
   */
  public static void main(String[] args)
  {
    Display display = Display.getDefault();
    final Shell shell = new Shell(display);
    new StartExplorerPreferencePage()
    {
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
            preferenceModel.getSeparatorData().setSeparatorData(
                SeparatorType.LF, null);
          }
        };
      }
    };
    shell.open();
    while (!shell.isDisposed())
    {
      if (!display.readAndDispatch())
      {
        display.sleep();
      }
    }
  }
}
