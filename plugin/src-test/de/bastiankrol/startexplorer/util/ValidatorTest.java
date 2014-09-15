package de.bastiankrol.startexplorer.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.junit.Before;
import org.junit.Test;

import de.bastiankrol.startexplorer.ResourceType;

/**
 * Tests for {@link Validator}.
 * 
 * @author Bastian Krol
 */
public class ValidatorTest
{

  private Validator validator;
  private MessageDialogHelper mockMessageDialogHelper;
  private ExecutionEvent executionEvent;

  /**
   * JUnit before
   */
  @Before
  public void setUp()
  {
    this.validator = new Validator();
    this.mockMessageDialogHelper = mock(MessageDialogHelper.class);
    this.validator.setMessageDialogHelper(this.mockMessageDialogHelper);
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
      this.validator.checkPath(null, ResourceType.BOTH, null);
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
      this.validator.checkPath("/path/to/resource", null, null);
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
    Validator.MaybeFile checkedFile = this.validator.checkPath(pathString,
        ResourceType.BOTH, null);
    assertNull(checkedFile.file);
    assertEquals(Validator.Reason.RESOURCE_DOES_NOT_EXIST, checkedFile.reason);
    this.validator.showMessageFor(checkedFile.reason, pathString,
        this.executionEvent);
    verify(this.mockMessageDialogHelper)
        .displayErrorMessage(
            "Resource does not exist",
            "The path "
                + pathString
                + " is not an existing file or folder and there is no parent folder available.",
            this.executionEvent);
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
    Validator.MaybeFile checkedFile = this.validator.checkPath(pathString,
        ResourceType.BOTH, null);
    assertNull(checkedFile.file);
    assertEquals(Validator.Reason.RESOURCE_DOES_NOT_EXIST, checkedFile.reason);
    this.validator.showMessageFor(checkedFile.reason, pathString,
        this.executionEvent);
    verify(this.mockMessageDialogHelper).displayErrorMessage(
        "Resource does not exist",
        "The path " + pathString
                + " is not an existing file or folder and there is no parent folder available.",
        this.executionEvent);
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
    Validator.MaybeFile checkedFile = this.validator.checkPath(pathString,
        ResourceType.FILE, null);
    assertNull(checkedFile.file);
    assertEquals(Validator.Reason.NOT_A_FILE, checkedFile.reason);
    this.validator.showMessageFor(checkedFile.reason, pathString,
        this.executionEvent);
    verify(this.mockMessageDialogHelper).displayErrorMessage("Not a file",
        "The path " + pathString + " is a directory, not a file.",
        this.executionEvent);
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
    Validator.MaybeFile checkedFile = this.validator.checkPath(pathString,
        ResourceType.DIRECTORY, null);
    assertEquals(new File(pathString).getParentFile(), checkedFile.file);
    assertNull(checkedFile.reason);
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
    Validator.MaybeFile checkedFile = this.validator.checkPath(pathString,
        ResourceType.FILE, null);
    assertEquals(new File(pathString), checkedFile.file);
    assertNull(checkedFile.reason);
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
    Validator.MaybeFile checkedFile = this.validator.checkPath(pathString,
        ResourceType.DIRECTORY, null);
    assertEquals(new File(pathString), checkedFile.file);
    assertNull(checkedFile.reason);
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
    Validator.MaybeFile checkedFile = this.validator.checkPath(pathString,
        ResourceType.DIRECTORY, null);
    assertEquals(new File(pathString), checkedFile.file);
    assertNull(checkedFile.reason);
  }
}
