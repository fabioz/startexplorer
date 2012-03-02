package de.bastiankrol.startexplorer.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

class ImportExportUtil
{
  private static final String[] FILTER_NAMES = {
      "StartExplorer Custom Command Definition (*.startexplorer)",
      "All Files (*.*)" };
  private static final String[] FILTER_EXTS = { "*.startexplorer", "*.*" };

  static String openFileDialog(Shell parentShell, String initialDirectory,
      String title, int dialogMode)
  {
    FileDialog fileDialog = new FileDialog(parentShell, dialogMode);
    fileDialog.setText(title);
    fileDialog.setFilterPath(initialDirectory);
    fileDialog.setFilterNames(FILTER_NAMES);
    fileDialog.setFilterExtensions(FILTER_EXTS);
    fileDialog.setOverwrite(dialogMode == SWT.SAVE);
    String exportFilename = fileDialog.open();
    return exportFilename;
  }
}
