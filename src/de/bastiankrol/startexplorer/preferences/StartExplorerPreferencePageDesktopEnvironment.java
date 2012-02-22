package de.bastiankrol.startexplorer.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.bastiankrol.startexplorer.Activator;
import de.bastiankrol.startexplorer.crossplatform.DesktopEnvironment;
import de.bastiankrol.startexplorer.crossplatform.DesktopEnvironmentAutoDetecter;
import de.bastiankrol.startexplorer.crossplatform.WorkingDirectoryMode;

/**
 * Preference page for StartExplorer
 * 
 * @author Bastian Krol
 */
public class StartExplorerPreferencePageDesktopEnvironment extends
    AbstractStartExplorerPreferencePage
{
  private Button radioAutoDetect;
  private Button radioSelectManually;
  private Button radioUseCustom;

  private Combo comboDesktopEnvironment;

  private Text textCommandForStartFileManager;
  private Text textCommandForStartFileManagerAndSelectFile;
  private Combo comboWorkingDirectoryModeForStartFileManager;
  private Text textCommandForStartShell;
  private Combo comboWorkingDirectoryModeForStartShell;
  private Text textCommandForStartSystemApplication;
  private Combo comboWorkingDirectoryModeForStartSystemApplication;
  private Combo comboWorkingDirectoryModeForCustomCommands;
  private Button checkboxFileSelectionSupported;
  private Button checkboxFilePartsWantWrapping;

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createContents(Composite parent)
  {
    createPanel(parent);
    RowLayout rowLayoutParent = new RowLayout(SWT.VERTICAL);
    this.getPanel().setLayout(rowLayoutParent);

    Label labelPageCaption = new Label(this.getPanel(), SWT.NONE);
    labelPageCaption.setText("Operating System/Desktop Environment");

    createRadioButtonAutoDetection(this.getPanel());
    createSpacer(this.getPanel());
    createSectionSelectManually(this.getPanel());
    createSpacer(this.getPanel());
    createSectionCustomDesktopEnvironment(this.getPanel());

    this.refreshViewFromModel();

    return this.getPanel();
  }

  private void createRadioButtonAutoDetection(Composite parent)
  {
    this.radioAutoDetect = new Button(parent, SWT.RADIO);
    this.radioAutoDetect.setText("Auto Detect Desktop Environment");
    this.radioAutoDetect.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        getModel().setAutoDetectDesktopEnvironment(true);
        getModel().setUseCustomeDesktopEnvironment(false);
        refreshViewFromModel();
      }
    });
  }

  private void createSectionSelectManually(Composite parent)
  {
    this.radioSelectManually = new Button(parent, SWT.RADIO);
    this.radioSelectManually.setText("Select Desktop Environment Manually");
    this.radioSelectManually.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        getModel().setAutoDetectDesktopEnvironment(false);
        getModel().setUseCustomeDesktopEnvironment(false);
        refreshViewFromModel();
      }
    });

    this.comboDesktopEnvironment = new Combo(parent, SWT.DROP_DOWN
        | SWT.READ_ONLY);
    this.comboDesktopEnvironment.setItems(DesktopEnvironment.allLabels()
        .toArray(new String[DesktopEnvironment.allLabels().size()]));
    this.comboDesktopEnvironment.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        getModel().setSelectedDesktopEnvironment(
            DesktopEnvironment.fromLabel(comboDesktopEnvironment.getText()));
      }
    });
  }

  private void createSectionCustomDesktopEnvironment(Composite parent)
  {
    this.radioUseCustom = new Button(parent, SWT.RADIO);
    this.radioUseCustom.setText("Custom Desktop Environment");
    this.radioUseCustom.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        getModel().setAutoDetectDesktopEnvironment(false);
        getModel().setUseCustomeDesktopEnvironment(true);
        refreshViewFromModel();
      }
    });
    this.createGroupCustomFields(parent);
  }

  private void createGroupCustomFields(Composite parent)
  {
    Group groupCustomDesktopEnvironment = new Group(parent,
        SWT.SHADOW_ETCHED_IN);
    groupCustomDesktopEnvironment
        .setText("Custom Desktop Environment Configuration");
    super.setGridLayoutWithTwoColumns(groupCustomDesktopEnvironment);
    this.textCommandForStartFileManager = createLabelTextPair(
        groupCustomDesktopEnvironment, "Start File Manager");
    this.textCommandForStartFileManager.addModifyListener(new ModifyListener()
    {
      public void modifyText(ModifyEvent event)
      {
        String content = textCommandForStartFileManager.getText();
        getModel().getCustomDesktopEnvironmentContainer()
            .setCommandForStartFileManager(content);
      }
    });
    this.textCommandForStartFileManagerAndSelectFile = createLabelTextPair(
        groupCustomDesktopEnvironment, "Select File in File Manager");
    this.textCommandForStartFileManagerAndSelectFile
        .addModifyListener(new ModifyListener()
        {
          public void modifyText(ModifyEvent event)
          {
            String content = textCommandForStartFileManagerAndSelectFile
                .getText();
            getModel().getCustomDesktopEnvironmentContainer()
                .setCommandForStartFileManagerAndSelectFile(content);
          }
        });

    this.comboWorkingDirectoryModeForStartFileManager = this
        .createWorkingDirectoryComboAndLabel(groupCustomDesktopEnvironment,
            "Working directory");
    this.comboWorkingDirectoryModeForStartFileManager
        .addSelectionListener(new SelectionAdapter()
        {
          @Override
          public void widgetSelected(SelectionEvent e)
          {
            getModel().getCustomDesktopEnvironmentContainer()
                .setWorkingDirectoryModeForStartFileManager(
                    WorkingDirectoryMode
                        .fromLabel(comboWorkingDirectoryModeForStartFileManager
                            .getText()));
          }
        });

    this.textCommandForStartShell = createLabelTextPair(
        groupCustomDesktopEnvironment, "Start Shell");
    this.textCommandForStartShell.addModifyListener(new ModifyListener()
    {
      public void modifyText(ModifyEvent event)
      {
        String content = textCommandForStartShell.getText();
        getModel().getCustomDesktopEnvironmentContainer()
            .setCommandForStartShell(content);
      }
    });

    this.comboWorkingDirectoryModeForStartShell = this
        .createWorkingDirectoryComboAndLabel(groupCustomDesktopEnvironment,
            "Working directory");
    this.comboWorkingDirectoryModeForStartShell
        .addSelectionListener(new SelectionAdapter()
        {
          @Override
          public void widgetSelected(SelectionEvent e)
          {
            getModel().getCustomDesktopEnvironmentContainer()
                .setWorkingDirectoryModeForStartShell(
                    WorkingDirectoryMode
                        .fromLabel(comboWorkingDirectoryModeForStartShell
                            .getText()));
          }
        });

    this.textCommandForStartSystemApplication = createLabelTextPair(
        groupCustomDesktopEnvironment, "Start Default Application");
    this.textCommandForStartSystemApplication
        .addModifyListener(new ModifyListener()
        {
          public void modifyText(ModifyEvent event)
          {
            String content = textCommandForStartSystemApplication.getText();
            getModel().getCustomDesktopEnvironmentContainer()
                .setCommandForStartSystemApplication(content);
          }
        });

    this.comboWorkingDirectoryModeForStartSystemApplication = this
        .createWorkingDirectoryComboAndLabel(groupCustomDesktopEnvironment,
            "Working directory");
    this.comboWorkingDirectoryModeForStartSystemApplication
        .addSelectionListener(new SelectionAdapter()
        {
          @Override
          public void widgetSelected(SelectionEvent e)
          {
            getModel()
                .getCustomDesktopEnvironmentContainer()
                .setWorkingDirectoryModeForStartSystemApplication(
                    WorkingDirectoryMode
                        .fromLabel(comboWorkingDirectoryModeForStartSystemApplication
                            .getText()));
          }
        });

    this.comboWorkingDirectoryModeForCustomCommands = this
        .createWorkingDirectoryComboAndLabel(groupCustomDesktopEnvironment,
            "Working directory");
    this.comboWorkingDirectoryModeForCustomCommands
        .addSelectionListener(new SelectionAdapter()
        {
          @Override
          public void widgetSelected(SelectionEvent e)
          {
            getModel().getCustomDesktopEnvironmentContainer()
                .setWorkingDirectoryModeForCustomCommands(
                    WorkingDirectoryMode
                        .fromLabel(comboWorkingDirectoryModeForCustomCommands
                            .getText()));
          }
        });

    this.checkboxFileSelectionSupported = new Button(
        groupCustomDesktopEnvironment, SWT.CHECK);
    this.spanTwoColumns(this.checkboxFileSelectionSupported);
    this.checkboxFileSelectionSupported
        .setText("File Manager Supports Single File Selection");
    this.checkboxFileSelectionSupported
        .addSelectionListener(new SelectionAdapter()
        {
          @Override
          public void widgetSelected(SelectionEvent e)
          {
            getModel().getCustomDesktopEnvironmentContainer()
                .setFileSelectionSupportedByFileManager(
                    checkboxFileSelectionSupported.getSelection());
            refreshViewFromModel();
          }
        });

    this.checkboxFilePartsWantWrapping = new Button(
        groupCustomDesktopEnvironment, SWT.CHECK);
    this.spanTwoColumns(this.checkboxFilePartsWantWrapping);
    this.checkboxFilePartsWantWrapping
        .setText("Wrap File Name Parts in Quotes");
    this.checkboxFilePartsWantWrapping
        .addSelectionListener(new SelectionAdapter()
        {
          @Override
          public void widgetSelected(SelectionEvent e)
          {
            getModel().getCustomDesktopEnvironmentContainer()
                .setFilePartsWantWrapping(
                    checkboxFilePartsWantWrapping.getSelection());
            refreshViewFromModel();
          }
        });
  }

  private void createSpacer(Composite parent)
  {
    new Label(parent, SWT.NONE);
  }

  private Text createLabelTextPair(Composite parent, String labelText)
  {
    Label label = new Label(parent, SWT.NONE);
    label.setText(labelText);
    final Text text = new Text(parent, SWT.SINGLE | SWT.BORDER);
    this.make300PxWide(text);
    return text;
  }

  private Combo createWorkingDirectoryComboAndLabel(Composite parent,
      String labelText)
  {
    Label label = new Label(parent, SWT.NONE);
    label.setText(labelText);
    Combo combo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
    combo.setItems(WorkingDirectoryMode.allLabels().toArray(
        new String[WorkingDirectoryMode.allLabels().size()]));
    return combo;
  }

  private void make300PxWide(Control control)
  {
    GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
    gridData.widthHint = 300;
    control.setLayoutData(gridData);
  }

  private void spanTwoColumns(Control control)
  {
    GridData gridData = new GridData();
    gridData.horizontalSpan = 2;
    control.setLayoutData(gridData);
  }

  /**
   * Refreshes the page from the preference model
   */
  @Override
  void refreshViewFromModel()
  {
    this.radioAutoDetect.setSelection(false);
    this.radioSelectManually.setSelection(false);
    this.radioUseCustom.setSelection(false);

    validateMode();
    if (this.getModel().isAutoDetectDesktopEnvironment())
    {
      this.radioAutoDetect.setSelection(true);
      DesktopEnvironment desktopEnvironment = DesktopEnvironmentAutoDetecter
          .findDesktopEnvironment();
      getModel().setSelectedDesktopEnvironment(desktopEnvironment);

    }
    else if (this.getModel().isUseCustomeDesktopEnvironment())
    {
      this.radioUseCustom.setSelection(true);
    }
    else
    {
      this.radioSelectManually.setSelection(true);
    }

    this.comboDesktopEnvironment.setText(this.getModel()
        .getSelectedDesktopEnvironment().getCombinedLabel());

    this.textCommandForStartFileManager
        .setText(this.getModel().getCustomDesktopEnvironmentContainer()
            .getCommandForStartFileManager());
    this.textCommandForStartFileManagerAndSelectFile.setText(this.getModel()
        .getCustomDesktopEnvironmentContainer()
        .getCommandForStartFileManagerAndSelectFile());
    this.comboWorkingDirectoryModeForStartFileManager.setText(this.getModel()
        .getCustomDesktopEnvironmentContainer()
        .getWorkingDirectoryModeForStartFileManager().getLabel());
    this.textCommandForStartShell.setText(this.getModel()
        .getCustomDesktopEnvironmentContainer().getCommandForStartShell());
    this.comboWorkingDirectoryModeForStartShell.setText(this.getModel()
        .getCustomDesktopEnvironmentContainer()
        .getWorkingDirectoryModeForStartShell().getLabel());
    this.textCommandForStartSystemApplication.setText(this.getModel()
        .getCustomDesktopEnvironmentContainer()
        .getCommandForStartSystemApplication());
    this.comboWorkingDirectoryModeForStartSystemApplication.setText(this
        .getModel().getCustomDesktopEnvironmentContainer()
        .getWorkingDirectoryModeForStartSystemApplication().getLabel());
    this.comboWorkingDirectoryModeForCustomCommands.setText(this.getModel()
        .getCustomDesktopEnvironmentContainer()
        .getWorkingDirectoryModeForCustomCommands().getLabel());
    this.checkboxFileSelectionSupported.setSelection(this.getModel()
        .getCustomDesktopEnvironmentContainer()
        .isFileSelectionSupportedByFileManager());
    this.checkboxFilePartsWantWrapping.setSelection(this.getModel()
        .getCustomDesktopEnvironmentContainer().doFilePartsWantWrapping());

    this.disableAndEnableControls();
  }

  private void disableAndEnableControls()
  {
    validateMode();
    if (this.getModel().isAutoDetectDesktopEnvironment())
    {
      this.comboDesktopEnvironment.setEnabled(false);
      this.setCustomFieldsEnabled(false);
    }
    else if (this.getModel().isUseCustomeDesktopEnvironment())
    {
      this.comboDesktopEnvironment.setEnabled(false);
      this.setCustomFieldsEnabled(true);
    }
    else
    {
      // select desktop environment manually
      this.comboDesktopEnvironment.setEnabled(true);
      this.setCustomFieldsEnabled(false);
    }
  }

  private void setCustomFieldsEnabled(boolean enabled)
  {
    this.textCommandForStartFileManager.setEnabled(enabled);
    this.textCommandForStartFileManagerAndSelectFile.setEnabled(enabled);
    this.comboWorkingDirectoryModeForStartFileManager.setEnabled(enabled);
    this.textCommandForStartShell.setEnabled(enabled);
    this.comboWorkingDirectoryModeForStartShell.setEnabled(enabled);
    this.textCommandForStartSystemApplication.setEnabled(enabled);
    this.comboWorkingDirectoryModeForStartSystemApplication.setEnabled(enabled);
    this.comboWorkingDirectoryModeForCustomCommands.setEnabled(enabled);
    this.checkboxFileSelectionSupported.setEnabled(enabled);
    this.checkboxFilePartsWantWrapping.setEnabled(enabled);

  }

  private void validateMode()
  {
    if (this.getModel().isAutoDetectDesktopEnvironment()
        && this.getModel().isUseCustomeDesktopEnvironment())
    {
      throw new IllegalStateException(
          "Preference model says \"auto detect desktop environment\" and \"use custom desktop environment\" at the same time. This is illegal.");
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.jface.preference.PreferencePage#performOk()
   */
  @Override
  public boolean performOk()
  {
    boolean returnValue = super.performOk();
    Activator.getContext().resetRuntimeExecCalls();
    return returnValue;
  }
}
