package de.bastiankrol.startexplorer;

import static de.bastiankrol.startexplorer.RuntimeExecCalls.*;
import static org.easymock.EasyMock.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class
 * 
 * @author Bastian Krol
 * @version $Revision:$ $Date:$ $Author:$
 */
public class RuntimeExecCallsTest
{
  private RuntimeExecCalls runtimeExecCalls;
  private IRuntimeExecDelegate mockRuntimeExecDelegate;
  private File file;
  private List<File> fileList;

  /**
   * JUnite before
   */
  @Before
  public void setUp()
  {
    this.file = new File("C:\\file\\to\\resource");

    this.fileList = new ArrayList<File>();
    this.fileList.add(new File("C:\\file\\to\\resource"));
    this.fileList.add(new File("/file/to/another/resource"));
    this.fileList
        .add(new File(
            "some weird string (RuntimeExecCalls doesn't check if it's a valid file"));

    this.runtimeExecCalls = new RuntimeExecCalls();
    this.mockRuntimeExecDelegate = createStrictMock(IRuntimeExecDelegate.class);
    this.runtimeExecCalls.setRuntimeExecDelegate(mockRuntimeExecDelegate);
  }

  /**
   * JUnit test method
   */
  @Test
  public void testStartWindowsExplorerForFile()
  {
    this.mockRuntimeExecDelegate.exec("Explorer.exe /e,\""
        + this.file.getAbsolutePath() + "\"");
    replay(this.mockRuntimeExecDelegate);
    this.runtimeExecCalls.startWindowsExplorerForFile(this.file);
    verify(this.mockRuntimeExecDelegate);
  }

  /**
   * JUnit test method
   */
  @Test
  public void testStartWindowsExplorerForFileList()
  {
    for (File fileFromList : this.fileList)
    {
      this.mockRuntimeExecDelegate.exec("Explorer.exe /e,\""
          + fileFromList.getAbsolutePath() + "\"");
    }
    replay(this.mockRuntimeExecDelegate);
    this.runtimeExecCalls.startWindowsExplorerForFileList(this.fileList);
    verify(this.mockRuntimeExecDelegate);
  }

  /**
   * JUnit test method
   */
  @Test
  public void testStartWindowsSystemApplicationForFile()
  {
    this.mockRuntimeExecDelegate.exec("cmd.exe /c \""
        + this.file.getAbsolutePath() + "\"");
    replay(this.mockRuntimeExecDelegate);
    this.runtimeExecCalls.startWindowsSystemApplicationForFile(this.file);
    verify(this.mockRuntimeExecDelegate);
  }

  /**
   * JUnit test method
   */
  @Test
  public void testStartWindowsSystemApplicationForFileList()
  {
    for (File fileFromList : this.fileList)
    {
      this.mockRuntimeExecDelegate.exec("cmd.exe /c \""
          + fileFromList.getAbsolutePath() + "\"");
    }
    replay(this.mockRuntimeExecDelegate);
    this.runtimeExecCalls
        .startWindowsSystemApplicationForFileList(this.fileList);
    verify(this.mockRuntimeExecDelegate);
  }

  /**
   * JUnit test method
   */
  @Test
  public void testStartCmdExeForFile()
  {
    this.mockRuntimeExecDelegate.exec("cmd.exe /c start /d \""
        + this.file.getAbsolutePath() + "\"");
    replay(this.mockRuntimeExecDelegate);
    this.runtimeExecCalls.startCmdExeForFile(this.file);
    verify(this.mockRuntimeExecDelegate);
  }

  /**
   * JUnit test method
   */
  @Test
  public void testStartCmdExeForFileList()
  {
    for (File fileFromList : this.fileList)
    {
      this.mockRuntimeExecDelegate.exec("cmd.exe /c start /d \""
          + fileFromList.getAbsolutePath() + "\"");
    }
    replay(this.mockRuntimeExecDelegate);
    this.runtimeExecCalls.startCmdExeForFileList(this.fileList);
    verify(this.mockRuntimeExecDelegate);
  }

  /**
   * JUnit test method
   */
  @Test
  public void testStartCustomCommandForFile()
  {
    String customCommand =
        "parent: " + RESOURCE_PARENT_VAR + " name: " + RESOURCE_NAME_VAR
            + " complete path: " + RESOURCE_PATH_VAR;
    String expectedCall =
        "parent: \"" + this.file.getParentFile().getAbsolutePath() + "\" name: \""
            + this.file.getName() + "\" complete path: \""
            + this.file.getAbsolutePath() + "\"";
    this.mockRuntimeExecDelegate.exec(expectedCall);
    replay(this.mockRuntimeExecDelegate);
    this.runtimeExecCalls.startCustomCommandForFile(customCommand, this.file);
    verify(this.mockRuntimeExecDelegate);
  }
}
