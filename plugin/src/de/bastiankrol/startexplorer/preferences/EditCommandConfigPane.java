package de.bastiankrol.startexplorer.preferences;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.bastiankrol.startexplorer.ResourceType;
import de.bastiankrol.startexplorer.customcommands.CommandConfig;

/**
 * Dialog window to edit a custom command.
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$
 */
public class EditCommandConfigPane extends Dialog
{
  private CommandConfig commandConfig;
  private Text textCommand;
  private Button checkboxEnabledForResources;
  private Text textNameForResources;
  private Button checkboxEnabledForTextSelection;
  private Text textNameForTextSelection;
  private Button checkboxPassSelectedText;
  private Combo comboResourceType;
  private List<CommandConfig> commandConfigList;

  /**
   * Creates a new EditCommandConfigPane to create and edit a <b>new</b> command
   * config. The CommandConfig will be added to <code>commandConfigList</code>.
   * 
   * @param parentShell the parent shell
   * @param commandConfigList the list of CommandConfigs to add the new
   *          CommandConfig to.
   */
  public EditCommandConfigPane(Shell parentShell,
      List<CommandConfig> commandConfigList)
  {
    super(parentShell);
    this.commandConfig = new CommandConfig();
    this.commandConfigList = commandConfigList;
  }

  /**
   * Creates a new EditCommandConfigPane to edit an existing commandConfig.
   * 
   * @param parentShell the parent shell
   * @param commandConfig the list of CommandConfigs to initialize the dialog
   *          with
   */
  public EditCommandConfigPane(Shell parentShell, CommandConfig commandConfig)
  {
    super(parentShell);
    this.commandConfig = commandConfig;
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createDialogArea(Composite parent)
  {
    Composite dialogArea = (Composite) super.createDialogArea(parent);
    this.getShell().setText("Command Configuration");
    ((GridLayout) dialogArea.getLayout()).numColumns = 2;
    GridData gridData = new GridData(300, 13);

    Label labelCommand = new Label(dialogArea, SWT.HORIZONTAL | SWT.SHADOW_NONE);
    labelCommand.setText("Command: ");
    this.textCommand = new Text(dialogArea, SWT.SINGLE | SWT.BORDER);
    this.textCommand.setLayoutData(gridData);

    ContentAssist.addContentAssistAdapter(this.textCommand);

    Label labelEnabledForResources = new Label(dialogArea, SWT.HORIZONTAL
        | SWT.SHADOW_NONE);
    labelEnabledForResources.setText("Enabled for resources: ");
    this.checkboxEnabledForResources = new Button(dialogArea, SWT.CHECK);

    Label labelNameForResource = new Label(dialogArea, SWT.HORIZONTAL
        | SWT.SHADOW_NONE);
    labelNameForResource.setText("Name for resources menu: ");
    this.textNameForResources = new Text(dialogArea, SWT.SINGLE | SWT.BORDER);
    this.textNameForResources.setLayoutData(gridData);

    Label labelEnabledForTextSelection = new Label(dialogArea, SWT.HORIZONTAL
        | SWT.SHADOW_NONE);
    labelEnabledForTextSelection.setText("Enabled for text selections: ");
    this.checkboxEnabledForTextSelection = new Button(dialogArea, SWT.CHECK);

    Label labelNameForTextSelection = new Label(dialogArea, SWT.HORIZONTAL
        | SWT.SHADOW_NONE);
    labelNameForTextSelection.setText("Name for text selection menu: ");
    this.textNameForTextSelection = new Text(dialogArea, SWT.SINGLE
        | SWT.BORDER);
    this.textNameForTextSelection.setLayoutData(gridData);

    Label labelResourceType = new Label(dialogArea, SWT.HORIZONTAL
        | SWT.SHADOW_NONE);
    labelResourceType.setText("Resource Type: ");
    this.comboResourceType = new Combo(dialogArea, SWT.DROP_DOWN
        | SWT.READ_ONLY);
    this.comboResourceType.setItems(ResourceType.allLabels().toArray(
        new String[ResourceType.allLabels().size()]));

    Label labelPassSelectedText = new Label(dialogArea, SWT.HORIZONTAL
        | SWT.SHADOW_NONE);
    labelPassSelectedText.setText("Pass selected text to application: ");
    this.checkboxPassSelectedText = new Button(dialogArea, SWT.CHECK);

    this.refreshViewFromModel();
    return dialogArea;
  }

  private void refreshViewFromModel()
  {
    this.textCommand.setText(this.commandConfig.getCommand());
    this.checkboxEnabledForResources.setSelection(this.commandConfig
        .isEnabledForResourcesMenu());
    this.textNameForResources.setText(this.commandConfig
        .getNameForResourcesMenu());
    this.checkboxEnabledForTextSelection.setSelection(this.commandConfig
        .isEnabledForTextSelectionMenu());
    this.textNameForTextSelection.setText(this.commandConfig
        .getNameForTextSelectionMenu());
    this.comboResourceType.setText(this.commandConfig.getResourceType()
        .getLabel());
    this.checkboxPassSelectedText.setSelection(this.commandConfig
        .isPassSelectedText());
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.jface.dialogs.Dialog#okPressed()
   */
  @Override
  protected void okPressed()
  {
    this.commandConfig.setCommand(this.textCommand.getText());
    this.commandConfig
        .setEnabledForResourcesMenu(this.checkboxEnabledForResources
            .getSelection());
    this.commandConfig.setNameForResourcesMenu(this.textNameForResources
        .getText());
    this.commandConfig
        .setEnabledForTextSelectionMenu(this.checkboxEnabledForTextSelection
            .getSelection());
    ResourceType resourceType = ResourceType.fromLabel(this.comboResourceType
        .getText());
    if (resourceType != null)
    {
      this.commandConfig.setResourceType(resourceType);
    }
    this.commandConfig
        .setNameForTextSelectionMenu(this.textNameForTextSelection.getText());
    this.commandConfig.setPassSelectedText(this.checkboxPassSelectedText
        .getSelection());
    if (this.commandConfigList != null)
    {
      this.commandConfigList.add(this.commandConfig);
    }
    this.close();
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.jface.dialogs.Dialog#cancelPressed()
   */
  @Override
  protected void cancelPressed()
  {
    this.close();
  }

  /**
   * Just for testing the page layout.
   * 
   * @param args ...
   */
  public static void main(String[] args)
  {
    Display display = Display.getDefault();
    Shell shell = new Shell(display);
    shell.open();
    EditCommandConfigPane pane = new EditCommandConfigPane(shell,
        new CommandConfig("command", ResourceType.BOTH, true,
            "name for resources", true, "name for text selection", false));
    pane.open();
    while (!shell.isDisposed())
    {
      if (!display.readAndDispatch())
      {
        display.sleep();
      }
    }
  }
}
