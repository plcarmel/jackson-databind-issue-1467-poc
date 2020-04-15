package com.fasterxml.jackson.databind1467poc.instances;

import com.fasterxml.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.List;

public abstract class StepInstanceNoAction<T> extends StepInstanceHavingDependencies<T> {

  public StepInstanceNoAction(List<DeserializationStepInstance<?>> dependencies) {
    super(dependencies);
  }

  @Override
  public void executeLocally() {
    // NOP
  }

}
