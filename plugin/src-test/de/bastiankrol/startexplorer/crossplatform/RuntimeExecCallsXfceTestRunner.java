package de.bastiankrol.startexplorer.crossplatform;

public class RuntimeExecCallsXfceTestRunner extends
    AbstractRuntimExecCallsTestRunner
{

  public static void main(String[] args)
  {
    new RuntimeExecCallsXfceTestRunner().runTests();
  }

  void before()
  {
    this.calls = new RuntimeExecCallsXfce(new RuntimeExecDelegateForTests());
  }
}
