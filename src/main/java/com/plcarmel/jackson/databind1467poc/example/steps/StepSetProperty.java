package com.plcarmel.jackson.databind1467poc.example.steps;

import com.plcarmel.jackson.databind1467poc.example.instances.InstanceSetProperty;
import com.plcarmel.jackson.databind1467poc.example.structures.StructureSetProperty;
import com.plcarmel.jackson.databind1467poc.example.structures.StructureUnmanaged;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.NoData;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;

import java.util.List;

public class StepSetProperty<
  TClass,
  TProperty
> extends
  StructureSetProperty<
    DeserializationStep<TClass>,
    DeserializationStep<? extends TProperty>,
    DeserializationStep<?>
> implements
  StepUnmanagedMixin<NoData>
{
  PropertyConfiguration<? extends TProperty> propertyConfiguration;

  public StepSetProperty(
    PropertyConfiguration<? extends TProperty> propertyConfiguration,
    DeserializationStep<TClass> instantiationStep,
    DeserializationStep<? extends TProperty> propertyDeserializationStep,
    List<DeserializationStep<?>> otherDependencies
  ) {
    super(instantiationStep, propertyDeserializationStep, otherDependencies);
    this.propertyConfiguration = propertyConfiguration;
  }

  @Override
  public DeserializationStepInstance<NoData> instantiated(DeserializationStep.InstanceFactory instanceFactory) {
    return new InstanceSetProperty<>(
      propertyConfiguration,
      instanceFactory.instantiate(instantiationStep),
      instanceFactory.instantiate(deserializationStep),
      instantiatedDependencies(instanceFactory)
    );
  }

  @Override
  public StructureUnmanaged<DeserializationStep<?>> thisAsStructureUnmanaged() {
    return this;
  }
}
