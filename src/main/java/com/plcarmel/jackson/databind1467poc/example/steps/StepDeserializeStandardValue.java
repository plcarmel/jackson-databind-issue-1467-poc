package com.plcarmel.jackson.databind1467poc.example.steps;

import com.plcarmel.jackson.databind1467poc.example.instances.InstanceDeserializeStandardValue;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;
import com.plcarmel.jackson.databind1467poc.theory.TypeConfiguration;

import java.util.List;

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
  public DeserializationStepInstance<T> instantiated() {
    return new InstanceDeserializeStandardValue<>(conf, instantiatedDependencies());
  }

}
