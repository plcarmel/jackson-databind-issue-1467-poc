package com.fasterxml.jackson.databind1467poc.example.steps;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind1467poc.example.instances.InstanceExpectToken;
import com.fasterxml.jackson.databind1467poc.theory.DeserializationStep;
import com.fasterxml.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.fasterxml.jackson.databind1467poc.theory.False;

import java.util.List;

public class StepExpectToken extends StepHavingUnmanagedDependencies<False> {

  private final JsonToken expectedTokenKind;
  private final Object expectedTokenValue;
  private final boolean useTokenValue;

  public StepExpectToken(
    JsonToken expectedTokenKind,
    Object expectedTokenValue,
    List<DeserializationStep<?>> dependencies
  ) {
    super(dependencies);
    this.expectedTokenKind = expectedTokenKind;
    this.expectedTokenValue = expectedTokenValue;
    useTokenValue = true;
  }

  public StepExpectToken(
    JsonToken expectedTokenKind,
    List<DeserializationStep<?>> dependencies
  ) {
    super(dependencies);
    this.expectedTokenKind = expectedTokenKind;
    this.expectedTokenValue = null;
    useTokenValue = false;
  }
  @Override
  public DeserializationStepInstance<False> instantiated() {
    return useTokenValue
      ? new InstanceExpectToken(expectedTokenKind, expectedTokenValue, instantiatedDependencies())
      : new InstanceExpectToken(expectedTokenKind, instantiatedDependencies());
  }
}
