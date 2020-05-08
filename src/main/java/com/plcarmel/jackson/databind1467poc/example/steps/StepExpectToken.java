package com.plcarmel.jackson.databind1467poc.example.steps;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.plcarmel.jackson.databind1467poc.generic.groups.DependencyGroups;
import com.plcarmel.jackson.databind1467poc.generic.groups.GetDependenciesMixin;
import com.plcarmel.jackson.databind1467poc.generic.groups.StepGroupMany;
import com.plcarmel.jackson.databind1467poc.example.instances.InstanceExpectToken;
import com.plcarmel.jackson.databind1467poc.theory.InstanceFactory;
import com.plcarmel.jackson.databind1467poc.theory.Step;
import com.plcarmel.jackson.databind1467poc.theory.StepInstance;
import com.plcarmel.jackson.databind1467poc.theory.NoData;

public class StepExpectToken
  implements Step<JsonParser, NoData>, GetDependenciesMixin<Step<JsonParser, ?>>
{
  private final JsonToken expectedTokenKind;
  private final Object expectedTokenValue;
  private final boolean useTokenValue;
  private final StepGroupMany<JsonParser> unmanaged;

  public StepExpectToken(
    JsonToken expectedTokenKind,
    Object expectedTokenValue,
    StepGroupMany<JsonParser> unmanaged
  ) {
    this.expectedTokenKind = expectedTokenKind;
    this.expectedTokenValue = expectedTokenValue;
    this.unmanaged = unmanaged;
    useTokenValue = true;
  }

  public StepExpectToken(
    JsonToken expectedTokenKind,
    StepGroupMany<JsonParser> unmanaged
  ) {
    this.expectedTokenKind = expectedTokenKind;
    this.expectedTokenValue = null;
    this.unmanaged = unmanaged;
    useTokenValue = false;
  }

  @Override
  public StepInstance<JsonParser, NoData> instantiated(InstanceFactory<JsonParser> factory) {
    return useTokenValue
      ? new InstanceExpectToken(
        expectedTokenKind,
        expectedTokenValue,
        unmanaged.instantiated(factory)
      )
      : new InstanceExpectToken(
        expectedTokenKind,
        unmanaged.instantiated(factory)
      );
  }

  @Override
  public DependencyGroups<Step<JsonParser, ?>> getDependencyGroups() {
    return null;
  }
}
