package de.bastiankrol.startexplorer;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Type of a filesystem resource, either file or directory.
 */
public enum ResourceType
{
  /**
   * Resource of type file (in contrast to directory).
   */
  FILE("Files"),

  /**
   * Resource of type directory (in contrast to file).
   */
  DIRECTORY("Folders"),

  /**
   * File or directory.
   */
  BOTH("Files & Folders");

  private static final Map<String, ResourceType> LABEL_TO_RESOURCE_TYPE;

  private final String label;

  static
  {
    LABEL_TO_RESOURCE_TYPE = new LinkedHashMap<String, ResourceType>();
    for (ResourceType resourceType : ResourceType.values())
    {
      LABEL_TO_RESOURCE_TYPE.put(resourceType.getLabel(), resourceType);
    }
  }

  private ResourceType(String label)
  {
    this.label = label;
  }

  public String getLabel()
  {
    return label;
  }

  public static Collection<String> allLabels()
  {
    return LABEL_TO_RESOURCE_TYPE.keySet();
  }

  public static ResourceType fromLabel(String label)
  {
    return LABEL_TO_RESOURCE_TYPE.get(label);
  }

  public static ResourceType fromName(String resourceTypeAsString)
  {
    if (resourceTypeAsString != null
        && resourceTypeAsString.equals(ResourceType.FILE.name()))
    {
      return ResourceType.FILE;
    }
    else if (resourceTypeAsString != null
        && resourceTypeAsString.equals(ResourceType.DIRECTORY.name()))
    {
      return ResourceType.DIRECTORY;
    }
    else
    {
      return ResourceType.BOTH;
    }
  }
}