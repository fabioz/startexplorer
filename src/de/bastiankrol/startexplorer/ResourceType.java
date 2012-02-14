package de.bastiankrol.startexplorer;

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

  private final String label;

  private ResourceType(String label)
  {
    this.label = label;
  }

  public String getLabel()
  {
    return label;
  }

  public static ResourceType fromString(String resourceTypeAsString)
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