package com.fasterxml.jackson.databind1467poc.steps;

import com.fasterxml.jackson.databind1467poc.theory.DeserializationStep;
import com.fasterxml.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.List;

import static java.util.stream.Collectors.toList;

public abstract class StepHavingDependencies<T> implements DeserializationStep<T> {

  private final List<DeserializationStep<?>> dependencies;

  protected StepHavingDependencies(List<DeserializationStep<?>> dependencies) {
    this.dependencies = dependencies;
  }

  protected final List<DeserializationStepInstance<?>> instantiatedDependencies() {
    return getDependencies().stream().map(DeserializationStep::instantiated).collect(toList());
  }

  @Override
  public List<DeserializationStep<?>> getDependencies() {
    return dependencies;
  }
}
