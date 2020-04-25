package com.plcarmel.jackson.databind1467poc.example;

import com.fasterxml.jackson.core.JsonToken;
import com.plcarmel.jackson.databind1467poc.example.builders.BasicBuilder;
import com.plcarmel.jackson.databind1467poc.example.steps.*;
import com.plcarmel.jackson.databind1467poc.theory.*;

public class SimpleDeserializationStepFactory implements DeserializationStepFactory {

  private static final SimpleDeserializationStepFactory instance = new SimpleDeserializationStepFactory();

  public static SimpleDeserializationStepFactory getInstance() { return instance; }

  private SimpleDeserializationStepFactory() {}

  @Override
  public <T> DeserializationStepBuilder<T> builderStepAlso(DeserializationStep<T> mainDependency) {
    return new BasicBuilder<>(l -> new StepAlso<>(mainDependency, l));
  }

  @Override
  public <T> DeserializationStepBuilder<T> builderDeserializeStandardType(PropertyConfiguration<T> conf) {
    return new BasicBuilder<>(l -> new StepDeserializeStandardValue<>(conf, l));
  }

  @Override
  public <T> DeserializationStepBuilder<T> builderInstantiateUsingDefaultConstructor(TypeConfiguration<T> conf) {
    return new BasicBuilder<>(l -> new StepInstantiateUsingDefaultConstructor<>(conf, l));
  }

  @Override
  public <T> DeserializationStepBuilder<T> builderInstantiateUsing(CreatorConfiguration<T> creatorConf) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public DeserializationStepBuilder<NoData> builderExpectTokenKind(JsonToken expectedTokenKind) {
    return new BasicBuilder<>(l -> new StepExpectToken(expectedTokenKind, l));
  }

  @Override
  public DeserializationStepBuilder<NoData> builderExpectToken(
    JsonToken expectedTokenKind,
    Object expectedTokenValue
  ) {
    return new BasicBuilder<>(l -> new StepExpectToken(expectedTokenKind, expectedTokenValue, l));
  }

  @Override
  public <T> DeserializationStepBuilder<T> builderDeserializeArray(TypeConfiguration<T> conf) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public <T> DeserializationStepBuilder<NoData> builderSetProperty(
    PropertyConfiguration<T> conf,
    DeserializationStep<?> instantiationStep,
    DeserializationStep<T> valueDeserializationStep
  ) {
    return new BasicBuilder<>(l -> new StepDeserializeProperty<>(conf, instantiationStep, valueDeserializationStep, l));
  }

}
