package de.bastiankrol.startexplorer.preferences;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.bastiankrol.startexplorer.Activator;
import de.bastiankrol.startexplorer.ActivatorInstanceInjector;

public class LayoutTesterDelegate
{
  static final Display display;
  static final Shell shell;

  static
  {
    ActivatorInstanceInjector.injectDefaultInstanceForTest(new Activator()
    {

    });
    display = Display.getDefault();
    shell = new Shell(display);
  }

  static void openPage(AbstractStartExplorerPreferencePage page)
  {
    page.getPanel().pack();
    shell.pack();
    shell.open();
    while (!shell.isDisposed())
    {
      if (!display.readAndDispatch())
      {
        display.sleep();
      }
    }
  }
}
