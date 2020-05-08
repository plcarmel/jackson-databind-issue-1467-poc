package com.plcarmel.jackson.databind1467poc.example.groups;

import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;

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

  public InstanceGroupTwo<TFirst, ? extends TSecond> instantiated(DeserializationStep.InstanceFactory factory) {
    return new InstanceGroupTwo<>(factory.instantiate(getFirst()), factory.instantiate(getSecond()));
  }
}
