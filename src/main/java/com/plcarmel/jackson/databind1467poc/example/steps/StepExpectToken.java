package com.plcarmel.jackson.databind1467poc.example.steps;

import com.fasterxml.jackson.core.JsonToken;
import com.plcarmel.jackson.databind1467poc.example.groups.DependencyGroups;
import com.plcarmel.jackson.databind1467poc.example.groups.HasDependencyGroupsMixin;
import com.plcarmel.jackson.databind1467poc.example.groups.StepGroupMany;
import com.plcarmel.jackson.databind1467poc.example.instances.InstanceExpectToken;
import com.plcarmel.jackson.databind1467poc.example.groups.GroupMany;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.NoData;

import java.util.List;
import java.util.stream.Collectors;

public class StepExpectToken
  implements DeserializationStep<NoData>, HasDependencyGroupsMixin<DeserializationStep<?>>
{
  private final JsonToken expectedTokenKind;
  private final Object expectedTokenValue;
  private final boolean useTokenValue;
  private final StepGroupMany unmanagedDependencies;

  public StepExpectToken(
    JsonToken expectedTokenKind,
    Object expectedTokenValue,
    StepGroupMany unmanagedDependencies
  ) {
    this.expectedTokenKind = expectedTokenKind;
    this.expectedTokenValue = expectedTokenValue;
    this.unmanagedDependencies = unmanagedDependencies;
    useTokenValue = true;
  }

  public StepExpectToken(
    JsonToken expectedTokenKind,
    StepGroupMany unmanagedDependencies
  ) {
    this.expectedTokenKind = expectedTokenKind;
    this.expectedTokenValue = null;
    this.unmanagedDependencies = unmanagedDependencies;
    useTokenValue = false;
  }

  @Override
  public DeserializationStepInstance<NoData> instantiated(InstanceFactory factory) {
    return useTokenValue
      ? new InstanceExpectToken(
        expectedTokenKind,
        expectedTokenValue,
        unmanagedDependencies.instantiated(factory)
      )
      : new InstanceExpectToken(
        expectedTokenKind,
        unmanagedDependencies.instantiated(factory)
      );
  }

  @Override
  public DependencyGroups<DeserializationStep<?>> getDependencyGroups() {
    return null;
  }
}
