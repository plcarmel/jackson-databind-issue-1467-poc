package com.fasterxml.jackson.databind1467poc.example.steps;

import com.fasterxml.jackson.databind1467poc.theory.DeserializationStep;
import com.fasterxml.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.fasterxml.jackson.databind1467poc.theory.TypeConfiguration;

import java.util.List;

public class StepInstanciateUsingDefaultConstructor<T> extends StepHavingUnmanagedDependencies<T> {

  private final TypeConfiguration<T> conf;

  public StepInstanciateUsingDefaultConstructor(TypeConfiguration<T> conf, List<DeserializationStep<?>> dependencies) {
    super(dependencies);
    this.conf = conf;
  }

  @Override
  public DeserializationStepInstance<T> instantiated() {
    return null;
  }
}
