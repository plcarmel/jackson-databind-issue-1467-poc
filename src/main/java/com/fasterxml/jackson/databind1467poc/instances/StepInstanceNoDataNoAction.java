package com.fasterxml.jackson.databind1467poc.instances;

import com.fasterxml.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.List;

public abstract class StepInstanceNoDataNoAction extends StepInstanceNoData {

  public StepInstanceNoDataNoAction(List<DeserializationStepInstance<?>> dependencies) {
    super(dependencies);
  }

  @Override
  public final void executeLocally() {
    // NOP
  }

}
