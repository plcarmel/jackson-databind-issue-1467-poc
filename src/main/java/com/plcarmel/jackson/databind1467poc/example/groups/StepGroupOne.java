package com.plcarmel.jackson.databind1467poc.example.groups;

import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

public class StepGroupOne<T> extends GroupOne<DeserializationStep<T>, DeserializationStep<?>> {

  public StepGroupOne(DeserializationStep<T> deserializationStep) {
    super(deserializationStep);
  }

  public DeserializationStepInstance<T> instantiatedMain(DeserializationStep.InstanceFactory factory) {
    return factory.instantiate(getMain());
  }
}
