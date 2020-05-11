package com.plcarmel.steps.jackson.builders;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.plcarmel.steps.generic.builders.GenericStepFactory;
import com.plcarmel.steps.generic.builders.UnmanagedDependenciesBuilder;
import com.plcarmel.steps.jackson.steps.*;
import com.plcarmel.steps.theory.NoData;
import com.plcarmel.steps.theory.PropertyConfiguration;
import com.plcarmel.steps.theory.StepBuilder;

public class JacksonStepFactory extends GenericStepFactory<JsonParser, JsonToken> {

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

  @Override
  public JsonToken tokenFieldName() {
    return JsonToken.FIELD_NAME;
  }

  @Override
  public JsonToken tokenStartObject() {
    return JsonToken.START_OBJECT;
  }

  @Override
  public JsonToken tokenEndObject() {
    return JsonToken.END_OBJECT;
  }

}
