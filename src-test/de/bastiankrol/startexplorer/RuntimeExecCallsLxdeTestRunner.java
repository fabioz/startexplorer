package de.bastiankrol.startexplorer;


public class RuntimeExecCallsLxdeTestRunner extends
    AbstractExecCallsTestRunner
{

  public static void main(String[] args)
  {
    new RuntimeExecCallsLxdeTestRunner().runTests();
  }

  void before()
  {
    this.calls = new RuntimeExecCallsLxde(new RuntimeExecDelegateForTests());
  }
}
