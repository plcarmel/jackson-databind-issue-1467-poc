package com.plcarmel.jackson.databind1467poc.example.steps;

import com.fasterxml.jackson.core.JsonToken;
import com.plcarmel.jackson.databind1467poc.example.instances.InstanceExpectToken;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.NoData;

import java.util.List;
import java.util.Map;

public class StepExpectToken extends StepHavingUnmanagedDependencies<NoData> {

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
  public DeserializationStepInstance<NoData> instantiated(
    Map<DeserializationStep<?>, DeserializationStepInstance<?>> alreadyInstantiated
  ) {
    //noinspection unchecked
    DeserializationStepInstance<NoData> instance = (DeserializationStepInstance<NoData>) alreadyInstantiated.get(this);
    if (instance != null) return instance;
    instance = useTokenValue
      ? new InstanceExpectToken(expectedTokenKind, expectedTokenValue, instantiatedDependencies(alreadyInstantiated))
      : new InstanceExpectToken(expectedTokenKind, instantiatedDependencies(alreadyInstantiated));
    alreadyInstantiated.put(this, instance);
    return instance;
  }
}
