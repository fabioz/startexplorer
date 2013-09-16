package de.bastiankrol.startexplorer.handlers.delegates;

import java.io.File;
import java.net.URL;

import de.bastiankrol.startexplorer.ResourceType;
import de.bastiankrol.startexplorer.crossplatform.Capabilities;

/**
 * Examines the selected region in a text file, tries to interpret it as a
 * Windows filesystem path and tries to start the default windows system
 * application for the file described by that path.
 * 
 * @author Bastian Krol
 */
public class StartSystemApplicationFromEditorHandlerDelegate extends
    AbstractStartFromEditorHandlerDelegate
{
  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromEditorHandler#getResourceType()
   */
  protected ResourceType getResourceType()
  {
    return ResourceType.FILE;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromEditorHandler#doActionForFile(java.io.File)
   */
  protected void doActionForFile(File file)
  {
    this.getRuntimeExecCalls().startSystemApplicationForFile(file);
  }

  @Override
  protected boolean areUrlsSupported(Capabilities capabilities)
  {
    return capabilities.isThereASystemApplicationForUrls();
  }

  @Override
  protected void doActionForUrl(URL url)
  {
    this.getRuntimeExecCalls().startSystemApplicationForUrl(url);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.handlers.AbstractStartFromEditorHandler#getAppropriateStartFromResourceHandler()
   */
  @Override
  protected AbstractStartFromResourceHandlerDelegate getAppropriateStartFromResourceHandlerDelegate()
  {
    return new StartSystemApplicationFromResourceHandlerDelegate();
  }
}
