package com.plcarmel.jackson.databind1467poc.generic.instances;

import com.plcarmel.jackson.databind1467poc.generic.groups.*;
import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

public abstract class InstanceInstantiateBase<TInput, TResult>
  extends
    InstanceHavingUnmanagedDependencies<TInput, TResult>
  implements
    GetDependenciesMixin<InstanceGroup<TInput>, StepInstance<TInput, ?>>,
    RemoveDependencyFromListMixin<TInput, TResult>,
    CollapseMixin<TInput, TResult>,
    NoTokenMixin<TInput, TResult>
{
  protected TResult data;

  public InstanceInstantiateBase(InstanceGroupMany<TInput> unmanaged) {
    super(unmanaged);
  }

  @Override
  public boolean isOptional() {
    return false;
  }

  @Override
  public TResult getData() {
    return data;
  }

  @Override
  public Boolean hasBeenExecuted() {
    return getData() != null;
  }
}
