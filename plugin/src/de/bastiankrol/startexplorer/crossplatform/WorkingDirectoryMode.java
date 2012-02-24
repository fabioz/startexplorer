package de.bastiankrol.startexplorer.crossplatform;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public enum WorkingDirectoryMode
{
  NONE("None"), RESOURCE("Resource"), PARENT("Parent");

  private static final Map<String, WorkingDirectoryMode> LABEL_TO_VALUE;

  private final String label;

  static
  {
    LABEL_TO_VALUE = new LinkedHashMap<String, WorkingDirectoryMode>();
    for (WorkingDirectoryMode value : WorkingDirectoryMode.values())
    {
      LABEL_TO_VALUE.put(value.getLabel(), value);
    }
  }

  private WorkingDirectoryMode(String label)
  {
    this.label = label;
  }

  public String getLabel()
  {
    return label;
  }

  public static Collection<String> allLabels()
  {
    return LABEL_TO_VALUE.keySet();
  }

  public static WorkingDirectoryMode fromLabel(String label)
  {
    return LABEL_TO_VALUE.get(label);
  }
}
