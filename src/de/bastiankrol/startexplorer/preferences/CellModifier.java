package de.bastiankrol.startexplorer.preferences;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TableItem;

/**
 * This class implements an ICellModifier An ICellModifier is called when the
 * user modifes a cell in the tableViewer
 */
public class CellModifier implements ICellModifier
{
  private StartExplorerPreferencePage preferencePage;
  private List<String> columnNames;

  /**
   * Constructor
   * 
   * @param preferencePage
   *          an instance of a StartExplorerPreferencePage
   */
  public CellModifier(StartExplorerPreferencePage preferencePage,
      String[] columnNames)
  {
    super();
    this.preferencePage = preferencePage;
    this.columnNames = Arrays.asList(columnNames);
  }

  /**
   * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object,
   *      java.lang.String)
   */
  public boolean canModify(Object element, String property)
  {
    return true;
  }

  /**
   * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object,
   *      java.lang.String)
   */
  public Object getValue(Object element, String property)
  {
    CommandConfig commandConfig = (CommandConfig) element;
    int columnIndex = this.columnNames.indexOf(property);
    switch (columnIndex)
    {
      case 0:
        return commandConfig.getCommand();
      case 1:
        return commandConfig.getNameForResourcesMenu();
      case 2:
        return commandConfig.getNameForTextSelectionMenu();
      default:
        throw new RuntimeException();
    }
  }

  /**
   * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object,
   *      java.lang.String, java.lang.Object)
   */
  public void modify(Object element, String property, Object value)
  {
    int columnIndex = this.columnNames.indexOf(property);
    TableItem item = (TableItem) element;
    CommandConfig commandConfig = (CommandConfig) item.getData();
    switch (columnIndex)
    {
      case 0:
        commandConfig.setCommand((String) value);
      break;
      case 1:
        commandConfig.setNameForResourcesMenu((String) value);
      break;
      case 2:
        commandConfig.setNameForTextSelectionMenu((String) value);
      break;
      default:
        throw new RuntimeException();
    }
    this.preferencePage.getCommandConfigList().notifyListenersUpdate(commandConfig);
  }
}
