package de.bastiankrol.startexplorer.crossplatform;

/**
 * Represents the capabilities of a platform.
 */
public class Capabilities
{

  private final boolean fileManagerSupportsFileSelection;
  private final boolean fileManagerSupportsUrls;
  private final boolean hasSystemApplicationForUrls;

  public Capabilities(boolean fileManagerSupportsFileSelection,
      boolean fileManagerSupportsUrls, boolean hasSystemApplicationForUrls)
  {
    super();
    this.fileManagerSupportsFileSelection = fileManagerSupportsFileSelection;
    this.fileManagerSupportsUrls = fileManagerSupportsUrls;
    this.hasSystemApplicationForUrls = hasSystemApplicationForUrls;
  }

  public static Builder create()
  {
    return new Builder();
  }

  /**
   * @return {@code true} if and only if the operating system's/desktop
   *         manager's file manager supports selecting files (as opposed to just
   *         opening a certain directory) on startup
   */
  public boolean isFileSelectionSupportedByFileManager()
  {
    return this.fileManagerSupportsFileSelection;
  }

  /**
   * @return {@code true} if and only if the operating system's/desktop
   *         manager's file manager supports URLs
   */
  public boolean areUrlsSupportedByFileManager()
  {
    return fileManagerSupportsUrls;
  }

  public boolean isThereASystemApplicationForUrls()
  {
    return hasSystemApplicationForUrls;
  }

  static class Builder
  {
    private boolean fileManagerSupportsFileSelection;
    private boolean fileManagerSupportsUrls;
    // by default, we assume that a browser (aka system application for URLs) is
    // always present
    private boolean hasSystemApplicationForUrls = true;

    Builder withFileSelectionSupport()
    {
      this.fileManagerSupportsFileSelection = true;
      return this;
    }

    Builder withFileSelectionSupport(boolean fileManagerSupportsFileSelection)
    {
      this.fileManagerSupportsFileSelection = fileManagerSupportsFileSelection;
      return this;
    }

    Builder withUrlSupport()
    {
      this.fileManagerSupportsUrls = true;
      return this;
    }

    Builder withUrlSupport(boolean fileManagerSupportsUrls)
    {
      this.fileManagerSupportsUrls = fileManagerSupportsUrls;
      return this;
    }

    Builder withoutBrowser()
    {
      this.hasSystemApplicationForUrls = false;
      return this;
    }

    Capabilities build()
    {
      return new Capabilities(this.fileManagerSupportsFileSelection,
          this.fileManagerSupportsUrls, this.hasSystemApplicationForUrls);
    }
  }
}
