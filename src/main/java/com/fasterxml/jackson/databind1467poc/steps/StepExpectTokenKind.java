package com.fasterxml.jackson.databind1467poc.steps;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind1467poc.instances.StepInstanceExpectTokenKind;
import com.fasterxml.jackson.databind1467poc.theory.DeserializationStep;
import com.fasterxml.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.fasterxml.jackson.databind1467poc.theory.False;

import java.util.List;

public class StepExpectTokenKind extends StepHavingDependencies<False> {

  private final JsonToken expectedTokenKind;

  protected StepExpectTokenKind(JsonToken tokenKind, List<DeserializationStep<?>> dependencies) {
    super(dependencies);
    expectedTokenKind = tokenKind;
  }

  @Override
  public DeserializationStepInstance<False> instantiated() {
    return new StepInstanceExpectTokenKind(expectedTokenKind, instantiatedDependencies());
  }
}
