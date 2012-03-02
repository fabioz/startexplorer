package de.bastiankrol.startexplorer.util;

import static de.bastiankrol.startexplorer.util.Util.*;
import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Test class
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$
 */
public class UtilTest
{

  /**
   * JUnit test method
   */
  @Test(expected = IllegalArgumentException.class)
  public void intToStringBelowZero()
  {
    intToString(-3);
  }

  /**
   * JUnit test method
   */
  @Test(expected = IllegalArgumentException.class)
  public void intToStringAbove999()
  {
    intToString(1000);
  }

  /**
   * JUnit test method
   */
  @Test
  public void intToStringTest()
  {
    assertEquals("000", intToString(0));
    assertEquals("001", intToString(1));
    assertEquals("002", intToString(2));
    assertEquals("003", intToString(3));
    assertEquals("026", intToString(26));
    assertEquals("234", intToString(234));
    assertEquals("999", intToString(999));
  }

  /**
   * JUnit test method
   */
  @Test
  public void testMoveUpSingle()
  {
    List<Integer> list = new ArrayList<Integer>();
    for (int i = 0; i < 10; i++)
    {
      list.add(i);
    }

    assertTrue(moveUpInList(list, 3));
    this.assertExcept(list, 2, 3);
    assertEquals(3, (int) list.get(2));
    assertEquals(2, (int) list.get(3));
    assertTrue(moveUpInList(list, 2));
    this.assertExcept(list, 1, 2, 3);
    assertEquals(3, (int) list.get(1));
    assertEquals(1, (int) list.get(2));
    assertEquals(2, (int) list.get(3));
    assertTrue(moveUpInList(list, 1));
    this.assertExcept(list, 0, 1, 2, 3);
    assertEquals(3, (int) list.get(0));
    assertEquals(0, (int) list.get(1));
    assertEquals(1, (int) list.get(2));
    assertEquals(2, (int) list.get(3));
    // ignore calls for first index
    assertFalse(moveUpInList(list, 0));
    this.assertExcept(list, 0, 1, 2, 3);
    assertEquals(3, (int) list.get(0));
    assertEquals(0, (int) list.get(1));
    assertEquals(1, (int) list.get(2));
    assertEquals(2, (int) list.get(3));
    try
    {
      moveUpInList(list, 10);
      fail("expected IndexOutOfBoundsException");
    }
    catch (IndexOutOfBoundsException e)
    {
      // expected
    }
  }

  /**
   * JUnit test method
   */
  @Test
  public void testMoveUpMultiple()
  {
    List<Integer> list = new ArrayList<Integer>();

    // the order of the indices to move up should make no difference
    testMoveUpThree(list, 1, 2, 3);
    testMoveUpThree(list, 1, 3, 2);
    testMoveUpThree(list, 2, 1, 3);
    testMoveUpThree(list, 2, 3, 1);
    testMoveUpThree(list, 3, 1, 2);
    testMoveUpThree(list, 3, 2, 1);

    // ignore calls which contain first index
    list.clear();
    for (int i = 0; i < 10; i++)
    {
      list.add(i);
    }
    assertFalse(moveUpInList(list, 2, 0, 1));
    this.assertExcept(list);
    try
    {
      moveUpInList(list, 6, 8, 10);
      fail("expected IndexOutOfBoundsException");
    }
    catch (IndexOutOfBoundsException e)
    {
      // expected
    }
  }

  private void testMoveUpThree(List<Integer> list, int... indices)
  {
    list.clear();
    for (int i = 0; i < 10; i++)
    {
      list.add(i);
    }
    assertTrue(moveUpInList(list, indices));
    this.assertExcept(list, 0, 1, 2, 3);
    assertEquals(1, (int) list.get(0));
    assertEquals(2, (int) list.get(1));
    assertEquals(3, (int) list.get(2));
    assertEquals(0, (int) list.get(3));
  }

  /**
   * JUnit test method
   */
  @Test
  public void testMoveDownSingle()
  {
    List<Integer> list = new ArrayList<Integer>();
    for (int i = 0; i < 10; i++)
    {
      list.add(i);
    }

    assertTrue(moveDownInList(list, 6));
    this.assertExcept(list, 6, 7);
    assertEquals(7, (int) list.get(6));
    assertEquals(6, (int) list.get(7));
    assertTrue(moveDownInList(list, 7));
    this.assertExcept(list, 6, 7, 8);
    assertEquals(7, (int) list.get(6));
    assertEquals(8, (int) list.get(7));
    assertEquals(6, (int) list.get(8));
    assertTrue(moveDownInList(list, 8));
    this.assertExcept(list, 6, 7, 8, 9);
    assertEquals(7, (int) list.get(6));
    assertEquals(8, (int) list.get(7));
    assertEquals(9, (int) list.get(8));
    assertEquals(6, (int) list.get(9));
    // ignore calls for last index
    assertFalse(moveDownInList(list, 9));
    this.assertExcept(list, 6, 7, 8, 9);
    assertEquals(7, (int) list.get(6));
    assertEquals(8, (int) list.get(7));
    assertEquals(9, (int) list.get(8));
    assertEquals(6, (int) list.get(9));

    try
    {
      moveDownInList(list, 10);
      fail("expected IndexOutOfBoundsException");
    }
    catch (IndexOutOfBoundsException e)
    {
      // expected
    }
  }

  /**
   * JUnit test method
   */
  @Test
  public void testMoveDownMultiple()
  {
    List<Integer> list = new ArrayList<Integer>();

    // the order of the indices to move up should make no difference
    testMoveDownThree(list, 6, 7, 8);
    testMoveDownThree(list, 6, 8, 7);
    testMoveDownThree(list, 7, 6, 8);
    testMoveDownThree(list, 7, 8, 6);
    testMoveDownThree(list, 8, 6, 7);
    testMoveDownThree(list, 8, 7, 6);

    // ignore calls which contain last index
    list.clear();
    for (int i = 0; i < 10; i++)
    {
      list.add(i);
    }
    assertFalse(moveDownInList(list, 4, 9, 3));
    this.assertExcept(list);
    try
    {
      moveDownInList(list, 6, 8, 10);
      fail("expected IndexOutOfBoundsException");
    }
    catch (IndexOutOfBoundsException e)
    {
      // expected
    }
  }

  private void testMoveDownThree(List<Integer> list, int... indices)
  {
    list.clear();
    for (int i = 0; i < 10; i++)
    {
      list.add(i);
    }
    assertTrue(moveDownInList(list, indices));
    this.assertExcept(list, 6, 7, 8, 9);
    assertEquals(9, (int) list.get(6));
    assertEquals(6, (int) list.get(7));
    assertEquals(7, (int) list.get(8));
    assertEquals(8, (int) list.get(9));
  }

  /**
   * JUnit test method
   */
  @Test
  public void testMoveDownMultipleOddNumberOfEntries()
  {
    List<Integer> list = new ArrayList<Integer>();
    for (int i = 0; i < 9; i++)
    {
      list.add(i);
    }
    assertTrue(moveDownInList(list, 5, 6, 7));
    for (int i = 0; i < 5; i++)
    {
      assertEquals(i, (int) list.get(i));
    }
    assertEquals(8, (int) list.get(5));
    assertEquals(5, (int) list.get(6));
    assertEquals(6, (int) list.get(7));
    assertEquals(7, (int) list.get(8));
  }

  private void assertExcept(List<Integer> list, int... indices)
  {
    for (int i = 0; i < 10; i++)
    {
      if (isInArray(indices, i))
      {
        continue;
      }
      else
      {
        assertEquals(i, (int) list.get(i));
      }
    }
  }

  @Test
  public void shouldSplitFilenamesWithoutDotCorrectly()
  {
    String[] nameWithoutExtensionAndExtension = separateNameAndExtension(
        new File("/path/to/resource"), true);
    assertEquals("\"resource\"", nameWithoutExtensionAndExtension[0]);
    assertEquals("", nameWithoutExtensionAndExtension[1]);
  }

  @Test
  public void shouldSplitFilenamesWithOneDotCorrectly()
  {
    String[] nameWithoutExtensionAndExtension = separateNameAndExtension(
        new File("/path/to/resource.extension"), true);
    assertEquals("\"resource\"", nameWithoutExtensionAndExtension[0]);
    assertEquals("\"extension\"", nameWithoutExtensionAndExtension[1]);
  }

  @Test
  public void shouldSplitFilenamesWithSeveralDotsCorrectly()
  {
    String[] nameWithoutExtensionAndExtension = separateNameAndExtension(
        new File("/path/to/re.so.ur.ce.extension"), true);
    assertEquals("\"re.so.ur.ce\"", nameWithoutExtensionAndExtension[0]);
    assertEquals("\"extension\"", nameWithoutExtensionAndExtension[1]);
  }

  @Test
  public void shouldSplitFilenamesWithTrailingDotCorrectly()
  {
    // Not a valid file name on Windows, but on Linux
    String[] nameWithoutExtensionAndExtension = separateNameAndExtension(
        new File("/path/to/resource."), false);
    assertEquals("resource.", nameWithoutExtensionAndExtension[0]);
    assertEquals("", nameWithoutExtensionAndExtension[1]);
  }

  @Test
  public void shouldSplitFilenamesWithSeveralDotsAndTrailingDotCorrectly()
  {
    // Not a valid file name on Windows, but on Linux
    String[] nameWithoutExtensionAndExtension = separateNameAndExtension(
        new File("/path/to/re.so.ur.ce.extension."), false);
    assertEquals("re.so.ur.ce", nameWithoutExtensionAndExtension[0]);
    assertEquals("extension.", nameWithoutExtensionAndExtension[1]);
  }

  @Test
  public void shouldSplitFilenamesWithOnlyLeadingDotCorrectly()
  {
    // Arguable: From my point of view, a leading dot should not
    // be interpreted as a name separator because it's
    // used to hide files in *nix.
    String[] nameWithoutExtensionAndExtension = separateNameAndExtension(
        new File("/path/to/.resource"), false);
    assertEquals(".resource", nameWithoutExtensionAndExtension[0]);
    assertEquals("", nameWithoutExtensionAndExtension[1]);
  }

  @Test
  public void shouldSplitFilenamesWithLeadingDotAndMoreDotsCorrectly()
  {
    // Arguable: From my point of view, a leading dot should not
    // be interpreted as a name separator because it's
    // used to hide files in *nix.
    String[] nameWithoutExtensionAndExtension = separateNameAndExtension(
        new File("/path/to/.re.so.ur.ce.extension"), false);
    assertEquals(".re.so.ur.ce", nameWithoutExtensionAndExtension[0]);
    assertEquals("extension", nameWithoutExtensionAndExtension[1]);
  }
}
