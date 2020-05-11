package com.plcarmel.jackson.databind1467poc.jackson.builders;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.plcarmel.jackson.databind1467poc.generic.builders.GenericStepFactory;
import com.plcarmel.jackson.databind1467poc.generic.builders.UnmanagedDependenciesBuilder;
import com.plcarmel.jackson.databind1467poc.jackson.steps.*;
import com.plcarmel.jackson.databind1467poc.theory.*;

public class JacksonStepFactory extends GenericStepFactory<JsonParser> {

  private static final JacksonStepFactory instance = new JacksonStepFactory();

  public static JacksonStepFactory getInstance() { return instance; }

  private JacksonStepFactory() {}

  @Override
  public
    <TValue>
    StepBuilder<JsonParser, TValue>
    builderDeserializeStandardType(PropertyConfiguration<?, TValue> conf)
  {
    return new UnmanagedDependenciesBuilder<>(u -> new StepDeserializeStandardValue<>(conf, u));
  }

  @Override
  public StepBuilder<JsonParser, NoData> builderExpectTokenKind(JsonToken expectedTokenKind) {
    return new UnmanagedDependenciesBuilder<>(
      u -> new StepExpectToken(expectedTokenKind, false, u)
    );
  }

  @Override
  public StepBuilder<JsonParser, NoData> builderExpectToken(
    JsonToken expectedTokenKind,
    Object expectedTokenValue,
    boolean isOptional
  ) {
    return new UnmanagedDependenciesBuilder<>(
      u -> new StepExpectToken(expectedTokenKind, expectedTokenValue, isOptional, u)
    );
  }

}
