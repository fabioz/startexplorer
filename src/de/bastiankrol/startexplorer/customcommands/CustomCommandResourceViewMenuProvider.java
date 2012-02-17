package de.bastiankrol.startexplorer.customcommands;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.actions.CompoundContributionItem;

import de.bastiankrol.startexplorer.Activator;


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
