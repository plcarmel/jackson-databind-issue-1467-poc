package com.plcarmel.jackson.databind1467poc.generic.instances.bases;

import com.plcarmel.jackson.databind1467poc.generic.groups.instances.InstanceGroupMany;

public abstract class InstanceUnmanagedBase<TInput, TResult>
  extends InstanceParentsBase<TInput, TResult>
{
  protected final InstanceGroupMany<TInput> unmanaged;

  public InstanceUnmanagedBase(InstanceGroupMany<TInput> unmanaged) {
    this.unmanaged = unmanaged;
  }
}
