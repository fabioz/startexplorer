package de.bastiankrol.startexplorer.preferences;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

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
    Label labelCommand =
        new Label(dialogArea, SWT.HORIZONTAL | SWT.SHADOW_NONE);
    labelCommand.setText("Command: ");
    this.textCommand = new Text(dialogArea, SWT.SINGLE | SWT.BORDER);
    this.textCommand.setLayoutData(gridData);
    Label labelEnabledForResources =
        new Label(dialogArea, SWT.HORIZONTAL | SWT.SHADOW_NONE);
    labelEnabledForResources.setText("Enabled for resources: ");
    this.checkboxEnabledForResources = new Button(dialogArea, SWT.CHECK);
    Label labelNameForResource =
        new Label(dialogArea, SWT.HORIZONTAL | SWT.SHADOW_NONE);
    labelNameForResource.setText("Name for resources menu: ");
    this.textNameForResources = new Text(dialogArea, SWT.SINGLE | SWT.BORDER);
    this.textNameForResources.setLayoutData(gridData);
    Label labelEnabledForTextSelection =
        new Label(dialogArea, SWT.HORIZONTAL | SWT.SHADOW_NONE);
    labelEnabledForTextSelection.setText("Enabled for text selections: ");
    this.checkboxEnabledForTextSelection = new Button(dialogArea, SWT.CHECK);
    Label labelNameForTextSelection =
        new Label(dialogArea, SWT.HORIZONTAL | SWT.SHADOW_NONE);
    labelNameForTextSelection.setText("Name for text selection menu: ");
    this.textNameForTextSelection =
        new Text(dialogArea, SWT.SINGLE | SWT.BORDER);
    this.textNameForTextSelection.setLayoutData(gridData);
    Label labelPassSelectedText =
        new Label(dialogArea, SWT.HORIZONTAL | SWT.SHADOW_NONE);
    labelPassSelectedText.setText("Pass selected text to application: ");
    this.checkboxPassSelectedText = new Button(dialogArea, SWT.CHECK);
    this.refreshViewFromModel();
    return dialogArea;
  }

  // /**
  // * {@inheritDoc}
  // *
  // * @see
  // org.eclipse.jface.dialogs.Dialog#createButtonBar(org.eclipse.swt.widgets.Composite)
  // */
  // @Override
  // protected Control createButtonBar(Composite parent)
  // {
  // return super.createButtonBar(parent);
  // // Composite compositeOkCancel = new Composite(dialogArea, SWT.NONE);
  // // RowLayout rowLayoutOkCancel = new RowLayout(SWT.HORIZONTAL);
  // // compositeOkCancel.setLayout(rowLayoutOkCancel);
  // // this.buttonOk = new Button(compositeOkCancel, SWT.PUSH);
  // // this.getShell().setDefaultButton(this.buttonOk);
  // // this.buttonOk.setText("OK");
  // // this.buttonOk.setLayoutData(new RowData(70, 22));
  // // this.buttonOk
  // // .addSelectionListener(new
  // StartExplorerPreferencePage.SimplifiedSelectionListener()
  // // {
  // // public void widgetSelected(SelectionEvent event)
  // // {
  // // EditCommandConfigPane.this.buttonOkPressed();
  // // }
  // // });
  // // this.buttonCancel = new Button(compositeOkCancel, SWT.PUSH);
  // // this.buttonCancel.setText("Cancel");
  // // this.buttonCancel.setLayoutData(new RowData(70, 22));
  // // this.buttonCancel
  // // .addSelectionListener(new
  // StartExplorerPreferencePage.SimplifiedSelectionListener()
  // // {
  // // public void widgetSelected(SelectionEvent event)
  // // {
  // // EditCommandConfigPane.this.buttonCancelPressed();
  // // }
  // // });
  // //
  // // compositeOkCancel.pack();
  // // GridData gridDataOkCancel = new GridData(GridData.HORIZONTAL_ALIGN_END);
  // // gridDataOkCancel.horizontalSpan = 2;
  // // compositeOkCancel.setLayoutData(gridDataOkCancel);
  // // this.getShell().pack();
  // }

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
    this.checkboxPassSelectedText.setSelection(this.commandConfig
        .isPassSelectedText());
    if (this.commandConfigList != null)
    {
      this.commandConfigList.add(this.commandConfig);
    }
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
    this.commandConfig
        .setNameForTextSelectionMenu(this.textNameForTextSelection.getText());
    this.commandConfig.setPassSelectedText(this.checkboxPassSelectedText
        .getSelection());
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
    EditCommandConfigPane pane =
        new EditCommandConfigPane(shell, new CommandConfig("command", true,
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
