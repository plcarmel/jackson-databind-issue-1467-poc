package com.plcarmel.steps.generic.instances;

import com.plcarmel.steps.generic.groups.instances.InstanceDependencyGroups;
import com.plcarmel.steps.generic.groups.instances.InstanceGroup;
import com.plcarmel.steps.generic.groups.instances.InstanceGroupMany;
import com.plcarmel.steps.generic.groups.instances.InstanceGroupOne;
import com.plcarmel.steps.generic.groups.instances.mixins.CleanMixin;
import com.plcarmel.steps.generic.groups.mixins.GetDependenciesMixin;
import com.plcarmel.steps.generic.groups.instances.mixins.RemoveDependencyFromListMixin;
import com.plcarmel.steps.generic.instances.mixins.NoTokenMixin;
import com.plcarmel.steps.generic.instances.mixins.NonExecutableMixin;
import com.plcarmel.steps.generic.instances.bases.InstanceUnmanagedBase;
import com.plcarmel.steps.theory.StepInstance;

import java.util.stream.Stream;

public final class InstanceAlso<TInput, TResult>
  extends
  InstanceUnmanagedBase<TInput, TResult>
  implements
  GetDependenciesMixin<InstanceGroup<TInput>, StepInstance<TInput, ?>>,
  RemoveDependencyFromListMixin<TInput, TResult>,
  CleanMixin<TInput, TResult>,
  NoTokenMixin<TInput, TResult>,
  NonExecutableMixin<TInput, TResult>
{
  private final InstanceGroupOne<TInput, TResult> managed;

  public InstanceAlso(
    InstanceGroupOne<TInput, TResult> managed,
    InstanceGroupMany<TInput> unmanaged
  ) {
    super(unmanaged);
    this.managed = managed;
  }

  @Override
  public boolean isOptional() {
    return managed.get().isOptional();
  }

  @Override
  public boolean hasTokenBeenReceived() {
    return getData() != null;
  }

  @Override
  public TResult getData() {
    return managed.get().getData();
  }

  @Override
  public InstanceDependencyGroups<TInput> getDependencyGroups() {
    return new InstanceDependencyGroups<>(Stream.of(managed, unmanaged));
  }

}
