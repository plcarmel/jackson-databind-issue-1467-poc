package com.fasterxml.jackson.databind1467poc.example;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind1467poc.example.builders.BasicBuilder;
import com.fasterxml.jackson.databind1467poc.example.steps.StepAlso;
import com.fasterxml.jackson.databind1467poc.example.steps.StepDeserializeStandardValue;
import com.fasterxml.jackson.databind1467poc.example.steps.StepExpectToken;
import com.fasterxml.jackson.databind1467poc.example.steps.StepInstanciateUsingDefaultConstructor;
import com.fasterxml.jackson.databind1467poc.theory.*;

public class SimpleDeserializationStepFactory implements DeserializationStepFactory {

  @Override
  public <T> DeserializationStepBuilder<T> builderStepAlso(DeserializationStep<T> mainDependency) {
    return new BasicBuilder<>(l -> new StepAlso<>(mainDependency, l));
  }

  @Override
  public <T> DeserializationStepBuilder<T> builderDeserializeStandardType(TypeConfiguration<T> desc) {
    return new BasicBuilder<>(l -> new StepDeserializeStandardValue<>(desc, l));
  }

  @Override
  public <T> DeserializationStepBuilder<T> builderInstantiateUsingDefaultConstructor(TypeConfiguration<T> desc) {
    return new BasicBuilder<>(l -> new StepInstanciateUsingDefaultConstructor<>(desc, l));
  }

  @Override
  public <T> DeserializationStepBuilder<T> builderInstantiateUsing(CreatorConfiguration<T> creatorConf) {
    return null;
  }

  @Override
  public DeserializationStepBuilder<False> builderExpectTokenKind(JsonToken expectedTokenKind) {
    return new BasicBuilder<>(l -> new StepExpectToken(expectedTokenKind, l));
  }

  @Override
  public DeserializationStepBuilder<False> builderExpectToken(
    JsonToken expectedTokenKind,
    Object expectedTokenValue
  ) {
    return new BasicBuilder<>(l -> new StepExpectToken(expectedTokenKind, expectedTokenValue, l));
  }

  @Override
  public <T> DeserializationStepBuilder<T> builderDeserializeArray(TypeConfiguration<T> conf) {
    return null;
  }

}
