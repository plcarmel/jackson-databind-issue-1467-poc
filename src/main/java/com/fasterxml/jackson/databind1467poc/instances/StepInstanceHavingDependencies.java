package com.fasterxml.jackson.databind1467poc.instances;

import com.fasterxml.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.List;

public abstract class StepInstanceHavingDependencies<T> implements DeserializationStepInstance<T> {

  private final List<DeserializationStepInstance<?>> dependencies;

  public StepInstanceHavingDependencies(List<DeserializationStepInstance<?>> dependencies) {
    this.dependencies = dependencies;
  }

  public List<DeserializationStepInstance<?>> getDependencies() {
    return dependencies;
  }
}
