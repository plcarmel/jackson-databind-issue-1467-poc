package com.plcarmel.steps.generic.instances;

import com.plcarmel.steps.generic.groups.instances.InstanceDependencyGroups;
import com.plcarmel.steps.generic.groups.instances.InstanceGroup;
import com.plcarmel.steps.generic.groups.instances.InstanceGroupMany;
import com.plcarmel.steps.generic.groups.instances.InstanceGroupOne;
import com.plcarmel.steps.generic.groups.instances.mixins.CleanMixin;
import com.plcarmel.steps.generic.groups.mixins.GetDependenciesMixin;
import com.plcarmel.steps.generic.groups.instances.mixins.RemoveDependencyFromListMixin;
import com.plcarmel.steps.generic.instances.mixins.NoDataMixin;
import com.plcarmel.steps.generic.instances.mixins.NoTokenMixin;
import com.plcarmel.steps.generic.instances.bases.InstanceUnmanagedBase;
import com.plcarmel.steps.theory.SettablePropertyConfiguration;
import com.plcarmel.steps.theory.StepInstance;
import com.plcarmel.steps.theory.NoData;

import java.util.stream.Stream;

public final class InstanceSetProperty<TInput, TClass, TValue>
  extends
  InstanceUnmanagedBase<TInput, NoData>
  implements
  GetDependenciesMixin<InstanceGroup<TInput>, StepInstance<TInput,?>>,
  RemoveDependencyFromListMixin<TInput, NoData>,
  CleanMixin<TInput, NoData>,
  NoDataMixin<TInput>,
  NoTokenMixin<TInput, NoData>
{
  private final SettablePropertyConfiguration<TClass, TValue> propertyConfiguration;
  private final InstanceGroupOne<TInput, ? extends TClass> instantiationStep;
  private final InstanceGroupOne<TInput, ? extends TValue> deserializationStep;
  private boolean hasBeenExecuted;

  public SettablePropertyConfiguration<TClass, TValue> getPropertyConfiguration() {
    return propertyConfiguration;
  }

  public InstanceSetProperty(
    SettablePropertyConfiguration<TClass, TValue> propertyConfiguration,
    InstanceGroupOne<TInput, ? extends TClass> instantiationStep,
    InstanceGroupOne<TInput, ? extends TValue> deserializationStep,
    InstanceGroupMany<TInput> unmanaged
  ) {
    super(unmanaged);
    this.propertyConfiguration = propertyConfiguration;
    this.instantiationStep = instantiationStep;
    this.deserializationStep = deserializationStep;
  }

  @Override
  public InstanceDependencyGroups<TInput> getDependencyGroups() {
    return new InstanceDependencyGroups<>(Stream.of(instantiationStep, deserializationStep, unmanaged));
  }

  @Override
  public boolean isOptional() {
    return !propertyConfiguration.isRequired();
  }

  @Override
  public boolean isSubstitution() {
    return true;
  }

  @Override
  public boolean isTerminal() {
    return false;
  }

  @Override
  public Boolean isReadyToBeExecuted() {
    return instantiationStep.get().isDone() && deserializationStep.get().isDone();
  }

  @Override
  public Boolean hasBeenExecuted() {
    return hasBeenExecuted;
  }

  @Override
  public void execute() {
    propertyConfiguration.set(instantiationStep.get().getData(), deserializationStep.get().getData());
    hasBeenExecuted = true;
  }
}

