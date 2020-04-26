package com.plcarmel.jackson.databind1467poc.example.steps;

import com.plcarmel.jackson.databind1467poc.example.instances.InstanceSetProperty;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.NoData;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;

import java.util.List;
import java.util.Map;

public class StepSetProperty<TClass, TProperty> extends StepHavingUnmanagedDependencies<NoData> {

  private final PropertyConfiguration<TClass, ? extends TProperty> propertyConfiguration;
  private final DeserializationStep<TClass> instantiationStep;
  private final DeserializationStep<? extends TProperty> propertyDeserializationStep;

  public StepSetProperty(
    PropertyConfiguration<TClass, ? extends TProperty> propertyConfiguration,
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
  public DeserializationStepInstance<NoData> instantiated(
    Map<DeserializationStep<?>, DeserializationStepInstance<?>> alreadyInstantiated
  ) {
    //noinspection unchecked
    DeserializationStepInstance<NoData> instance = (DeserializationStepInstance<NoData>) alreadyInstantiated.get(this);
    if (instance != null) return instance;
    instance = new InstanceSetProperty<>(
      propertyConfiguration,
      instantiationStep.instantiated(alreadyInstantiated),
      propertyDeserializationStep.instantiated(alreadyInstantiated),
      instantiatedDependencies(alreadyInstantiated)
    );
    alreadyInstantiated.put(this, instance);
    return instance;
  }

}
