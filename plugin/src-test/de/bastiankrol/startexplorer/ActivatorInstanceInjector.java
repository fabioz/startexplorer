package de.bastiankrol.startexplorer;

import static org.mockito.Mockito.*;

/**
 * Used to inject a default Activator instance for tests. Makes some package
 * private members publicly accessible.
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

  /**
   * Use this to provide a plug-in context if the activator is not mocked.
   * 
   * @param activator the {@link Activator} instance
   * @param pluginContext the {@link PluginContext} instance
   */
  public static void setPluginContextInActivator(Activator activator,
      PluginContext pluginContext)
  {
    activator.context = pluginContext;
  }

  /**
   * Use this to provide a plug-in context if the activator is mocked.
   * 
   * @param activator the {@link Activator} instance
   * @param pluginContext the {@link PluginContext} instance
   */
  public static void stubGetContextCall(Activator activator,
      PluginContext pluginContext)
  {
    when(activator.getContext()).thenReturn(pluginContext);
  }
}
