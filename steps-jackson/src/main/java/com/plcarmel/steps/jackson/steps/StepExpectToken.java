package com.plcarmel.steps.jackson.steps;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.plcarmel.steps.generic.groups.DependencyGroups;
import com.plcarmel.steps.generic.groups.mixins.GetDependenciesMixin;
import com.plcarmel.steps.generic.groups.Group;
import com.plcarmel.steps.generic.groups.steps.StepGroupMany;
import com.plcarmel.steps.jackson.instances.InstanceExpectToken;
import com.plcarmel.steps.theory.InstanceFactory;
import com.plcarmel.steps.theory.Step;
import com.plcarmel.steps.theory.StepInstance;
import com.plcarmel.steps.theory.NoData;

public class StepExpectToken
  implements Step<JsonParser, NoData>, GetDependenciesMixin<Group<Step<JsonParser, ?>>, Step<JsonParser, ?>>
{
  private final JsonToken expectedTokenKind;
  private final Object expectedTokenValue;
  private final boolean useTokenValue;
  private final boolean isOptional;
  private final StepGroupMany<JsonParser> unmanaged;

  public JsonToken getExpectedTokenKind() {
    return expectedTokenKind;
  }

  public StepExpectToken(
    JsonToken expectedTokenKind,
    Object expectedTokenValue,
    boolean isOptional,
    StepGroupMany<JsonParser> unmanaged
  ) {
    this.expectedTokenKind = expectedTokenKind;
    this.expectedTokenValue = expectedTokenValue;
    this.isOptional = isOptional;
    this.unmanaged = unmanaged;
    useTokenValue = true;
  }

  public StepExpectToken(
    JsonToken expectedTokenKind,
    boolean isOptional,
    StepGroupMany<JsonParser> unmanaged
  ) {
    this.expectedTokenKind = expectedTokenKind;
    this.expectedTokenValue = null;
    this.isOptional = isOptional;
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
  public DependencyGroups<Group<Step<JsonParser, ?>>, Step<JsonParser, ?>> getDependencyGroups() {
    return null;
  }
}
