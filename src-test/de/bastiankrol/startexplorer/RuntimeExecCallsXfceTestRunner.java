package de.bastiankrol.startexplorer;


public class RuntimeExecCallsXfceTestRunner extends
    AbstractExecCallsTestRunner
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
