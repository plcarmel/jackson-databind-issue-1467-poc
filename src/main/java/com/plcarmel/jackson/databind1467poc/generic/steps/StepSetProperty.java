package com.plcarmel.jackson.databind1467poc.generic.steps;

import com.plcarmel.jackson.databind1467poc.generic.groups.*;
import com.plcarmel.jackson.databind1467poc.generic.instances.InstanceSetProperty;
import com.plcarmel.jackson.databind1467poc.theory.*;

import java.util.stream.Stream;

public class StepSetProperty<TInput, TClass, TProperty>
  implements Step<TInput, NoData>, GetDependenciesMixin<Step<TInput, ?>>
{
  private final PropertyConfiguration<? extends TProperty> propertyConfiguration;
  private final StepGroupOne<TInput, TClass> instantiationStep;
  private final StepGroupOne<TInput, ? extends TProperty> deserializationStep;
  private final StepGroupMany<TInput> unmanaged;

  public StepSetProperty(
    PropertyConfiguration<? extends TProperty> propertyConfiguration,
    StepGroupOne<TInput, TClass> instantiationStep,
    StepGroupOne<TInput, ? extends TProperty> deserializationStep,
    StepGroupMany<TInput> unmanaged
  ) {
    this.propertyConfiguration = propertyConfiguration;
    this.instantiationStep = instantiationStep;
    this.deserializationStep = deserializationStep;
    this.unmanaged = unmanaged;
  }

  @Override
  public StepInstance<TInput, NoData> instantiated(InstanceFactory<TInput> factory) {
    return new InstanceSetProperty<>(
      propertyConfiguration,
      instantiationStep.instantiated(factory),
      deserializationStep.instantiated(factory),
      unmanaged.instantiated(factory)
    );
  }

  @Override
  public DependencyGroups<Step<TInput, ?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(instantiationStep, deserializationStep, unmanaged));
  }
}
