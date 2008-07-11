/**
 * (c) Copyright Mirasol Op'nWorks Inc. 2002, 2003. 
 * http://www.opnworks.com
 * Created on Apr 2, 2003 by lgauthier@opnworks.com
 * 
 */

package de.bastiankrol.startexplorer.preferences;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public class CommandConfigSorter extends ViewerSorter
{

  public enum Criteria {
    COMMAND, NAME_RESOURCES, NAME_TEXT_SELECTION
  };

  private Criteria criteria;

  /**
   * Creates a resource sorter that will use the given sort criteria.
   * 
   * @param criteria
   *          the sort criterion to use.
   */
  public CommandConfigSorter(Criteria criteria)
  {
    super();
    this.criteria = criteria;
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer,
   *      java.lang.Object, java.lang.Object)
   */
  @Override
  public int compare(Viewer viewer, Object o1, Object o2)
  {

    CommandConfig commandConfig1 = (CommandConfig) o1;
    CommandConfig commandConfig2 = (CommandConfig) o2;

    switch (criteria)
    {
      case COMMAND:
        return commandConfig1.getCommand().compareTo(
            commandConfig2.getCommand());
      case NAME_RESOURCES:
        return commandConfig1.getNameForResourcesMenu().compareTo(
            commandConfig2.getNameForResourcesMenu());
      case NAME_TEXT_SELECTION:
        return commandConfig1.getNameForTextSelectionMenu().compareTo(
            commandConfig2.getNameForTextSelectionMenu());
      default:
        throw new RuntimeException();
    }
  }

  /**
   * Returns the sort criteria of this this sorter.
   * 
   * @return the sort criterion
   */
  public Criteria getCriteria()
  {
    return criteria;
  }
}
