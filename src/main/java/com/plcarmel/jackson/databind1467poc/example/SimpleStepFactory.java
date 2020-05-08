package com.plcarmel.jackson.databind1467poc.example;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.plcarmel.jackson.databind1467poc.generic.builders.BasicBuilder;
import com.plcarmel.jackson.databind1467poc.generic.groups.StepGroupOne;
import com.plcarmel.jackson.databind1467poc.generic.groups.StepGroupTwo;
import com.plcarmel.jackson.databind1467poc.example.steps.*;
import com.plcarmel.jackson.databind1467poc.generic.steps.StepAlso;
import com.plcarmel.jackson.databind1467poc.generic.steps.StepInstantiateUsingDefaultConstructor;
import com.plcarmel.jackson.databind1467poc.generic.steps.StepSetProperty;
import com.plcarmel.jackson.databind1467poc.theory.*;

public class SimpleStepFactory implements StepFactory<JsonParser> {

  private static final SimpleStepFactory instance = new SimpleStepFactory();

  public static SimpleStepFactory getInstance() { return instance; }

  private SimpleStepFactory() {}

  @Override
  public <TResult> StepBuilder<JsonParser, TResult> builderStepAlso(Step<JsonParser, TResult> mainDependency) {
    return new BasicBuilder<>(l -> new StepAlso<>(new StepGroupOne<>(mainDependency), l));
  }

  @Override
  public <T> StepBuilder<JsonParser, T> builderDeserializeStandardType(PropertyConfiguration<T> conf) {
    return new BasicBuilder<>(l -> new StepDeserializeStandardValue<>(conf, l));
  }

  @Override
  public <T> StepBuilder<JsonParser, T> builderInstantiateUsingDefaultConstructor(TypeConfiguration<T> conf) {
    return new BasicBuilder<>(l -> new StepInstantiateUsingDefaultConstructor<>(conf, l));
  }

  @Override
  public <T> StepBuilder<JsonParser, T> builderInstantiateUsing(CreatorConfiguration<T> creatorConf) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public StepBuilder<JsonParser, NoData> builderExpectTokenKind(JsonToken expectedTokenKind) {
    return new BasicBuilder<>(l -> new StepExpectToken(expectedTokenKind, l));
  }

  @Override
  public StepBuilder<JsonParser, NoData> builderExpectToken(
    JsonToken expectedTokenKind,
    Object expectedTokenValue
  ) {
    return new BasicBuilder<>(l -> new StepExpectToken(expectedTokenKind, expectedTokenValue, l));
  }

  @Override
  public <T> StepBuilder<JsonParser, T> builderDeserializeArray(TypeConfiguration<T> conf) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public <TClass, TProperty> StepBuilder<JsonParser, NoData> builderSetProperty(
    PropertyConfiguration<? extends TProperty> conf,
    Step<JsonParser, TClass> instantiationStep,
    Step<JsonParser, ? extends TProperty> valueDeserializationStep
  ) {
    return new BasicBuilder<>(l ->
      new StepSetProperty<>(conf, new StepGroupTwo<>(instantiationStep, valueDeserializationStep), l)
    );
  }

}
