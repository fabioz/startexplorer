package de.bastiankrol.startexplorer.preferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CommandConfigList extends ArrayList<CommandConfig>
{

  private static final long serialVersionUID = 1L;

  private Set<ICommandConfigListViewer> changeListeners = new HashSet<ICommandConfigListViewer>();

  /**
   * Constructor
   */
  public CommandConfigList()
  {
    super();
  }

  public CommandConfigList(Collection<? extends CommandConfig> c)
  {
    super(c);
  }

  public CommandConfigList(int initialCapacity)
  {
    super(initialCapacity);
  }

  public void notifyListenersAdd(CommandConfig commandConfig)
  {
    for (ICommandConfigListViewer listener : this.changeListeners)
    {
      listener.addCommandConfig(commandConfig);
    }
  }

  public void notifyListenersRemove(CommandConfig commandConfig)
  {
    for (ICommandConfigListViewer listener : this.changeListeners)
    {
      listener.removeCommandConfig(commandConfig);
    }
  }

  public void notifyListenersUpdate(CommandConfig commandConfig)
  {
    for (ICommandConfigListViewer listener : this.changeListeners)
    {
      listener.updateCommandConfig(commandConfig);
    }
  }

  /**
   * @param viewer
   */
  public void removeChangeListener(ICommandConfigListViewer viewer)
  {
    changeListeners.remove(viewer);
  }

  /**
   * @param viewer
   */
  public void addChangeListener(ICommandConfigListViewer viewer)
  {
    changeListeners.add(viewer);
  }

  @Override
  public boolean add(CommandConfig commandConfig)
  {
    boolean b = super.add(commandConfig);
    this.notifyListenersAdd(commandConfig);
    return b;
  }

  @Override
  public void add(int index, CommandConfig commandConfig)
  {
    super.add(index, commandConfig);
    this.notifyListenersAdd(commandConfig);
  }

  @Override
  public boolean addAll(Collection<? extends CommandConfig> commandConfigCollection)
  {
    boolean b = super.addAll(commandConfigCollection);
    for (CommandConfig commandConfig : commandConfigCollection)
    {
      this.notifyListenersAdd(commandConfig);
    }
    return b;
  }

  @Override
  public boolean addAll(int index,
      Collection<? extends CommandConfig> commandConfigCollection)
  {
    boolean b = super.addAll(index, commandConfigCollection);
    for (CommandConfig commandConfig : commandConfigCollection)
    {
      this.notifyListenersAdd(commandConfig);
    }
    return b;
  }

  @Override
  public void clear()
  {
    for (CommandConfig commandConfig : this)
    {
      this.notifyListenersRemove(commandConfig);
    }
    super.clear();
  }

  @Override
  public CommandConfig remove(int index)
  {
    CommandConfig removed = super.remove(index);
    this.notifyListenersRemove(removed);
    return removed;
  }

  @Override
  public boolean remove(Object o)
  {
    boolean b = super.remove(o);
    if (b)
    {
      this.notifyListenersRemove((CommandConfig) o);
    }
    return b;
  }

  @Override
  public CommandConfig set(int index, CommandConfig newCommandConfig)
  {
    CommandConfig oldCommandConfig = super.set(index, newCommandConfig);
    this.notifyListenersRemove(newCommandConfig);
    return oldCommandConfig;

  }

  @Override
  public boolean removeAll(Collection<?> commandConfigCollection)
  {
    for (Object object : commandConfigCollection)
    {
      this.notifyListenersRemove((CommandConfig) object);
    }
    return super.removeAll(commandConfigCollection);
  }

  @Override
  public boolean retainAll(Collection<?> commandConfigCollection)
  {
    if (false)
    {
      commandConfigCollection.getClass();
    }
    throw new RuntimeException("Not implemented");
  }

}
