package com.plcarmel.jackson.databind1467poc.generic.instances;

import com.plcarmel.jackson.databind1467poc.generic.groups.instances.InstanceDependencyGroups;
import com.plcarmel.jackson.databind1467poc.generic.groups.instances.InstanceGroup;
import com.plcarmel.jackson.databind1467poc.generic.groups.instances.InstanceGroupMany;
import com.plcarmel.jackson.databind1467poc.generic.groups.instances.InstanceGroupOne;
import com.plcarmel.jackson.databind1467poc.generic.groups.instances.mixins.CleanMixin;
import com.plcarmel.jackson.databind1467poc.generic.groups.mixins.GetDependenciesMixin;
import com.plcarmel.jackson.databind1467poc.generic.groups.instances.mixins.RemoveDependencyFromListMixin;
import com.plcarmel.jackson.databind1467poc.generic.instances.mixins.NoTokenMixin;
import com.plcarmel.jackson.databind1467poc.generic.instances.mixins.NonExecutableMixin;
import com.plcarmel.jackson.databind1467poc.generic.instances.bases.InstanceUnmanagedBase;
import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

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
