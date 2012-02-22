package de.bastiankrol.startexplorer.preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;

import de.bastiankrol.startexplorer.util.ContentProposal;

/**
 * Shamelessly copied (and modified) from
 * {@link org.eclipse.jface.fieldassist.SimpleContentProposalProvider}, mainly
 * to provide explanations for each proposal.
 * 
 * @see IContentProposalProvider
 */
class StartExplorerContentProposalProvider implements IContentProposalProvider
{

  /*
   * The proposals provided.
   */
  private ContentProposal[] proposals;

  /*
   * Boolean that tracks whether filtering is used.
   */
  private boolean filterProposals = false;

  /**
   * Construct a StartExplorerContentProposalProvider whose content proposals
   * are always the specified array of Objects.
   * 
   * @param proposals a map with proposals as keys and their explanations (can
   *          be null) as value.
   */
  public StartExplorerContentProposalProvider(Map<String, String> proposalMap)
  {
    super();
    this.setProposals(proposalMap);
  }



  /**
   * Return an array of Objects representing the valid content proposals for a
   * field.
   * 
   * @param contents the current contents of the field (only consulted if
   *          filtering is set to <code>true</code>)
   * @param position the current cursor position within the field (ignored)
   * @return the array of Objects that represent valid proposals for the field
   *         given its current content.
   */
  public IContentProposal[] getProposals(String contents, int position)
  {
    if (!this.filterProposals)
    {
      return this.proposals;
    }
    else
    {
      List<ContentProposal> list = new ArrayList<ContentProposal>();
      for (ContentProposal proposal : this.proposals)
      {
        if (proposal.getContent().length() >= contents.length()
            && proposal.getContent().substring(0, contents.length()).equalsIgnoreCase(
                contents))
        {
          list.add(proposal);
        }
      }
      return (IContentProposal[]) list
          .toArray(new IContentProposal[list.size()]);
    }
  }

  /**
   * Set the Strings to be used as content proposals.
   * 
   * @param items the array of Strings to be used as proposals.
   */
  public void setProposals(Map<String, String> proposalMap)
  {
    this.proposals = new ContentProposal[proposalMap.size()];
    int i = 0;
    for (Map.Entry<String, String> entry : proposalMap.entrySet())
    {
      ContentProposal proposal = new ContentProposal(entry.getKey(), entry.getValue());
      proposals[i++] = proposal;
    }
  }

  /**
   * Set the boolean that controls whether proposals are filtered according to
   * the current field content.
   * 
   * @param filterProposals <code>true</code> if the proposals should be
   *          filtered to show only those that match the current contents of the
   *          field, and <code>false</code> if the proposals should remain the
   *          same, ignoring the field content.
   */
  public void setFiltering(boolean filterProposals)
  {
    this.filterProposals = filterProposals;
  }
}
