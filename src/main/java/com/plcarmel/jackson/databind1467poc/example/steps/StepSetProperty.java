package com.plcarmel.jackson.databind1467poc.example.steps;

import com.plcarmel.jackson.databind1467poc.example.groups.*;
import com.plcarmel.jackson.databind1467poc.example.instances.InstanceSetProperty;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.NoData;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;

import java.util.stream.Stream;

public class StepSetProperty<
  TClass,
  TProperty
> implements DeserializationStep<NoData>, HasDependencyGroupsMixin<DeserializationStep<?>>
{
  private final PropertyConfiguration<? extends TProperty> propertyConfiguration;
  private final StepGroupMany unmanaged;
  private final GroupTwo<DeserializationStep<TClass>, DeserializationStep<? extends TProperty>, DeserializationStep<?>>
    managed;

  public StepSetProperty(
    PropertyConfiguration<? extends TProperty> propertyConfiguration,
    GroupTwo<DeserializationStep<TClass>, DeserializationStep<? extends TProperty>, DeserializationStep<?>>
      managed,
    StepGroupMany unmanaged
  ) {
    this.propertyConfiguration = propertyConfiguration;
    this.managed = managed;
    this.unmanaged = unmanaged;
  }

  @Override
  public DeserializationStepInstance<NoData> instantiated(DeserializationStep.InstanceFactory factory) {
    return new InstanceSetProperty<>(
      propertyConfiguration,
      factory.instantiate(managed.getFirst()),
      factory.instantiate(managed.getSecond()),
      unmanaged.instantiated(factory)
    );
  }

  @Override
  public DependencyGroups<DeserializationStep<?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(managed, unmanaged));
  }
}
