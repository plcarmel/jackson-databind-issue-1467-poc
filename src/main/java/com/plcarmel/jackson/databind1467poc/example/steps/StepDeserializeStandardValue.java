package com.plcarmel.jackson.databind1467poc.example.steps;

import com.plcarmel.jackson.databind1467poc.example.instances.InstanceAlso;
import com.plcarmel.jackson.databind1467poc.example.instances.InstanceDeserializeStandardValue;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;

import java.util.List;
import java.util.Map;

public class StepDeserializeStandardValue<T> extends StepHavingUnmanagedDependencies<T> {

  private final PropertyConfiguration<?, T> conf;

  public StepDeserializeStandardValue(
    PropertyConfiguration<?, T> conf,
    List<DeserializationStep<?>> dependencies
  ) {
    super(dependencies);
    this.conf = conf;
  }

  @Override
  public DeserializationStepInstance<T> instantiated(
    Map<DeserializationStep<?>, DeserializationStepInstance<?>> alreadyInstantiated
  ) {
    //noinspection unchecked
    DeserializationStepInstance<T> instance = (DeserializationStepInstance<T>) alreadyInstantiated.get(this);
    if (instance != null) return instance;
    instance = new InstanceDeserializeStandardValue<>(conf, instantiatedDependencies(alreadyInstantiated));
    alreadyInstantiated.put(this, instance);
    return instance;
  }

}
