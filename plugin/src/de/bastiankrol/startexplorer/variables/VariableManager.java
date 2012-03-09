package de.bastiankrol.startexplorer.variables;

import static de.bastiankrol.startexplorer.util.Util.*;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.variables.IDynamicVariable;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.IValueVariable;
import org.eclipse.core.variables.VariablesPlugin;

import de.bastiankrol.startexplorer.Activator;
import de.bastiankrol.startexplorer.util.MessageDialogHelper;
import de.bastiankrol.startexplorer.util.MessageDialogHelper;

/**
 * Provides access to the Eclipse variables API (org.eclipse.core.variables).
 * 
 * @author bastian.krol
 */
public class VariableManager
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

  private IStringVariableManager variableManager;
  private MessageDialogHelper messageDialogHelper;

  public VariableManager()
  {
    this.variableManager = VariablesPlugin.getDefault()
        .getStringVariableManager();
    this.messageDialogHelper = new MessageDialogHelper();
  }

  VariableManager(IStringVariableManager eclipseVariableManager,
      MessageDialogHelper messageDialogHelper)
  {
    this.variableManager = eclipseVariableManager;
    this.messageDialogHelper = messageDialogHelper;
  }

  public Map<String, String> getNamesWithDescriptions()
  {
    Map<String, String> variableNamesWithDescription = new LinkedHashMap<String, String>();
    IValueVariable[] valueVariables = variableManager.getValueVariables();
    for (IValueVariable variable : valueVariables)
    {
      variableNamesWithDescription.put("${" + variable.getName() + "}",
          variable.getDescription());
    }
    IDynamicVariable[] dynamicVariables = variableManager.getDynamicVariables();
    for (IDynamicVariable variable : dynamicVariables)
    {
      variableNamesWithDescription.put("${" + variable.getName() + "}",
          variable.getDescription());
    }
    return variableNamesWithDescription;
  }

  // TODO Integrate "old" StartExplorer variables in standard Eclipse variables
  // mechanism, that is, provide them as an extension as dynamic variables.

  public String replaceAllVariablesInCommand(String command, File file,
      boolean wrapFileParts)
  {
    command = replaceStartExplorerVariables(command, file, wrapFileParts);
    command = replaceEclipseVariables(command);
    return command;
  }

  private String replaceStartExplorerVariables(String command, File file,
      boolean wrapFileParts)
  {
    String path = getPath(file, wrapFileParts);
    command = command.replace(RESOURCE_PATH_VAR, path);
    String name = getName(file, wrapFileParts);
    command = command.replace(RESOURCE_NAME_VAR, name);
    File parent = file.getParentFile();
    String parentPath;
    if (parent != null)
    {
      parentPath = getPath(parent, wrapFileParts);
      command = command.replace(RESOURCE_PARENT_VAR, parentPath);
    }
    else if (command.contains(RESOURCE_PARENT_VAR))
    {
      Activator.logWarning("The custom command contains the variable "
          + RESOURCE_PARENT_VAR + " but the file " + file.getAbsolutePath()
          + "has no parent.");
    }

    String[] nameWithoutExtensionAndExtension = separateNameAndExtension(file,
        wrapFileParts);
    command = command.replace(RESOURCE_NAME_WIHTOUT_EXTENSION_VAR,
        nameWithoutExtensionAndExtension[0]);
    command = command.replace(RESOURCE_EXTENSION_VAR,
        nameWithoutExtensionAndExtension[1]);
    return command;
  }

  private String replaceEclipseVariables(String command)
  {
    try
    {
      command = this.variableManager.performStringSubstitution(command);
    }
    catch (CoreException e)
    {
      String message;
      if (e.getMessage() != null)
      {
        message = e.getMessage();
      }
      else
      {
        message = "A "
            + e.getClass().getSimpleName()
            + " occured while resolving the variables in the custom command <"
            + command
            + ">. No further information about the underlying problem is available.";
      }
      this.messageDialogHelper.displayErrorMessage(
          "Error resolving variables in custom command", message);
      throw new RuntimeException(e);
    }
    return command;
  }
}
