package de.bastiankrol.startexplorer.util;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.junit.Before;
import org.junit.Test;

import de.bastiankrol.startexplorer.ResourceType;

/**
 * Test class
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$
 */
public class PathCheckerTest
{

  private PathChecker pathChecker;
  private MessageDialogHelper mockMessageDialogHelper;
  private ExecutionEvent executionEvent;

  /**
   * JUnit before
   */
  @Before
  public void setUp()
  {
    this.pathChecker = new PathChecker();
    this.mockMessageDialogHelper = mock(MessageDialogHelper.class);
    this.pathChecker.setMessageDialogHelper(this.mockMessageDialogHelper);
    this.executionEvent = new ExecutionEvent();
  }

  /**
   * JUnit test method
   * 
   * @throws Exception ...
   */
  @Test
  public void testNullPath() throws Exception
  {
    try
    {
      this.pathChecker.checkPath(null, ResourceType.BOTH, this.executionEvent);
      fail("IllegalArgumentException expected");
    }
    catch (IllegalArgumentException e)
    {
      // IllegalArgumentException expected
    }
  }

  /**
   * JUnit test method
   * 
   * @throws Exception ...
   */
  @Test
  public void testNullResourceType() throws Exception
  {
    try
    {
      this.pathChecker
          .checkPath("/path/to/resource", null, this.executionEvent);
      fail("IllegalArgumentException expected");
    }
    catch (IllegalArgumentException e)
    {
      // IllegalArgumentException expected
    }
  }

  /**
   * JUnit test method
   * 
   * @throws Exception ...
   */
  @Test
  public void testNullExecutionEvent() throws Exception
  {
    try
    {
      this.pathChecker.checkPath("/path/to/resource", ResourceType.BOTH, null);
      fail("IllegalArgumentException expected");
    }
    catch (IllegalArgumentException e)
    {
      // IllegalArgumentException expected
    }
  }

  /**
   * JUnit test method
   * 
   * @throws Exception ...
   */
  @Test
  public void testResourceDoesNotExistNoParentAvailable() throws Exception
  {
    String pathString = "doesnotexist";
    File checkedFile = this.pathChecker.checkPath(pathString,
        ResourceType.BOTH, this.executionEvent);
    verify(this.mockMessageDialogHelper)
        .displayErrorMessage(
            "Resource does not exist",
            "The path "
                + pathString
                + " is not an existing file or folder and there is no parent folder available.",
            this.executionEvent);
    assertNull(checkedFile);
  }

  /**
   * JUnit test method
   * 
   * @throws Exception ...
   */
  @Test
  public void testResourceDoesNotExistNorDoesParent() throws Exception
  {
    String pathString = "test-resources/does/not/exist";
    File checkedFile = this.pathChecker.checkPath(pathString,
        ResourceType.BOTH, this.executionEvent);
    verify(this.mockMessageDialogHelper).displayErrorMessage(
        "Resource does not exist",
        "The path " + pathString
            + " is not an existing file or folder nor does its parent exist.",
        this.executionEvent);
    assertNull(checkedFile);
  }

  /**
   * JUnit test method
   * 
   * @throws Exception ...
   */
  @Test
  public void testDirectoryInsteadOfFile() throws Exception
  {
    String pathString = "test-resources/path/to/resource";
    File checkedFile = this.pathChecker.checkPath(pathString,
        ResourceType.FILE, this.executionEvent);
    verify(this.mockMessageDialogHelper).displayErrorMessage("Not a file",
        "The path " + pathString + " is a directory, not a file.",
        this.executionEvent);
    assertNull(checkedFile);
  }

  /**
   * JUnit test method
   * 
   * @throws Exception ...
   */
  @Test
  public void testFileInsteadOfDirectoryReturnParentDirectory()
      throws Exception
  {
    String pathString = "test-resources/path/to/resource/file.txt";
    File checkedFile = this.pathChecker.checkPath(pathString,
        ResourceType.DIRECTORY, this.executionEvent);
    assertEquals(new File(pathString).getParentFile(), checkedFile);
  }

  /**
   * JUnit test method
   * 
   * @throws Exception ...
   */
  @Test
  public void testFile() throws Exception
  {
    String pathString = "test-resources/path/to/resource/file.txt";
    File checkedFile = this.pathChecker.checkPath(pathString,
        ResourceType.FILE, this.executionEvent);
    assertEquals(new File(pathString), checkedFile);
  }

  /**
   * JUnit test method
   * 
   * @throws Exception ...
   */
  @Test
  public void testDirectoryNoTrailingSlash() throws Exception
  {
    String pathString = "test-resources/path/to/resource";
    File checkedFile = this.pathChecker.checkPath(pathString,
        ResourceType.DIRECTORY, this.executionEvent);
    assertEquals(new File(pathString), checkedFile);

  }

  /**
   * JUnit test method
   * 
   * @throws Exception ...
   */
  @Test
  public void testDirectoryTrailingSlash() throws Exception
  {
    String pathString = "test-resources/path/to/resource/";
    File checkedFile = this.pathChecker.checkPath(pathString,
        ResourceType.DIRECTORY, this.executionEvent);
    assertEquals(new File(pathString), checkedFile);
  }
}
