package de.bastiankrol.startexplorer.crossplatform;

import static de.bastiankrol.startexplorer.Activator.getPluginContext;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

import de.bastiankrol.startexplorer.Activator;
import de.bastiankrol.startexplorer.util.Util;
import de.bastiankrol.startexplorer.variables.VariableManager;

/**
 * Bundles common functionality for all implementations of
 * {@link IRuntimeExecCalls}.
 * 
 * @author Bastian Krol
 */
abstract class AbstractRuntimeExecCalls implements IRuntimeExecCalls
{
  IRuntimeExecDelegate runtimeExecDelegate;

  private final Capabilities defaultCapabilities = Capabilities.create()
      .build();

  /**
   * Creates a new instance and initializes the {@link RuntimeExecDelegate}.
   */
  AbstractRuntimeExecCalls()
  {
    this.runtimeExecDelegate = new RuntimeExecDelegate();
  }

  /**
   * Creates a new instance with the given {@link RuntimeExecDelegate}.
   * 
   * @param delegate the RuntimeExecDelegate to use
   */
  AbstractRuntimeExecCalls(RuntimeExecDelegate delegate)
  {
    this.runtimeExecDelegate = delegate;
  }

  /**
   * Sets the IRuntimeExecDelegate which executes the calls to
   * {@link Runtime#exec(String)}.
   * 
   * @param runtimeExecDelegate the {@link IRuntimeExecDelegate}
   */
  public void setRuntimeExecDelegate(IRuntimeExecDelegate runtimeExecDelegate)
  {
    this.runtimeExecDelegate = runtimeExecDelegate;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.crossplatform.IRuntimeExecCalls#startFileManagerForFileList
   *      (java.util.List, boolean)
   */
  @Override
  public void startFileManagerForFileList(List<File> fileList,
      boolean selectFile)
  {
    for (File file : fileList)
    {
      this.startFileManagerForFile(file, selectFile);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.IRuntimeExecCalls#
   *      startSystemApplicationForFileList (java.util.List)
   */
  @Override
  public void startSystemApplicationForFileList(List<File> fileList)
  {
    for (File file : fileList)
    {
      this.startSystemApplicationForFile(file);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.crossplatform.IRuntimeExecCalls#startShellForFileList
   *      (java.util.List)
   */
  @Override
  public void startShellForFileList(List<File> fileList)
  {
    for (File file : fileList)
    {
      this.startShellForFile(file);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.crossplatform.IRuntimeExecCalls#startCustomCommandForFileList(String[],
   *      List)
   */
  @Override
  public void startCustomCommandForFileList(String[] customCommand,
      List<File> fileList)
  {
    for (File file : fileList)
    {
      this.startCustomCommandForFile(customCommand, file);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.crossplatform.IRuntimeExecCalls#startFileManagerForFile
   *      (java.io.File, boolean)
   */
  @Override
  public void startFileManagerForFile(File file, boolean selectFile)
  {
    this.runtimeExecDelegate.exec(
        this.getCommandForStartFileManager(file, selectFile),
        this.getWorkingDirectoryForStartFileManager(file), this.isWindows());
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.crossplatform.IRuntimeExecCalls#startFileManagerForUrl(java.net.URL)
   */
  @Override
  public void startFileManagerForUrl(URL url)
  {
    this.runtimeExecDelegate.exec(this.getCommandForStartFileManager(url),
        null, this.isWindows());
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.crossplatform.IRuntimeExecCalls#startShellForFile
   *      (java.io.File)
   */
  @Override
  public void startShellForFile(File file)
  {
    this.runtimeExecDelegate.exec(this.getCommandForStartShell(file),
        this.getWorkingDirectoryForForStartShell(file), this.isWindows());
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.crossplatform.IRuntimeExecCalls#startSystemApplicationForFile
   *      (java.io.File)
   */
  @Override
  public void startSystemApplicationForFile(File file)
  {
    this.runtimeExecDelegate.exec(
        this.getCommandForStartSystemApplication(file),
        this.getWorkingDirectoryForForStartSystemApplication(file),
        this.isWindows());
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.crossplatform.IRuntimeExecCalls#startSystemApplicationForUrl(URL)
   */
  @Override
  public void startSystemApplicationForUrl(URL url)
  {
    if (Desktop.isDesktopSupported())
    {
      Desktop desktop = Desktop.getDesktop();
      try
      {
        desktop.browse(url.toURI());
      }
      catch (IOException e)
      {
        Activator
            .getPluginContext()
            .getLogFacility()
            .logException(
                "Opening a browser for URL " + url.toString() + " failed.", e);
      }
      catch (URISyntaxException e)
      {
        Activator
            .getPluginContext()
            .getLogFacility()
            .logException(
                "Opening a browser for URL " + url.toString() + " failed.", e);
      }
    }
  }

  abstract String[] getCommandForStartFileManager(File file, boolean selectFile);

  abstract String[] getCommandForStartFileManager(URL url);

  abstract File getWorkingDirectoryForStartFileManager(File file);

  abstract String[] getCommandForStartShell(File file);

  abstract File getWorkingDirectoryForForStartShell(File file);

  abstract String[] getCommandForStartSystemApplication(File file);

  abstract File getWorkingDirectoryForForStartSystemApplication(File file);

  abstract boolean isWindows();

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.crossplatform.IRuntimeExecCalls#startCustomCommandForFile(String[],
   *      File)
   */
  @Override
  public void startCustomCommandForFile(String[] cmdArray, File file)
  {
    boolean wrapFileParts = this.doFilePartsWantWrapping();
    boolean escapeFileParts = this.doFilePartsWantEscaping();
    getVariableManager().replaceAllVariablesInCommand(cmdArray, file,
        wrapFileParts, escapeFileParts);
    this.runtimeExecDelegate.exec(cmdArray,
        this.getWorkingDirectoryForCustomCommand(file), this.isWindows());
  }

  /**
   * If not on windows, this method tokenizes a command string (with spaces)
   * into separate command parts, like Runtime.exec(String) would do. On
   * Windows, it returns a single element array with the input string.
   * 
   * 
   * @param command the command to split up
   * @return the command parts
   */
  public String[] convertCommandStringToArray(String command)
  {
    if (!this.isWindows())
    {
      // mimic tokenization behavior of Runtime.exec(String) for custom commands
      StringTokenizer st = new StringTokenizer(command);
      String[] cmdArray = new String[st.countTokens()];
      for (int i = 0; st.hasMoreTokens(); i++)
      {
        cmdArray[i] = st.nextToken();
      }
      return cmdArray;
    }
    else
    {
      return new String[] { command };
    }
  }

  /**
   * Replaces all supported variables in the given {@code command} and returns
   * the result. If {@code command} contains no variables, it will be returned
   * unchanged.
   * 
   * @param command the string in which variables will be replaced
   * @param file the file to evaluate for the variables
   * @param wrapFileParts if file parts should be wrapped in quotes
   * @return the command with variables substituted, if any.
   */

  abstract File getWorkingDirectoryForCustomCommand(File file);

  /**
   * @return {@code true} if and only if file path/name parts should be wrapped
   *         in quotes for this operating system's/desktop manager's file
   *         manager
   */
  abstract boolean doFilePartsWantWrapping();

  /**
   * @return {@code true} if and only if spaces in file path/name parts should
   *         be escaped by prepending a backspace for this operating
   *         system's/desktop manager's file manager
   */
  abstract boolean doFilePartsWantEscaping();

  /**
   * Default implementation returns an empty capabilities object. Subclasses
   * should override as needed.
   */
  @Override
  public Capabilities getCapabilities()
  {
    return defaultCapabilities;
  }

  VariableManager getVariableManager()
  {
    return getPluginContext().getVariableManager();
  }

  String getPath(File file)
  {
    return Util.getPath(file, this.doFilePartsWantWrapping(),
        this.doFilePartsWantEscaping());
  }

  String getParentPath(File file)
  {
    return Util.getParentPath(file, this.doFilePartsWantWrapping(),
        this.doFilePartsWantEscaping());
  }

  String getName(File file)
  {
    return Util.getName(file, this.doFilePartsWantWrapping(),
        this.doFilePartsWantEscaping());
  }
}
