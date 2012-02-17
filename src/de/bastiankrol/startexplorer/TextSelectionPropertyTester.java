package de.bastiankrol.startexplorer;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jface.text.ITextSelection;

public class TextSelectionPropertyTester extends PropertyTester
{
  public TextSelectionPropertyTester()
  {
    super();
  }

  public boolean test(Object receiver, String property, Object[] args,
      Object expectedValue)
  {
    if (!(receiver instanceof ITextSelection))
    {
      return false;
    }
    if (!(expectedValue instanceof Boolean))
    {
      return false;
    }
    ITextSelection selection = (ITextSelection) receiver;
    boolean isEmpty = selection.isEmpty();
    boolean expected = (Boolean) expectedValue;
    return isEmpty == expected;
  }
}
