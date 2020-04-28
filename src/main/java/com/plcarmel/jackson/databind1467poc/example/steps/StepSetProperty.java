package com.plcarmel.jackson.databind1467poc.example.steps;

import com.plcarmel.jackson.databind1467poc.example.instances.InstanceSetProperty;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.NoData;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;

import java.util.List;

public class StepSetProperty<TClass, TProperty> extends StepHavingUnmanagedDependencies<NoData> {

  private final PropertyConfiguration<? extends TProperty> propertyConfiguration;
  private final DeserializationStep<TClass> instantiationStep;
  private final DeserializationStep<? extends TProperty> propertyDeserializationStep;

  public StepSetProperty(
    PropertyConfiguration<? extends TProperty> propertyConfiguration,
    DeserializationStep<TClass> instantiationStep,
    DeserializationStep<? extends TProperty> propertyDeserializationStep,
    List<DeserializationStep<?>> otherDependencies
  ) {
    super(otherDependencies);
    this.propertyConfiguration = propertyConfiguration;
    this.instantiationStep = instantiationStep;
    this.propertyDeserializationStep = propertyDeserializationStep;
  }

  @Override
  public DeserializationStepInstance<NoData> instantiated(InstanceFactory instanceFactory) {
    return new InstanceSetProperty<>(
      propertyConfiguration,
      instanceFactory.instantiate(instantiationStep),
      instanceFactory.instantiate(propertyDeserializationStep),
      instantiatedDependencies(instanceFactory)
    );
  }

}
