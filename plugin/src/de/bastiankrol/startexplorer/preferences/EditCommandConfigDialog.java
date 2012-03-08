package de.bastiankrol.startexplorer.preferences;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
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
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import de.bastiankrol.startexplorer.Activator;
import de.bastiankrol.startexplorer.ResourceType;
import de.bastiankrol.startexplorer.customcommands.CommandConfig;
import de.bastiankrol.startexplorer.customcommands.SharedFileManager;
import de.bastiankrol.startexplorer.util.IMessageDialogHelper;
import de.bastiankrol.startexplorer.util.MessageDialogHelper;
import de.bastiankrol.startexplorer.util.Util;

/**
 * Dialog window to edit a custom command.
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$
 */
public class EditCommandConfigDialog extends Dialog
{
  private SharedFileManager sharedFileManager;
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
  private Button radioButtonPreferences;
  private Button radioButtonSharedFile;
  private Text textSharedFilename;
  private Button buttonBrowse;
  private Button buttonExport;

  private EditCommandConfigDialog(Shell parentShell)
  {
    super(parentShell);
    this.sharedFileManager = new SharedFileManager();
    this.messageDialogHelper = new MessageDialogHelper();
  }

  /**
   * Creates a new EditCommandConfigDialog to create and edit a <b>new</b>
   * command config. The CommandConfig will be added to
   * <code>commandConfigList</code>.
   * 
   * @param parentShell the parent shell
   * @param commandConfigList the list of CommandConfigs to add the new
   *          CommandConfig to.
   */
  public EditCommandConfigDialog(Shell parentShell,
      List<CommandConfig> commandConfigList)
  {
    this(parentShell);

    this.commandConfig = new CommandConfig();
    this.commandConfigList = commandConfigList;
  }

  /**
   * Creates a new EditCommandConfigDialog to edit an existing commandConfig.
   * 
   * @param parentShell the parent shell
   * @param commandConfig the list of CommandConfigs to initialize the dialog
   *          with
   */
  public EditCommandConfigDialog(Shell parentShell, CommandConfig commandConfig)
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

    // spacers
    new Label(dialogArea, SWT.NONE);
    new Label(dialogArea, SWT.NONE);

    this.createSaveAsSection(dialogArea);

    this.refreshViewFromModel();
    return dialogArea;
  }

  private void createSaveAsSection(Composite parent)
  {
    Composite saveAsArea = new Composite(parent, SWT.NONE);
    spanTwoColumns(saveAsArea);

    GridLayout gridLayoutSaveAsArea = new GridLayout();
    gridLayoutSaveAsArea.numColumns = 3;
    saveAsArea.setLayout(gridLayoutSaveAsArea);

    Label labelSaveAs = new Label(saveAsArea, SWT.HORIZONTAL | SWT.SHADOW_NONE);
    labelSaveAs.setText("Save As: ");
    spanThreeColumns(labelSaveAs);

    this.radioButtonPreferences = new Button(saveAsArea, SWT.RADIO);
    this.radioButtonPreferences.setText("Local (Preference Store)");
    this.addTrigger(this.radioButtonPreferences);
    spanThreeColumns(this.radioButtonPreferences);

    this.radioButtonSharedFile = new Button(saveAsArea, SWT.RADIO);
    this.radioButtonSharedFile.setText("Shared File");
    this.addTrigger(this.radioButtonSharedFile);

    this.textSharedFilename = new Text(saveAsArea, SWT.SINGLE | SWT.BORDER);
    GridData gridDataTextSharedFilename = new GridData();
    gridDataTextSharedFilename.widthHint = 330;
    gridDataTextSharedFilename.grabExcessHorizontalSpace = true;
    this.textSharedFilename.setLayoutData(gridDataTextSharedFilename);

    this.buttonBrowse = new Button(saveAsArea, SWT.PUSH);
    this.buttonBrowse.setText("Browse...");
    GridData gridDataButtonBrowse = new GridData();
    gridDataButtonBrowse.horizontalAlignment = SWT.RIGHT;
    this.buttonBrowse.setLayoutData(gridDataButtonBrowse);
    this.buttonBrowse.addSelectionListener(new EventlessSelectionAdapter()
    {
      @Override
      void widgetSelected()
      {
        EditCommandConfigDialog.this.buttonBrowsePressed();
      }
    });
  }

  private void spanTwoColumns(Control control)
  {
    GridData gridData = new GridData();
    gridData.horizontalSpan = 2;
    gridData.horizontalAlignment = SWT.LEFT;
    control.setLayoutData(gridData);
  }

  private void spanThreeColumns(Control control)
  {
    GridData gridData = new GridData();
    gridData.horizontalSpan = 3;
    gridData.horizontalAlignment = SWT.LEFT;
    control.setLayoutData(gridData);
  }

  private void addTrigger(Button radioButton)
  {
    radioButton.addSelectionListener(new EventlessSelectionAdapter()
    {
      @Override
      void widgetSelected()
      {
        EditCommandConfigDialog.this.disableEnableControls();
      }
    });
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
        EditCommandConfigDialog.this.buttonExportPressed();
      }
    });
    super.createButtonsForButtonBar(parent);
  }

  private void buttonExportPressed()
  {
    String exportFilename = FileDialogUtil.openFileDialog(this.getShell(),
        getWorkspaceRootAbsolutePath(), "Export Custom Command Definition",
        SWT.SAVE);
    if (exportFilename != null)
    {
      try
      {
        CommandConfig commandConfigForExport = new CommandConfig();
        this.flushViewToModel(commandConfigForExport);
        this.sharedFileManager.exportCommandConfigToFile(
            commandConfigForExport, new File(exportFilename));
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

  private void buttonBrowsePressed()
  {
    ContainerSelectionDialog dialog = new ContainerSelectionDialog(
        this.getShell(), ResourcesPlugin.getWorkspace().getRoot(), true,
        "Choose a folder where you want to store the custom command definition.\n");
    dialog.setTitle("Folder Selection");
    int returnCode = dialog.open();
    if (returnCode == Dialog.OK)
    {
      if (dialog.getResult().length > 0
          && dialog.getResult()[0] instanceof Path)
      {
        Path path = (Path) dialog.getResult()[0];
        this.createSharedFilenameFromTextFields(path);
      }
      else
      {
        Activator.logWarning("Unexpected result fro ContainerSelectionDialog: "
            + dialog.getResult());
      }
    }
  }

  private void createSharedFilenameFromTextFields(Path selectedPath)
  {
    String parentDirectory = selectedPath.toString() + "/";
    String potentialFilename = evaluateTextFieldsForPotentialFilenames(
        this.textNameForResources, this.textNameForTextSelection,
        this.textCommand);
    if (potentialFilename == null)
    {
      potentialFilename = "startexplorer-command";
    }
    String finalFilename = parentDirectory + potentialFilename
        + ".startexplorer";
    File finalFile = this.fileInWorkspace(finalFilename);
    if (finalFile.exists())
    {
      boolean okToOverwrite = this.messageDialogHelper.displayQuestionDialog(
          "File exists", "The selected " + finalFilename
              + " file already exists. Is it okay to overwrite it?");
      if (!okToOverwrite)
      {
        this.textSharedFilename.setText("");
        return;
      }
    }
    this.textSharedFilename.setText(finalFilename);
  }

  private String evaluateTextFieldsForPotentialFilenames(Text... sources)
  {
    String potentialFilename = null;
    for (Text source : sources)
    {
      potentialFilename = this.evaluatePotentialFilename(source);
      if (potentialFilename != null)
      {
        break;
      }
    }
    return potentialFilename;
  }

  private String evaluatePotentialFilename(Text textField)
  {
    String potentialFilename = textField.getText();
    if (potentialFilename == null)
    {
      return null;
    }
    potentialFilename = this.transformToValidFilename(potentialFilename);
    potentialFilename = potentialFilename.trim();
    if (potentialFilename.length() == 0)
    {
      return null;
    }
    return potentialFilename;
  }

  private String transformToValidFilename(String potentialFilename)
  {
    if (potentialFilename.length() > 255)
    {
      potentialFilename = potentialFilename.substring(0, 255);
    }
    return potentialFilename.replaceAll("[\\\\/:*?\"<>|]", "");
  }

  void addContentAssistAdapter()
  {
    ContentAssist.addContentAssistAdapter(this.textCommand);
  }

  private void disableEnableControls()
  {
    this.textSharedFilename.setEnabled(this.radioButtonSharedFile
        .getSelection());
    this.buttonBrowse.setEnabled(this.radioButtonSharedFile.getSelection());
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
    this.textSharedFilename.setEnabled(this.radioButtonSharedFile
        .getSelection());
    this.radioButtonPreferences.setSelection(this.commandConfig
        .isStoreInPreferences());
    this.radioButtonSharedFile.setSelection(this.commandConfig
        .isStoreAsSharedFile());
    if (this.commandConfig.getSharedFilePath() != null)
    {
      this.textSharedFilename.setText(this.commandConfig.getSharedFilePath());
    }
    this.disableEnableControls();
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

    if (this.radioButtonPreferences.getSelection())
    {
      if (commandConfig.isStoreAsSharedFile())
      {
        this.sharedFileManager.delete(commandConfig);
      }
      commandConfig.setStoreInPreferences();
    }
    if (this.radioButtonSharedFile.getSelection())
    {
      commandConfig.setStoreAsSharedFile(this.textSharedFilename.getText());
    }
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

  String getWorkspaceRootAbsolutePath()
  {
    return Util.getWorkspaceRootAbsolutePath();
  }

  File getWorkspaceRootAsFile()
  {
    return Util.getWorkspaceRootAsFile();
  }

  File fileInWorkspace(String filename)
  {
    return Util.getFileInWorkspace(filename);
  }
}
