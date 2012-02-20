package de.bastiankrol.startexplorer;


public class RuntimeExecCallsKdeTestRunner extends
    AbstractExecCallsTestRunner
{

  public static void main(String[] args)
  {
    new RuntimeExecCallsKdeTestRunner().runTests();
  }

  void before()
  {
    this.calls = new RuntimeExecCallsKde(new RuntimeExecDelegateForTests());
  }
}
