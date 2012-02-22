package de.bastiankrol.startexplorer.crossplatform;

import java.io.File;

abstract class AbstractRuntimExecCallsTestRunner
{
  private static final String TEST_DIRECTORY = new File("misc/release-notes")
      .getAbsolutePath();
  private static final String TEST_FILE = new File(
      "misc/release-notes/README.txt").getAbsolutePath();

  AbstractRuntimeExecCalls calls;

  void runTests()
  {
    this.before();
    this.runAllTests();
  }

  abstract void before();

  void runAllTests()
  {
    this.calls.startFileManagerForFile(new File(TEST_DIRECTORY), false);
    this.calls.startFileManagerForFile(new File(TEST_FILE), true);
    this.calls.startShellForFile(new File(TEST_DIRECTORY));
    this.calls.startSystemApplicationForFile(new File(TEST_FILE));
  }
}
