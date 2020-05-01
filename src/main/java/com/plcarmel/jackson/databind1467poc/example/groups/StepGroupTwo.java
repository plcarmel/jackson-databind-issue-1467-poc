package com.plcarmel.jackson.databind1467poc.example.groups;

import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

public class StepGroupTwo<TFirst, TSecond>
  extends GroupTwo<
    DeserializationStep<TFirst>,
    DeserializationStep<TSecond>,
    DeserializationStep<?>
  >
{
  public StepGroupTwo(DeserializationStep<TFirst> first, DeserializationStep<TSecond> second) {
    super(first, second);
  }

  public DeserializationStepInstance<TFirst> instantiatedFirst(DeserializationStep.InstanceFactory factory) {
    return factory.instantiate(getFirst());
  }

  public DeserializationStepInstance<TSecond> instantiatedSecond(DeserializationStep.InstanceFactory factory) {
    return factory.instantiate(getSecond());
  }
}
