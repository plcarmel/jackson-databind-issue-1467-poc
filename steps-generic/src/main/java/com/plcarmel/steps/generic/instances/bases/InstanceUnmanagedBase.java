package com.plcarmel.steps.generic.instances.bases;

import com.plcarmel.steps.generic.groups.instances.InstanceGroupMany;

public abstract class InstanceUnmanagedBase<TInput, TResult>
  extends InstanceParentsBase<TInput, TResult>
{
  protected final InstanceGroupMany<TInput> unmanaged;

  public InstanceUnmanagedBase(InstanceGroupMany<TInput> unmanaged) {
    this.unmanaged = unmanaged;
  }
}
