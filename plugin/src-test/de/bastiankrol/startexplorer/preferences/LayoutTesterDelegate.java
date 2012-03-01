package de.bastiankrol.startexplorer.preferences;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.bastiankrol.startexplorer.Activator;
import de.bastiankrol.startexplorer.ActivatorForLayoutTests;
import de.bastiankrol.startexplorer.ActivatorInstanceInjector;

public class LayoutTesterDelegate
{
  static final Display display;
  static final Shell shell;

  static
  {
    replaceActivator(new ActivatorForLayoutTests());
    display = Display.getDefault();
    shell = new Shell(display);
  }

  static void replaceActivator(Activator activator)
  {
    ActivatorInstanceInjector.injectDefaultInstanceForTest(activator);
  }

  static void openPage(AbstractStartExplorerPreferencePage page)
  {
    page.getPanel().pack();
    shell.pack();
    shell.open();
    run();
  }

  static void openDialog(Dialog dialog)
  {
    dialog.open();
    run();
  }

  private static void run()
  {
    while (!shell.isDisposed())
    {
      if (!display.readAndDispatch())
      {
        display.sleep();
      }
    }
  }
}
