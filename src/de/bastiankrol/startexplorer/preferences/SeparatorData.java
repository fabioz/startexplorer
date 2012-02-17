package de.bastiankrol.startexplorer.preferences;

import static de.bastiankrol.startexplorer.preferences.PreferenceConstantsAndDefaults.*;

import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Value object for a separator for the "Copy resource path to clip board"
 * action. When multiple resources are selected, all selected resource's path
 * are copied to the clip board, separated by this separator.
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$
 */
class SeparatorData
{
  static enum SeparatorType
  {
    LF("\n"), CR("\r"), CRLF("\r\n"), TAB("\t"), CUSTOM(null);

    private String stringRepresentation;

    private SeparatorType(String representation)
    {
      this.stringRepresentation = representation;
    }

    boolean isCustomSeparator()
    {
      return this == CUSTOM;
    }

    String getStringRepresentationForStandardSeparator()
    {
      if (this.isCustomSeparator())
      {
        throw new IllegalStateException(
            "Separator#getRepresentation() may not be called for custom separator.");
      }
      return this.stringRepresentation;
    }

    static SeparatorType standardSeparatorFromString(String separatorString)
    {
      if (LF.stringRepresentation.equals(separatorString))
      {
        return LF;
      }
      else if (CR.stringRepresentation.equals(separatorString))
      {
        return CR;
      }
      else if (CRLF.stringRepresentation.equals(separatorString))
      {
        return CRLF;
      }
      else if (TAB.stringRepresentation.equals(separatorString))
      {
        return TAB;
      }
      else
      {
        throw new IllegalArgumentException("No standard separator for <"
            + separatorString + ">");
      }
    }
  }

  /**
   * Separator for the copy resource path to clipboard command
   */
  private SeparatorType separatorType;

  /**
   * Custom separator string for the copy resource path to clipboard command
   */
  private String customSeparatorString;

  SeparatorData()
  {
    this.initializeFromDefaults();
  }

  SeparatorData(boolean isCustom, String separatorStringStandard,
      String separatorStringCustom)
  {
    if (isCustom)
    {
      this.separatorType = SeparatorType.CUSTOM;
    }
    else
    {
      this.separatorType = SeparatorType
          .standardSeparatorFromString(separatorStringStandard);
    }
    this.customSeparatorString = separatorStringCustom;
  }

  /**
   * Returns the separator for the copy resource path to clipboard command
   * 
   * @return the separator for the copy resource path to clipboard command
   */
  SeparatorType getSeparatorType()
  {
    return this.separatorType;
  }

  /**
   * Returns the separator string for the copy resource path to clipboard
   * command
   * 
   * @return the separator string for the copy resource path to clipboard
   *         command
   */
  String getCustomSeparatorString()
  {
    return this.customSeparatorString;
  }

  /**
   * Sets the separator data for the copy resource path to clipboard command
   * 
   * @param copyResourcePathSeparator the separator data for the copy resource
   *          path to clipboard command
   */
  void setSeparatorData(SeparatorType copyResourcePathSeparator,
      String customCopyResourcePathSeparator)
  {
    this.separatorType = copyResourcePathSeparator;
    this.customSeparatorString = customCopyResourcePathSeparator;
  }

  /**
   * Sets the separator for the copy resource path to clipboard command, leaving
   * the custom separator string untouched.
   * 
   * @param separatorType the separator string for the copy resource path to
   *          clipboard command
   */
  void setSeparatorType(SeparatorType separatorType)
  {
    this.separatorType = separatorType;
  }

  /**
   * Sets the custom separator string for the copy resource path to clipboard
   * command
   * 
   * @param customSeparatorString the custom separator string
   */
  void setCustomSeparatorString(String customSeparatorString)
  {
    this.customSeparatorString = customSeparatorString;
  }

  /**
   * Returns the custom separator string for the copy resource path to clipboard
   * command
   * 
   * @return the separator string for the copy resource path to clipboard
   *         command
   */
  String getStringRepresentation()
  {
    if (this.separatorType.isCustomSeparator())
    {
      return this.customSeparatorString;
    }
    else
    {
      return this.separatorType.getStringRepresentationForStandardSeparator();
    }
  }

  void initializeFromDefaults()
  {
    this.separatorType = DEFAULT_COPY_RESOURCE_PATH_SEPARATOR;
    this.customSeparatorString = DEFAULT_CUSTOM_COPY_RESOURCE_PATH_SEPARATOR_STRING;
  }

  void storeValues(IPreferenceStore store)
  {
    if (this.separatorType.isCustomSeparator())
    {
      store.setValue(KEY_COPY_RESOURCE_PATH_SEPARATOR_IS_CUSTOM, true);
      store.setValue(KEY_COPY_RESOURCE_PATH_SEPARATOR_STANDARD, "");
    }
    else
    {
      store.setValue(KEY_COPY_RESOURCE_PATH_SEPARATOR_IS_CUSTOM, false);
      store.setValue(KEY_COPY_RESOURCE_PATH_SEPARATOR_STANDARD,
          this.separatorType.getStringRepresentationForStandardSeparator());
    }
    store.setValue(KEY_COPY_RESOURCE_PATH_SEPARATOR_CUSTOM_STRING,
        this.customSeparatorString);
  }
}
