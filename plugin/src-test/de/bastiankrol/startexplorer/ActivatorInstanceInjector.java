package de.bastiankrol.startexplorer;

/**
 * Used to inject a default Activator instance for tests.
 * 
 * @author bastian.krol
 */
public class ActivatorInstanceInjector
{
  /**
   * Used to inject a default Activator instance for tests.
   * 
   * @param instance the instance to inject
   */
  public static void injectDefaultInstanceForTest(Activator instance)
  {
    Activator.injectDefaultInstanceForTest(instance);
  }
}
