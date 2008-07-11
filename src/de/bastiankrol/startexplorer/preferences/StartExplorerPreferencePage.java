package de.bastiankrol.startexplorer.preferences;

import static de.bastiankrol.startexplorer.preferences.PreferenceConstantsAndDefaults.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
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

import static de.bastiankrol.startexplorer.util.Util.*;

import de.bastiankrol.startexplorer.Activator;

public class StartExplorerPreferencePage extends PreferencePage implements
    IWorkbenchPreferencePage // ,SelectionListener, ModifyListener
{

  private Composite parent;
  protected List<CommandConfig> commandConfigList;
  private Table tableCommands;

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
    if (false)
    {
      workbench.getClass();
    }
    this.commandConfigList = new ArrayList<CommandConfig>();
  }

  protected IPreferenceStore doGetPreferenceStore()
  {
    return Activator.getDefault().getPreferenceStore();
  }

  protected void initializeDefaults()
  {
    // create a new list from Arrays.asList(DEF..), otherwise changes to the
    // list would write through to the default array, which is not what we want.
    this.commandConfigList = new ArrayList<CommandConfig>(Arrays
        .asList(DEFAULT_CUSTOM_COMMANDS));
    this.refreshViewFromModel();
  }

  private void initializeValues()
  {
    IPreferenceStore store = getPreferenceStore();
    int numberOfCustomCommands = store.getInt(KEY_NUMBER_OF_CUSTOM_COMMANDS);
    for (int i = 0; i < numberOfCustomCommands; i++)
    {
      String command = store.getString(getCommandKey(i));
      boolean enabledForResourcesMenu = store
          .getBoolean(getCommandEnabledForResourcesMenuKey(i));
      String nameForResourcesMenu = store
          .getString(getCommandNameForResourcesMenuKey(i));
      boolean enabledForTextSelectionMenu = store
          .getBoolean(getCommandEnabledForTextSelectionMenuKey(i));
      String nameForTextSelectionMenu = store
          .getString(getCommandNameForTextSelectionMenuKey(i));
      CommandConfig commandConfig = new CommandConfig(command,
          enabledForResourcesMenu, nameForResourcesMenu,
          enabledForTextSelectionMenu, nameForTextSelectionMenu);
      this.commandConfigList.add(commandConfig);
    }
    this.refreshViewFromModel();
  }

  private void storeValues()
  {
    IPreferenceStore store = getPreferenceStore();
    this.storeValues(store);
  }

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
        item.setForeground(1, Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
      }
      item.setText(2, commandConfig.getNameForTextSelectionMenu());
      if (!commandConfig.isEnabledForTextSelectionMenu())
      {
        item.setForeground(2, Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
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
        new SimplifiedSelectionListener()
        {
          public void widgetSelected(SelectionEvent event)
          {
            StartExplorerPreferencePage.this.buttonAddPressed();
          }
        });
    createButton(buttonColumn, "Edit").addSelectionListener(
        new SimplifiedSelectionListener()
        {
          public void widgetSelected(SelectionEvent event)
          {
            StartExplorerPreferencePage.this.buttonEditPressed();
          }
        });
    createButton(buttonColumn, "Remove").addSelectionListener(
        new SimplifiedSelectionListener()
        {
          public void widgetSelected(SelectionEvent event)
          {
            StartExplorerPreferencePage.this.buttonRemovePressed();
          }
        });
    createButton(buttonColumn, "Up").addSelectionListener(
        new SimplifiedSelectionListener()
        {
          public void widgetSelected(SelectionEvent event)
          {
            StartExplorerPreferencePage.this.buttonUpPressed();
          }
        });
    createButton(buttonColumn, "Down").addSelectionListener(
        new SimplifiedSelectionListener()
        {
          public void widgetSelected(SelectionEvent event)
          {
            StartExplorerPreferencePage.this.buttonDownPressed();
          }
        });

    this.initializeValues();

    // parent.pack(); ???

    return this.parent;
  }

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

  abstract static class SimplifiedSelectionListener implements
      SelectionListener
  {
    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetDefaultSelected(SelectionEvent event)
    {
      this.widgetSelected(event);
    }
  };

  /**
   * Just for testing the page layout.
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
