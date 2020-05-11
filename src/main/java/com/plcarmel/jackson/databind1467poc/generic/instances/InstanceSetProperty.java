package com.plcarmel.jackson.databind1467poc.generic.instances;

import com.plcarmel.jackson.databind1467poc.generic.groups.*;
import com.plcarmel.jackson.databind1467poc.theory.SettablePropertyConfiguration;
import com.plcarmel.jackson.databind1467poc.theory.StepInstance;
import com.plcarmel.jackson.databind1467poc.theory.NoData;

import java.util.stream.Stream;

public final class InstanceSetProperty<TInput, TClass, TValue>
  extends
    InstanceHavingUnmanagedDependencies<TInput, NoData>
  implements
    GetDependenciesMixin<InstanceGroup<TInput>, StepInstance<TInput,?>>,
    RemoveDependencyFromListMixin<TInput, NoData>,
    CollapseMixin<TInput, NoData>,
    NoDataMixin<TInput>,
    NoTokenMixin<TInput, NoData>
{
  private final SettablePropertyConfiguration<TClass, TValue> propertyConfiguration;
  private final InstanceGroupOne<TInput, ? extends TClass> instantiationStep;
  private final InstanceGroupOne<TInput, ? extends TValue> deserializationStep;
  private boolean hasBeenExecuted;

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

