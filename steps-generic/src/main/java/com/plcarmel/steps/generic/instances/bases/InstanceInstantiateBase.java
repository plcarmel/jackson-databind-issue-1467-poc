package com.plcarmel.steps.generic.instances.bases;

import com.plcarmel.steps.generic.groups.instances.InstanceGroup;
import com.plcarmel.steps.generic.groups.instances.InstanceGroupMany;
import com.plcarmel.steps.generic.groups.instances.mixins.CleanMixin;
import com.plcarmel.steps.generic.groups.mixins.GetDependenciesMixin;
import com.plcarmel.steps.generic.groups.instances.mixins.RemoveDependencyFromListMixin;
import com.plcarmel.steps.generic.instances.mixins.NoTokenMixin;
import com.plcarmel.steps.theory.StepInstance;

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
