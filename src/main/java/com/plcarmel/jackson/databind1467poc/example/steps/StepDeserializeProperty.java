package com.plcarmel.jackson.databind1467poc.example.steps;

import com.plcarmel.jackson.databind1467poc.example.instances.InstanceDeserializeProperty;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;

import java.util.List;

public class StepDeserializeProperty<TClass, TProperty> extends StepHavingUnmanagedDependencies<TProperty> {

  private final PropertyConfiguration<TClass, TProperty> propertyConfiguration;
  private final DeserializationStep<TProperty> valueDeserializationStep;

  public StepDeserializeProperty(
    PropertyConfiguration<TClass, TProperty> propertyConfiguration,
    DeserializationStep<TProperty> valueDeserializationStep,
    List<DeserializationStep<?>> otherDependencies
  ) {
    super(otherDependencies);
    this.propertyConfiguration = propertyConfiguration;
    this.valueDeserializationStep = valueDeserializationStep;
  }

  @Override
  public DeserializationStepInstance<TProperty> instantiated() {
    return new InstanceDeserializeProperty<>(
      propertyConfiguration,
      valueDeserializationStep.instantiated(),
      instantiatedDependencies()
    );
  }

}
