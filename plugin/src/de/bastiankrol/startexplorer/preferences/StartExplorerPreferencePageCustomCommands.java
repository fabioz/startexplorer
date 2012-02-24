package de.bastiankrol.startexplorer.preferences;

import static de.bastiankrol.startexplorer.util.Util.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import de.bastiankrol.startexplorer.customcommands.CommandConfig;

/**
 * Preference page for StartExplorer
 * 
 * @author Bastian Krol
 */
public class StartExplorerPreferencePageCustomCommands extends
    AbstractStartExplorerPreferencePage
{
  private static final int MAX_COLUMN_WIDTH = 300;

  private Table tableCommands;

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createContents(Composite parent)
  {
    // do basic layout for preference page
    Composite innerParent = new Composite(parent, parent.getStyle());
    this.setPanel(innerParent);
    this.setGridLayoutWithTwoColumns(this.getPanel());

    // Upper left part: command config table
    this.tableCommands = this.createTable(this.getPanel());

    // Upper right part: buttons to control the command config table (add, edit,
    // delete, up, down, ...)
    this.createControlButtonSection(this.getPanel());

    this.refreshViewFromModel();

    return this.getPanel();
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
    Label labelPageCaption = new Label(parent, SWT.NONE);
    labelPageCaption.setText("Custom Command Configuration");
    GridData gridDataLabelPageCaption = new GridData();
    gridDataLabelPageCaption.horizontalSpan = 2;
    labelPageCaption.setLayoutData(gridDataLabelPageCaption);

    Table table = new Table(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
    GridData gridDataTable = new GridData(SWT.FILL, SWT.FILL, true, true);
    table.setLayoutData(gridDataTable);
    table.setLinesVisible(true);
    table.setHeaderVisible(true);
    String[] titles = { "Command", "Name/Resources", "Name/Text Selection",
        "Resource Type" };
    for (int i = 0; i < titles.length; i++)
    {
      TableColumn column = new TableColumn(table, SWT.NONE);
      column.setText(titles[i]);
    }
    return table;
  }

  private void createControlButtonSection(Composite parent)
  {
    Composite compositeButtonColumn = new Composite(parent, SWT.NONE);
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
            StartExplorerPreferencePageCustomCommands.this.buttonAddPressed();
          }
        });
    createButton(compositeButtonColumn, "Edit").addSelectionListener(
        new EventlessSelectionAdapter()
        {
          public void widgetSelected()
          {
            StartExplorerPreferencePageCustomCommands.this.buttonEditPressed();
          }
        });
    createButton(compositeButtonColumn, "Remove").addSelectionListener(
        new EventlessSelectionAdapter()
        {
          public void widgetSelected()
          {
            StartExplorerPreferencePageCustomCommands.this
                .buttonRemovePressed();
          }
        });
    createButton(compositeButtonColumn, "Up").addSelectionListener(
        new EventlessSelectionAdapter()
        {
          public void widgetSelected()
          {
            StartExplorerPreferencePageCustomCommands.this.buttonUpPressed();
          }
        });
    createButton(compositeButtonColumn, "Down").addSelectionListener(
        new EventlessSelectionAdapter()
        {
          public void widgetSelected()
          {
            StartExplorerPreferencePageCustomCommands.this.buttonDownPressed();
          }
        });
  }

  /**
   * Refreshes the page from the preference model
   */
  @Override
  void refreshViewFromModel()
  {
    this.refreshTableCustomCommands();
  }

  private void refreshTableCustomCommands()
  {
    this.tableCommands.removeAll();
    for (CommandConfig commandConfig : this.getModel().getCommandConfigList())
    {
      TableItem item = new TableItem(this.tableCommands, SWT.NONE);
      item.setText(0, commandConfig.getCommand());
      item.setText(1, commandConfig.getNameForResourcesMenu());
      if (!commandConfig.isEnabledForResourcesMenu())
      {
        item.setForeground(1,
            Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
      }
      item.setText(2, commandConfig.getNameForTextSelectionMenu());
      if (!commandConfig.isEnabledForTextSelectionMenu())
      {
        item.setForeground(2,
            Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
      }
      item.setText(3, commandConfig.getResourceType().getLabel());
    }
    for (int i = 0; i < this.tableCommands.getColumnCount(); i++)
    {
      TableColumn column = this.tableCommands.getColumn(i);
      column.pack();
      if (column.getWidth() > MAX_COLUMN_WIDTH)
      {
        column.setWidth(MAX_COLUMN_WIDTH);
      }
    }
  }

  private Button createButton(Composite parent, String label)
  {
    Button button = new Button(parent, SWT.PUSH);
    button.setText(label);
    return button;
  }

  private void buttonAddPressed()
  {
    new EditCommandConfigPane(this.getPanel().getShell(), this.getModel()
        .getCommandConfigList()).open();
    this.refreshViewFromModel();
  }

  private void buttonEditPressed()
  {
    int selectionIndex = this.tableCommands.getSelectionIndex();
    if (selectionIndex != -1)
    {
      new EditCommandConfigPane(this.getPanel().getShell(), this.getModel()
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
      this.getModel().getCommandConfigList().remove(selectedIndex);
    }
    this.refreshViewFromModel();
  }

  private void buttonUpPressed()
  {
    int[] selectionIndices = this.tableCommands.getSelectionIndices();
    boolean changed = moveUpInList(this.getModel().getCommandConfigList(),
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
    boolean changed = moveDownInList(this.getModel().getCommandConfigList(),
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
      this.widgetSelected();
    }

    abstract void widgetSelected();
  }
}