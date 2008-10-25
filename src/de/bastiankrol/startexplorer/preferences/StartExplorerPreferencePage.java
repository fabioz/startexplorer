package de.bastiankrol.startexplorer.preferences;

import static de.bastiankrol.startexplorer.preferences.PreferenceConstantsAndDefaults.*;
import static de.bastiankrol.startexplorer.util.Util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.bastiankrol.startexplorer.Activator;

/**
 * Preference page for StartExplorer
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$ $Author:$
 */
public class StartExplorerPreferencePage extends PreferencePage implements
    IWorkbenchPreferencePage
{

  /**
   * List of command configs
   */
  private List<CommandConfig> commandConfigList;

  private Composite parent;
  private Table tableCommands;

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
   */
  public void init(IWorkbench workbench)
  {
    // do nothing
    if (false)
    {
      workbench.getClass();
    }
    this.commandConfigList = new ArrayList<CommandConfig>();
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.jface.preference.PreferencePage#doGetPreferenceStore()
   */
  @Override
  protected IPreferenceStore doGetPreferenceStore()
  {
    return PreferenceUtil.retrievePreferenceStore();
  }

  /**
   * Initializes the preference model with the defaults, if no preferences have
   * been set by the user yet.
   */
  protected void initializeDefaults()
  {
    // create a new list from Arrays.asList(DEF..), otherwise changes to the
    // list would write through to the default array, which is not what we want.
    this.commandConfigList = new ArrayList<CommandConfig>(Arrays
        .asList(DEFAULT_CUSTOM_COMMANDS));
    this.refreshViewFromModel();
  }

  /**
   * Initializes the preference model by loading the stored preferences from the
   * preference store.
   */
  private void initializeValues()
  {
    List<CommandConfig> commandConfigList = this.commandConfigList;
    PreferenceUtil.fillCommandConfigListFromPreferences(commandConfigList);
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
   * @param store
   *          the {@link IPreferenceStore} to store the preferences in.
   */
  protected void storeValues(IPreferenceStore store)
  {
    store
        .setValue(KEY_NUMBER_OF_CUSTOM_COMMANDS, this.commandConfigList.size());
    for (int i = 0; i < this.commandConfigList.size(); i++)
    {
      CommandConfig commandConfig = this.commandConfigList.get(i);
      store.setValue(getCommandKey(i), commandConfig.getCommand());
      store.setValue(getCommandEnabledForResourcesMenuKey(i), commandConfig
          .isEnabledForResourcesMenu());
      store.setValue(getCommandNameForResourcesMenuKey(i), commandConfig
          .getNameForResourcesMenu());
      store.setValue(getCommandEnabledForTextSelectionMenuKey(i), commandConfig
          .isEnabledForTextSelectionMenu());
      store.setValue(getCommandNameForTextSelectionMenuKey(i), commandConfig
          .getNameForTextSelectionMenu());
    }
  }

  /**
   * Refreshes the page from the preference model
   */
  private void refreshViewFromModel()
  {
    this.tableCommands.removeAll();
    for (CommandConfig commandConfig : this.commandConfigList)
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
    // TODO do we need to create a new composite as a child of parent and return
    // this new composite instead of manipulating parent directly?

    this.parent = parent;
    GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns = 2;
    this.parent.setLayout(gridLayout);

    this.tableCommands = this.createTable(this.parent);

    Composite buttonColumn = new Composite(this.parent, SWT.NONE);
    buttonColumn.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
    RowLayout buttonColumnLayout = new RowLayout();
    buttonColumnLayout.type = SWT.VERTICAL;
    buttonColumnLayout.pack = false;
    buttonColumn.setLayout(buttonColumnLayout);

    createButton(buttonColumn, "Add").addSelectionListener(
        new EventlessSelectionAdapter()
        {
          public void widgetSelected()
          {
            StartExplorerPreferencePage.this.buttonAddPressed();
          }
        });
    createButton(buttonColumn, "Edit").addSelectionListener(
        new EventlessSelectionAdapter()
        {
          public void widgetSelected()
          {
            StartExplorerPreferencePage.this.buttonEditPressed();
          }
        });
    createButton(buttonColumn, "Remove").addSelectionListener(
        new EventlessSelectionAdapter()
        {
          public void widgetSelected()
          {
            StartExplorerPreferencePage.this.buttonRemovePressed();
          }
        });
    createButton(buttonColumn, "Up").addSelectionListener(
        new EventlessSelectionAdapter()
        {
          public void widgetSelected()
          {
            StartExplorerPreferencePage.this.buttonUpPressed();
          }
        });
    createButton(buttonColumn, "Down").addSelectionListener(
        new EventlessSelectionAdapter()
        {
          public void widgetSelected()
          {
            StartExplorerPreferencePage.this.buttonDownPressed();
          }
        });

    this.initializeValues();

    // parent.pack(); ???

    return this.parent;
  }

  /**
   * Creates the table for the preference page.
   * 
   * @param parent
   *          parent composite, the table will be a child of <code>parent</code>
   * @return
   */
  private Table createTable(Composite parent)
  {
    String[] titles = { "Command", "Name/Resources", "Name/Text Selection" };
    Table table = new Table(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
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
    new EditCommandConfigPane(this.parent.getShell(), this.commandConfigList)
        .open();
    this.refreshViewFromModel();
  }

  private void buttonEditPressed()
  {
    int selectionIndex = this.tableCommands.getSelectionIndex();
    if (selectionIndex != -1)
    {
      new EditCommandConfigPane(this.parent.getShell(), this.commandConfigList
          .get(selectionIndex)).open();
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
      this.commandConfigList.remove(selectedIndex);
    }
    this.refreshViewFromModel();
  }

  private void buttonUpPressed()
  {
    int[] selectionIndices = this.tableCommands.getSelectionIndices();
    boolean changed = moveUpInList(this.commandConfigList, selectionIndices);
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
    boolean changed = moveDownInList(this.commandConfigList, selectionIndices);
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
   * @param args
   *          ...
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
