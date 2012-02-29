package de.bastiankrol.startexplorer.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Util
{

  public static String intToString(int i)
  {
    if (i < 0)
    {
      throw new IllegalArgumentException("Illegal call: intToString(" + i
          + ") - i < 0 is not allowed.");
    }
    if (i > 999)
    {
      throw new IllegalArgumentException("Illegal call: intToString(" + i
          + ") - i > 999 is not allowed.");
    }
    return String.format("%1$03d", i);
  }

  private static <E> boolean moveUpInListSingleEntry(List<E> list, int index)
  {
    // ignore calls which try to move up first index
    if (index == 0)
    {
      return false;
    }
    E element = list.remove(index);
    list.add(index - 1, element);
    return true;
  }

  private static <E> boolean moveDownInListSingleEntry(List<E> list, int index)
  {
    // ignore calls which try to move down last first index
    if (index == list.size() - 1)
    {
      return false;
    }
    E element = list.remove(index);
    list.add(index + 1, element);
    return true;
  }

  public static <E> boolean moveUpInList(List<E> list, int... indices)
  {
    // ignore calls which contain first index
    if (isInArray(indices, 0))
    {
      return false;
    }
    Arrays.sort(indices);
    boolean changed = false;
    for (int i : indices)
    {
      changed = moveUpInListSingleEntry(list, i) || changed;
    }
    return changed;
  }

  public static <E> boolean moveDownInList(List<E> list, int... indices)
  {
    // ignore calls which contain last index
    if (isInArray(indices, list.size() - 1))
    {
      return false;
    }
    // sort
    Arrays.sort(indices);

    // revert order
    int len = indices.length;
    int hlen = len / 2;
    int temp;
    for (int i = 0; i < hlen; i++)
    {
      temp = indices[i];
      indices[i] = indices[len - 1 - i];
      indices[len - 1 - i] = temp;
    }
    boolean changed = false;
    for (int i : indices)
    {
      changed = moveDownInListSingleEntry(list, i) || changed;
    }
    return changed;
  }

  public static boolean isInArray(int[] array, int element)
  {
    for (int i : array)
    {
      if (i == element)
      {
        return true;
      }
    }
    return false;
  }

  public static String[] separateNameAndExtension(File file)
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

  public static String getPath(File file, boolean wrap)
  {
    return wrap(file.getAbsolutePath(), wrap);
  }

  public static String getParentPath(File file, boolean wrap)
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

  public static String getName(File file, boolean wrap)
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