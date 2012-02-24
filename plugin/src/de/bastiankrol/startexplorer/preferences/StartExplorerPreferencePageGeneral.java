package de.bastiankrol.startexplorer.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.bastiankrol.startexplorer.preferences.SeparatorData.SeparatorType;

/**
 * Preference page for StartExplorer
 * 
 * @author Bastian Krol
 */
public class StartExplorerPreferencePageGeneral extends
    AbstractStartExplorerPreferencePage
{
  private Text textCustomCopyResourceSeparatorString;

  private Button radioButtonUnixLinebreak;
  private Button radioButtonWindowsLinebreak;
  private Button radioButtonMacLinebreak;
  private Button radioButtonTab;
  private Button radioButtonCustomSeparator;
  private Button checkboxSelectFileInExplorer;

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createContents(Composite parent)
  {
    Composite innerParent = new Composite(parent, parent.getStyle());
    this.setPanel(innerParent);
    RowLayout rowLayoutParent = new RowLayout(SWT.VERTICAL);
    this.getPanel().setLayout(rowLayoutParent);

    Label labelPageCaption = new Label(this.getPanel(), SWT.NONE);
    labelPageCaption.setText("General Options");

    // general options
    this.createGeneralOptions(this.getPanel());

    // section for configurable separator for the copy resource path
    // command
    this.createCopyResourcePathSeparatorSection(this.getPanel());

    this.refreshViewFromModel();

    return this.getPanel();
  }

  private void createGeneralOptions(Composite parent)
  {
    Composite compositeGeneralOptions = this
        .createCompositeWithGridLayoutWithTwoColumns(parent);

    this.checkboxSelectFileInExplorer = new Button(compositeGeneralOptions,
        SWT.CHECK);
    this.checkboxSelectFileInExplorer.setText("Select File In File Manager");
    this.checkboxSelectFileInExplorer
        .addSelectionListener(new SelectionAdapter()
        {
          @Override
          public void widgetSelected(SelectionEvent e)
          {
            getModel()
                .setSelectFileInExplorer(
                    StartExplorerPreferencePageGeneral.this.checkboxSelectFileInExplorer
                        .getSelection());
            refreshSeparatorStuffFromModel();
          }
        });
  }

  private void createCopyResourcePathSeparatorSection(Composite parent)
  {
    Composite compositeCopyResourcePathSeparator = this
        .createCompositeWithGridLayoutWithTwoColumns(parent);
    Label labelHeadline = new Label(compositeCopyResourcePathSeparator,
        SWT.NONE);
    labelHeadline.setText("Separator for \"Copy resource path to clipboard\"");
    GridData gridDataLabelHeadline = new GridData(SWT.FILL, SWT.FILL, true,
        false);
    gridDataLabelHeadline.horizontalSpan = 2;
    labelHeadline.setLayoutData(gridDataLabelHeadline);

    this.radioButtonUnixLinebreak = new Button(
        compositeCopyResourcePathSeparator, SWT.RADIO);
    this.radioButtonUnixLinebreak.setText("Unix Linebreak (LF)");
    this.radioButtonUnixLinebreak.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        getModel().getSeparatorData().setSeparatorType(SeparatorType.LF);
        refreshSeparatorStuffFromModel();
      }
    });
    this.radioButtonWindowsLinebreak = new Button(
        compositeCopyResourcePathSeparator, SWT.RADIO);
    this.radioButtonWindowsLinebreak.setText("Windows Linebreak (CR+LF)");
    this.radioButtonWindowsLinebreak
        .addSelectionListener(new SelectionAdapter()
        {
          @Override
          public void widgetSelected(SelectionEvent e)
          {
            getModel().getSeparatorData().setSeparatorType(SeparatorType.CRLF);
            refreshSeparatorStuffFromModel();
          }
        });
    this.radioButtonMacLinebreak = new Button(
        compositeCopyResourcePathSeparator, SWT.RADIO);
    this.radioButtonMacLinebreak.setText("Mac Linebreak (CR)");
    this.radioButtonMacLinebreak.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        getModel().getSeparatorData().setSeparatorType(SeparatorType.CR);
        refreshSeparatorStuffFromModel();
      }
    });
    this.radioButtonTab = new Button(compositeCopyResourcePathSeparator,
        SWT.RADIO);
    this.radioButtonTab.setText("Tab");
    this.radioButtonTab.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        getModel().getSeparatorData().setSeparatorType(SeparatorType.TAB);
        refreshSeparatorStuffFromModel();
      }
    });

    this.radioButtonCustomSeparator = new Button(
        compositeCopyResourcePathSeparator, SWT.RADIO);
    this.radioButtonCustomSeparator.setText("Custom String: ");
    this.radioButtonCustomSeparator.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        getModel().getSeparatorData().setSeparatorType(SeparatorType.CUSTOM);
        refreshSeparatorStuffFromModel();
      }
    });

    this.textCustomCopyResourceSeparatorString = new Text(
        compositeCopyResourcePathSeparator, SWT.SINGLE | SWT.BORDER);
    GridData layoutDataText = new GridData();
    layoutDataText.widthHint = 50;
    this.textCustomCopyResourceSeparatorString.setLayoutData(layoutDataText);
    this.textCustomCopyResourceSeparatorString
        .addModifyListener(new ModifyListener()
        {
          public void modifyText(ModifyEvent event)
          {
            String content = textCustomCopyResourceSeparatorString.getText();
            getModel().getSeparatorData().setCustomSeparatorString(content);
          }
        });
  }

  /**
   * Refreshes the page from the preference model
   */
  @Override
  void refreshViewFromModel()
  {
    this.refreshSeparatorStuffFromModel();
    this.refreshOtherOptionsFromModel();
  }

  private void refreshSeparatorStuffFromModel()
  {
    this.radioButtonUnixLinebreak.setSelection(false);
    this.radioButtonWindowsLinebreak.setSelection(false);
    this.radioButtonMacLinebreak.setSelection(false);
    this.radioButtonTab.setSelection(false);
    this.radioButtonCustomSeparator.setSelection(false);

    SeparatorType copyResourceSeparator = this.getModel().getSeparatorData()
        .getSeparatorType();
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
    String customCopyResourceSeparatorString = this.getModel()
        .getSeparatorData().getCustomSeparatorString();
    // Won't happen when running in Eclipse, just for testing
    if (customCopyResourceSeparatorString == null)
    {
      customCopyResourceSeparatorString = "";
    }
    this.textCustomCopyResourceSeparatorString
        .setText(customCopyResourceSeparatorString);
  }

  private void refreshOtherOptionsFromModel()
  {
    this.checkboxSelectFileInExplorer.setSelection(this.getModel()
        .isSelectFileInExplorer());
  }
}
