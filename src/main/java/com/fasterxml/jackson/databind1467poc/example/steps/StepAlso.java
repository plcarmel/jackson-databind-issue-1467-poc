package com.fasterxml.jackson.databind1467poc.example.steps;

import com.fasterxml.jackson.databind1467poc.theory.DeserializationStep;
import com.fasterxml.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.List;

public class StepAlso<T> extends StepHavingUnmanagedDependencies<T> {

  private final DeserializationStep<T> mainDependency;

  public StepAlso(DeserializationStep<T> mainDependency, List<DeserializationStep<?>> unmanagedDependencies) {
    super(unmanagedDependencies);
    this.mainDependency = mainDependency;
  }

  @Override
  public DeserializationStepInstance<T> instantiated() {
    return null;
  }
}
