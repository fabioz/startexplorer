package de.bastiankrol.startexplorer.preferences;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

abstract class EventlessSelectionAdapter extends SelectionAdapter
{
  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
   */
  public final void widgetSelected(SelectionEvent event)
  {
    this.widgetSelected();
  }

  abstract void widgetSelected();
}