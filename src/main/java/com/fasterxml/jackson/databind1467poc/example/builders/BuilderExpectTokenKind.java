package com.fasterxml.jackson.databind1467poc.example.builders;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind1467poc.example.steps.StepExpectToken;
import com.fasterxml.jackson.databind1467poc.theory.DeserializationStep;
import com.fasterxml.jackson.databind1467poc.theory.False;

public class BuilderExpectTokenKind extends BuilderHavingUnmanagedDependencies<False> {

  private final JsonToken expectedToken;

  public BuilderExpectTokenKind(JsonToken expectedToken) {
    this.expectedToken = expectedToken;
  }

  @Override
  public DeserializationStep<False> build() {
    return new StepExpectToken(expectedToken, getDependencies());
  }
}
