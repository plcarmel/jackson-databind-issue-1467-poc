package com.plcarmel.jackson.databind1467poc.example.groups;

import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;

public class StepGroupOne<T> extends GroupOne<DeserializationStep<T>, DeserializationStep<?>> {

  public StepGroupOne(DeserializationStep<T> deserializationStep) {
    super(deserializationStep);
  }

  public InstanceGroupOne<T> instantiatedMain(DeserializationStep.InstanceFactory factory) {
    return new InstanceGroupOne<>(factory.instantiate(getMain()));
  }
}
