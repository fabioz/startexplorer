package de.bastiankrol.startexplorer.crossplatform;

import static de.bastiankrol.startexplorer.variables.VariableManager.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import de.bastiankrol.startexplorer.Activator;
import de.bastiankrol.startexplorer.ActivatorInstanceInjector;
import de.bastiankrol.startexplorer.PluginContext;
import de.bastiankrol.startexplorer.variables.VariableManager;

/**
 * Test class
 * 
 * @author Bastian Krol
 */
public class RuntimeExecCallsTest
{
  private RuntimeExecCallsWindows runtimeExecCalls;
  private String path;
  private String resourceName;
  private String extension;
  private File file;
  private List<File> fileList;

  @Mock
  private IRuntimeExecDelegate runtimeExecDelegateMock;

  @Mock
  private Activator activatorMock;

  @Mock
  private PluginContext pluginContextMock;

  @Mock
  private VariableManager variableManagerMock;

  /**
   * JUnite before
   */
  @Before
  public void setUp()
  {
    MockitoAnnotations.initMocks(this);

    this.path = "/path/to";
    this.resourceName = "resource";
    this.extension = "txt";
    this.file = new File(this.path + this.resourceName + "." + this.extension);

    this.fileList = new ArrayList<File>();
    this.fileList.add(new File("C:\\file\\to\\resource"));
    this.fileList.add(new File("/file/to/another/resource"));
    this.fileList
        .add(new File(
            "some weird string (RuntimeExecCalls doesn't check if it's a valid file"));

    this.runtimeExecCalls = new RuntimeExecCallsWindows();
    this.runtimeExecCalls.setRuntimeExecDelegate(this.runtimeExecDelegateMock);
  }

  /**
   * JUnit test method
   */
  @Test
  public void testStartWindowsExplorerForFile()
  {
    this.runtimeExecCalls.startFileManagerForFile(this.file, false);
    verify(this.runtimeExecDelegateMock).exec(
        new String[] { "Explorer.exe /e,\"" + this.file.getAbsolutePath()
            + "\"" }, null, true);
  }

  /**
   * JUnit test method
   */
  @Test
  public void testSelectFileInWindowsExplorer()
  {
    File fileMock = mock(File.class);
    when(fileMock.isFile()).thenReturn(true);
    when(fileMock.getAbsolutePath()).thenReturn("C:\\file\\to\\resource.txt");
    this.runtimeExecCalls.startFileManagerForFile(fileMock, true);
    verify(this.runtimeExecDelegateMock).exec(
        new String[] { "Explorer.exe /select,\"" + fileMock.getAbsolutePath()
            + "\"" }, null, true);
  }

  /**
   * JUnit test method
   */
  @Test
  public void testStartWindowsExplorerForFileList()
  {
    this.runtimeExecCalls.startFileManagerForFileList(this.fileList, false);
    for (File fileFromList : this.fileList)
    {
      verify(this.runtimeExecDelegateMock).exec(
          new String[] { "Explorer.exe /e,\"" + fileFromList.getAbsolutePath()
              + "\"" }, null, true);
    }
  }

  /**
   * JUnit test method
   */
  @Test
  public void testStartWindowsSystemApplicationForFile()
  {
    this.runtimeExecCalls.startSystemApplicationForFile(this.file);
    verify(this.runtimeExecDelegateMock).exec(
        new String[] { "cmd.exe /c \"" + this.file.getAbsolutePath() + "\"" },
        null, true);
  }

  /**
   * JUnit test method
   */
  @Test
  public void testStartWindowsSystemApplicationForFileList()
  {
    this.runtimeExecCalls.startSystemApplicationForFileList(this.fileList);
    for (File fileFromList : this.fileList)
    {
      verify(this.runtimeExecDelegateMock).exec(
          new String[] { "cmd.exe /c \"" + fileFromList.getAbsolutePath()
              + "\"" }, null, true);
    }
  }

  /**
   * JUnit test method
   */
  @Test
  public void testStartCmdExeForFile()
  {
    this.runtimeExecCalls.startShellForFile(this.file);
    verify(this.runtimeExecDelegateMock).exec(
        new String[] { "cmd.exe /c start /d \"" + this.file.getAbsolutePath()
            + "\"" }, null, true);
  }

  /**
   * JUnit test method
   */
  @Test
  public void testStartCmdExeForFileList()
  {
    this.runtimeExecCalls.startShellForFileList(this.fileList);
    for (File fileFromList : this.fileList)
    {
      verify(this.runtimeExecDelegateMock).exec(
          new String[] { "cmd.exe /c start /d \""
              + fileFromList.getAbsolutePath() + "\"" }, null, true);
    }
  }

  /**
   * JUnit test method
   */
  @Test
  public void testStartCustomCommandForFile()
  {
    // Prepare mocks that are only used for this test
    ActivatorInstanceInjector.injectDefaultInstanceForTest(this.activatorMock);
    ActivatorInstanceInjector.stubGetContextCall(this.activatorMock,
        this.pluginContextMock);
    when(this.pluginContextMock.getVariableManager()).thenReturn(
        this.variableManagerMock);

    final String[] cmdArray = new String[] { "parent: " + RESOURCE_PARENT_VAR, //
        "name: " + RESOURCE_NAME_VAR, //
        "complete path: " + RESOURCE_PATH_VAR, //
        "name without extension: " + RESOURCE_NAME_WIHTOUT_EXTENSION_VAR, //
        "extension: " + RESOURCE_EXTENSION_VAR //
    };
    final String[] cmdArrayWithVariablesReplaced = new String[] {//
    "parent: \"" + this.file.getParentFile().getAbsolutePath() + "\"", //
        "name: \"" + this.file.getName() + "\"", //
        "complete path: \"" + this.file.getAbsolutePath() + "\"", //
        "name without extension: " + this.resourceName, //
        "extension: " + this.extension //
    };
    doAnswer(new Answer<Object>()
    {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable
      {
        // String[] cmdArray = (String[]) invocation.getArguments()[0];
        for (int i = 0; i < cmdArray.length; i++)
        {
          cmdArray[i] = cmdArrayWithVariablesReplaced[i];
        }
        return null;
      }
    }).when(this.variableManagerMock).replaceAllVariablesInCommand(cmdArray,
        this.file, true, false);

    this.runtimeExecCalls.startCustomCommandForFile(cmdArray, this.file);
    verify(this.runtimeExecDelegateMock).exec(cmdArrayWithVariablesReplaced,
        null, true);
  }
}
