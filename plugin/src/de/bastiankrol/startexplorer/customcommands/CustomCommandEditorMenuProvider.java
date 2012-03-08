package de.bastiankrol.startexplorer.customcommands;

import static de.bastiankrol.startexplorer.Activator.*;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.actions.CompoundContributionItem;

/**
 * Provides the context menu items for the configured custom commands that can
 * be opened from an editor and invoked for a text selection.
 * 
 * @author Bastian Krol
 */
public class CustomCommandEditorMenuProvider extends CompoundContributionItem
{
  public static final String REGISTRATION_ID = CustomCommandEditorMenuProvider.class
      .getName();

  /**
   * Constructor
   */
  public CustomCommandEditorMenuProvider()
  {
    super();
  }

  /**
   * Constructor
   * 
   * @param id
   */
  public CustomCommandEditorMenuProvider(String id)
  {
    super(id);
  }

  @Override
  protected IContributionItem[] getContributionItems()
  {
    return getPluginContext().getCustomCommandEditorFactory()
        .getContributionItems();
  }
}
