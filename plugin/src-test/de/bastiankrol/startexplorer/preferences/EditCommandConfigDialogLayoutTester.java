package de.bastiankrol.startexplorer.preferences;

import java.io.File;

import org.eclipse.swt.widgets.Shell;

import de.bastiankrol.startexplorer.ResourceType;
import de.bastiankrol.startexplorer.customcommands.CommandConfig;

public class EditCommandConfigDialogLayoutTester extends
    EditCommandConfigDialog
{

  EditCommandConfigDialogLayoutTester(Shell shell, CommandConfig commandConfig)
  {
    super(shell, commandConfig);
  }

  @Override
  void addContentAssistAdapter()
  {
    // Do nothing
  }

  @Override
  String getWorkspaceRootAbsolutePath()
  {
    return "C:/tmp";
  }

  File getWorkspaceRootAsFile()
  {
    return new File(getWorkspaceRootAbsolutePath());
  }

  /**
   * Just for testing the page layout.
   * 
   * @param args ...
   */
  public static void main(String[] args)
  {
    EditCommandConfigDialogLayoutTester pane = new EditCommandConfigDialogLayoutTester(
        LayoutTesterDelegate.shell, new CommandConfig("command",
            ResourceType.BOTH, true, "name for resources", true,
            "name for text selection", false));

    LayoutTesterDelegate.openDialog(pane);
  }
}
