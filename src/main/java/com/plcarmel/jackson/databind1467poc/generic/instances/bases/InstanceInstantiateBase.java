package com.plcarmel.jackson.databind1467poc.generic.instances.bases;

import com.plcarmel.jackson.databind1467poc.generic.groups.instances.InstanceGroup;
import com.plcarmel.jackson.databind1467poc.generic.groups.instances.InstanceGroupMany;
import com.plcarmel.jackson.databind1467poc.generic.groups.instances.mixins.CleanMixin;
import com.plcarmel.jackson.databind1467poc.generic.groups.mixins.GetDependenciesMixin;
import com.plcarmel.jackson.databind1467poc.generic.groups.instances.mixins.RemoveDependencyFromListMixin;
import com.plcarmel.jackson.databind1467poc.generic.instances.mixins.NoTokenMixin;
import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

public abstract class InstanceInstantiateBase<TInput, TResult>
  extends
  InstanceUnmanagedBase<TInput, TResult>
  implements
  GetDependenciesMixin<InstanceGroup<TInput>, StepInstance<TInput, ?>>,
  RemoveDependencyFromListMixin<TInput, TResult>,
  CleanMixin<TInput, TResult>,
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
