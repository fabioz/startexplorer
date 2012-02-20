package de.bastiankrol.startexplorer;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.IStatus;

/**
 * Bundles common functionality for all implementations of
 * {@link IRuntimeExecCalls}.
 * 
 * @author Bastian Krol
 */
abstract class AbstractRuntimeExecCalls implements IRuntimeExecCalls
{
  /**
   * prefix for variables
   */
  public static final String VAR_BEGIN = "${";

  /**
   * suffix for variables
   */
  public static final String VAR_END = "}";

  private static final String RESOURCE_PATH = "resource_path";
  private static final String RESOURCE_PARENT = "resource_parent";
  private static final String RESOURCE_NAME = "resource_name";
  private static final String RESOURCE_WIHTOUT_EXTENSION = "resource_name_without_extension";
  private static final String RESOURCE_EXTENSION = "resource_extension";

  /**
   * variable for resource path
   */
  public static final String RESOURCE_PATH_VAR = VAR_BEGIN + RESOURCE_PATH
      + VAR_END;

  /**
   * variable for resource parent
   */
  public static final String RESOURCE_PARENT_VAR = VAR_BEGIN + RESOURCE_PARENT
      + VAR_END;

  /**
   * variable for resource name
   */
  public static final String RESOURCE_NAME_VAR = VAR_BEGIN + RESOURCE_NAME
      + VAR_END;

  public static final String RESOURCE_NAME_WIHTOUT_EXTENSION_VAR = VAR_BEGIN
      + RESOURCE_WIHTOUT_EXTENSION + VAR_END;

  public static final String RESOURCE_EXTENSION_VAR = VAR_BEGIN
      + RESOURCE_EXTENSION + VAR_END;

  IRuntimeExecDelegate runtimeExecDelegate;

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
   * @see de.bastiankrol.startexplorer.IRuntimeExecCalls#startFileManagerForFileList
   *      (java.util.List, boolean)
   */
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
   * @seede.bastiankrol.startexplorer.IRuntimeExecCalls# 
   *                                                     startSystemApplicationForFileList
   *                                                     (java.util.List)
   */
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
   * @see de.bastiankrol.startexplorer.IRuntimeExecCalls#startShellForFileList
   *      (java.util.List)
   */
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
   * @see de.bastiankrol.startexplorer.IRuntimeExecCalls#startCustomCommandForFileList
   *      (java.lang.String, java.util.List)
   */
  public void startCustomCommandForFileList(String customCommand,
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
   * @see de.bastiankrol.startexplorer.IRuntimeExecCalls#startFileManagerForFile
   *      (java.io.File, boolean)
   */
  public void startFileManagerForFile(File file, boolean selectFile)
  {
    this.runtimeExecDelegate.exec(
        this.getCommandForStartFileManager(file, selectFile),
        this.getWorkingDirectoryForStartFileManager(file));
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.IRuntimeExecCalls#startShellForFile
   *      (java.io.File)
   */
  public void startShellForFile(File file)
  {
    this.runtimeExecDelegate.exec(this.getCommandForStartShell(file),
        this.getWorkingDirectoryForForStartShell(file));
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.IRuntimeExecCalls#startSystemApplicationForFile
   *      (java.io.File)
   */
  public void startSystemApplicationForFile(File file)
  {
    this.runtimeExecDelegate.exec(
        this.getCommandForStartSystemApplication(file),
        this.getWorkingDirectoryForForStartSystemApplication(file));
  }

  abstract String getCommandForStartFileManager(File file, boolean selectFile);

  abstract File getWorkingDirectoryForStartFileManager(File file);

  abstract String getCommandForStartShell(File file);

  abstract File getWorkingDirectoryForForStartShell(File file);

  abstract String getCommandForStartSystemApplication(File file);

  abstract File getWorkingDirectoryForForStartSystemApplication(File file);

  /**
   * {@inheritDoc}
   * 
   * @see de.bastiankrol.startexplorer.IRuntimeExecCalls#startCustomCommandForFile
   *      (java.lang.String, java.io.File)
   */
  public void startCustomCommandForFile(String customCommand, File file)
  {
    boolean wrapFileParts = this.doFilePartsWantWrapping();

    String path = getPath(file, wrapFileParts);
    customCommand = customCommand.replace(RESOURCE_PATH_VAR, path);
    String name = getName(file, wrapFileParts);
    customCommand = customCommand.replace(RESOURCE_NAME_VAR, name);
    File parent = file.getParentFile();
    String parentPath;
    if (parent != null)
    {
      parentPath = getPath(parent, wrapFileParts);
      customCommand = customCommand.replace(RESOURCE_PARENT_VAR, parentPath);
    }
    else if (customCommand.contains(RESOURCE_PARENT_VAR))
    {
      Activator.logMessage(IStatus.WARNING,
          "The custom command contains the variable " + RESOURCE_PARENT_VAR
              + " but the file " + file.getAbsolutePath() + "has no parent.");
    }

    String[] nameWithoutExtensionAndExtension = separateNameAndExtension(file);
    customCommand = customCommand.replace(RESOURCE_NAME_WIHTOUT_EXTENSION_VAR,
        nameWithoutExtensionAndExtension[0]);
    customCommand = customCommand.replace(RESOURCE_EXTENSION_VAR,
        nameWithoutExtensionAndExtension[1]);

    this.runtimeExecDelegate.exec(customCommand,
        this.getWorkingDirectoryForCustomCommand(file));
  }

  abstract File getWorkingDirectoryForCustomCommand(File file);

  /**
   * @return {@code true} if and only if file path/name parts should be wrapped
   *         in quotes for this operating system's/desktop manager's file
   *         manager
   */
  abstract boolean doFilePartsWantWrapping();

  static String[] separateNameAndExtension(File file)
  {
    String filename = file.getName();
    String[] segments = filename.split("\\.");
    if (segments.length > 1)
    {
      // Only dot is leading dot => not a name separator
      if (segments.length == 2 && segments[0].length() == 0)
      {
        return new String[] { file.getName(), "" };
        // Multiple dots or not leading dot
      }
      else
      {
        String extension = segments[segments.length - 1];
        // For trailing dot
        if (filename.endsWith("."))
        {
          extension += ".";
        }
        String nameWithoutExtension = filename.substring(0, filename.length()
            - extension.length() - 1);
        return new String[] { nameWithoutExtension, extension };
      }
    }
    else
    {
      // No dot at all
      return new String[] { file.getName(), "" };
    }
  }

  String getPath(File file)
  {
    return getPath(file, this.doFilePartsWantWrapping());
  }

  String getParentPath(File file)
  {
    return getParentPath(file, this.doFilePartsWantWrapping());
  }

  String getName(File file)
  {
    return getName(file, this.doFilePartsWantWrapping());
  }

  static String getPath(File file, boolean wrap)
  {
    return wrap(file.getAbsolutePath(), wrap);
  }

  static String getParentPath(File file, boolean wrap)
  {
    if (file.getParent() != null)
    {
      return wrap(file.getParentFile().getAbsolutePath(), wrap);
    }
    else
    {
      return null;
    }
  }

  static String getName(File file, boolean wrap)
  {
    return wrap(file.getName(), wrap);
  }

  private static String wrap(String string, boolean wrap)
  {
    if (wrap)
    {
      return "\"" + string + "\"";
    }
    else
    {
      return string;
    }
  }
}
