package de.bastiankrol.startexplorer.preferences;

import org.eclipse.swt.widgets.Shell;

import de.bastiankrol.startexplorer.ResourceType;
import de.bastiankrol.startexplorer.customcommands.CommandConfig;

public class EditCommandConfigPaneLayoutTester extends EditCommandConfigPane
{

  EditCommandConfigPaneLayoutTester(Shell shell, CommandConfig commandConfig)
  {
    super(shell, commandConfig);
  }

  void addContentAssistAdapter()
  {
    // Do nothing
  }

  /**
   * Just for testing the page layout.
   * 
   * @param args ...
   */
  public static void main(String[] args)
  {
    EditCommandConfigPaneLayoutTester pane = new EditCommandConfigPaneLayoutTester(
        LayoutTesterDelegate.shell, new CommandConfig("command",
            ResourceType.BOTH, true, "name for resources", true,
            "name for text selection", false));

    LayoutTesterDelegate.openDialog(pane);
  }
}
