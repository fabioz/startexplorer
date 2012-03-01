package de.bastiankrol.startexplorer.variables;

import java.util.LinkedHashMap;
import java.util.Map;

public class VariableManagerForLayoutTests extends VariableManager
{
  public VariableManagerForLayoutTests()
  {
    super(null, null);
  }

  @Override
  public Map<String, String> getNamesWithDescriptions()
  {
    return new LinkedHashMap<String, String>();
  }
}