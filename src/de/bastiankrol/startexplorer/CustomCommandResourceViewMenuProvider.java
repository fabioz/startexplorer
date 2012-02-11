package de.bastiankrol.startexplorer;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.actions.CompoundContributionItem;

/**
 * Provides the context menu items for the configured custom commands that can
 * be opened from Package Explorer/Project Explorer/Navigator invoked for a
 * resource.
 * 
 * @author Bastian Krol
 */
public class CustomCommandResourceViewMenuProvider extends
CompoundContributionItem
{
  
  /**
   * CustomCommandPackageExplorerMenuProvider Konstruktor.
   */
  public CustomCommandResourceViewMenuProvider()
  {
    super();
  }

  /**
   * CustomCommandPackageExplorerMenuProvider Konstruktor.
   * 
   * @param id ...
   */
  public CustomCommandResourceViewMenuProvider(String id)
  {
    super(id);
  }

  @Override
  protected IContributionItem[] getContributionItems()
  {
    return Activator.getDefault().getCustomCommandResourceViewFactory().getContributionItems();
  }
}
