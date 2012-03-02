package de.bastiankrol.startexplorer.preferences;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.bastiankrol.startexplorer.ResourceType;
import de.bastiankrol.startexplorer.customcommands.CommandConfig;
import de.bastiankrol.startexplorer.customcommands.JsonConverter;
import de.bastiankrol.startexplorer.util.IMessageDialogHelper;
import de.bastiankrol.startexplorer.util.MessageDialogHelper;
import de.bastiankrol.startexplorer.util.Util;

/**
 * Dialog window to edit a custom command.
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$
 */
public class EditCommandConfigPane extends Dialog
{
  private JsonConverter jsonConverter;
  private IMessageDialogHelper messageDialogHelper;

  private CommandConfig commandConfig;
  private Text textCommand;
  private Button checkboxEnabledForResources;
  private Text textNameForResources;
  private Button checkboxEnabledForTextSelection;
  private Text textNameForTextSelection;
  private Button checkboxPassSelectedText;
  private Combo comboResourceType;
  private List<CommandConfig> commandConfigList;

  private Button buttonExport;

  private EditCommandConfigPane(Shell parentShell)
  {
    super(parentShell);
    this.jsonConverter = new JsonConverter();
    this.messageDialogHelper = new MessageDialogHelper();
  }

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
    this(parentShell);

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
    this(parentShell);
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

    addContentAssistAdapter();

    Label labelEnabledForResources = new Label(dialogArea, SWT.HORIZONTAL
        | SWT.SHADOW_NONE);
    labelEnabledForResources.setText("Enabled for Resources: ");
    this.checkboxEnabledForResources = new Button(dialogArea, SWT.CHECK);

    Label labelNameForResource = new Label(dialogArea, SWT.HORIZONTAL
        | SWT.SHADOW_NONE);
    labelNameForResource.setText("Name for Resources Menu: ");
    this.textNameForResources = new Text(dialogArea, SWT.SINGLE | SWT.BORDER);
    this.textNameForResources.setLayoutData(gridData);

    Label labelEnabledForTextSelection = new Label(dialogArea, SWT.HORIZONTAL
        | SWT.SHADOW_NONE);
    labelEnabledForTextSelection.setText("Enabled for Text Selections: ");
    this.checkboxEnabledForTextSelection = new Button(dialogArea, SWT.CHECK);

    Label labelNameForTextSelection = new Label(dialogArea, SWT.HORIZONTAL
        | SWT.SHADOW_NONE);
    labelNameForTextSelection.setText("Name for Text Selection Menu: ");
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
    labelPassSelectedText.setText("Pass Selected Text to Application: ");
    this.checkboxPassSelectedText = new Button(dialogArea, SWT.CHECK);

    this.refreshViewFromModel();
    return dialogArea;
  }

  @Override
  protected void createButtonsForButtonBar(Composite parent)
  {
    this.buttonExport = super.createButton(parent, IDialogConstants.CLIENT_ID,
        "Export", false);
    this.buttonExport.addSelectionListener(new EventlessSelectionAdapter()
    {
      public void widgetSelected()
      {
        EditCommandConfigPane.this.buttonExportPressed();
      }
    });

    super.createButtonsForButtonBar(parent);
  }

  private void buttonExportPressed()
  {
    String exportFilename = ImportExportUtil.openFileDialog(this.getShell(),
        this.getInitialDirectoryForImportExport(),
        "Export Custom Command Definition", SWT.SAVE);
    if (exportFilename != null)
    {
      try
      {
        CommandConfig commandConfigForExport = new CommandConfig();
        this.flushViewToModel(commandConfigForExport);
        this.jsonConverter.exportCommandConfigToFile(commandConfigForExport,
            new File(exportFilename));
      }
      catch (IOException e)
      {
        this.messageDialogHelper.displayErrorMessage(
            "Command could not be exported",
            "The command could not be exported due to an IO problem. Message: "
                + e.getMessage());

      }
    }
  }

  String getInitialDirectoryForImportExport()
  {
    return Util.getWorkspaceRoot().getAbsolutePath();
  }

  void addContentAssistAdapter()
  {
    ContentAssist.addContentAssistAdapter(this.textCommand);
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
    this.flushViewToModel();
    // When adding a command, this.commandConfigList is available
    if (this.commandConfigList != null)
    {
      this.commandConfigList.add(this.commandConfig);
    }
    this.close();
  }

  private void flushViewToModel()
  {
    this.flushViewToModel(this.commandConfig);
  }

  private void flushViewToModel(CommandConfig commandConfig)
  {
    commandConfig.setCommand(this.textCommand.getText());
    commandConfig.setEnabledForResourcesMenu(this.checkboxEnabledForResources
        .getSelection());
    commandConfig.setNameForResourcesMenu(this.textNameForResources.getText());
    commandConfig
        .setEnabledForTextSelectionMenu(this.checkboxEnabledForTextSelection
            .getSelection());
    ResourceType resourceType = ResourceType.fromLabel(this.comboResourceType
        .getText());
    if (resourceType != null)
    {
      commandConfig.setResourceType(resourceType);
    }
    commandConfig.setNameForTextSelectionMenu(this.textNameForTextSelection
        .getText());
    commandConfig.setPassSelectedText(this.checkboxPassSelectedText
        .getSelection());
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
}
