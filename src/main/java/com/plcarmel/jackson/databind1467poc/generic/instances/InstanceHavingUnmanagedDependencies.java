package com.plcarmel.jackson.databind1467poc.generic.instances;

import com.plcarmel.jackson.databind1467poc.generic.groups.InstanceGroupMany;

public abstract class InstanceHavingUnmanagedDependencies<TInput, TResult>
  extends InstanceParentsImpl<TInput, TResult>
{
  protected final InstanceGroupMany<TInput> unmanaged;

  public InstanceHavingUnmanagedDependencies(InstanceGroupMany<TInput> unmanaged) {
    this.unmanaged = unmanaged;
  }
}
