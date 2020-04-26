package com.plcarmel.jackson.databind1467poc.example.steps;

import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public abstract class StepHavingUnmanagedDependencies<T> implements DeserializationStep<T> {

  private final List<DeserializationStep<?>> dependencies;

  protected StepHavingUnmanagedDependencies(List<DeserializationStep<?>> dependencies) {
    this.dependencies = dependencies;
  }

  protected final List<DeserializationStepInstance<?>> instantiatedDependencies(
    Map<DeserializationStep<?>, DeserializationStepInstance<?>> alreadyInstantiated
  ) {
    return getDependencies()
      .stream()
      .map((DeserializationStep<?> deserializationStep) -> deserializationStep.instantiated(alreadyInstantiated))
      .collect(toList());
  }

  @Override
  public List<DeserializationStep<?>> getDependencies() {
    return dependencies;
  }
}
