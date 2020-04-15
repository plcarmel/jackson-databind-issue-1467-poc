package com.fasterxml.jackson.databind1467poc.instances;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.io.IOException;
import java.util.List;

public class StepInstanceExpectTokenKind extends StepInstanceNoDataNoAction {

  private final JsonToken expectedTokenKind;
  private boolean isDone = false;

  public StepInstanceExpectTokenKind(JsonToken tokenKind, List<DeserializationStepInstance<?>> dependencies) {
    super(dependencies);
    expectedTokenKind = tokenKind;
  }

  @Override
  public boolean canHandle(JsonToken kind, String token) {
    return kind == expectedTokenKind;
  }

  @Override
  public void push(JsonToken kind, String token) throws IOException {
    assert kind == expectedTokenKind;
    isDone = true;
  }

  @Override
  public boolean isDone() {
    return isDone;
  }

  @Override
  public List<DeserializationStepInstance<?>> getDependencies() {
    return null;
  }

}
