package com.plcarmel.jackson.databind1467poc.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.plcarmel.jackson.databind1467poc.generic.builders.UnmanagedDependenciesBuilder;
import com.plcarmel.jackson.databind1467poc.generic.groups.StepGroupOne;
import com.plcarmel.jackson.databind1467poc.jackson.steps.*;
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
    return new UnmanagedDependenciesBuilder<>(u -> new StepAlso<>(new StepGroupOne<>(mainDependency), u));
  }

  @Override
  public <T> StepBuilder<JsonParser, T> builderDeserializeStandardType(PropertyConfiguration<T> conf) {
    return new UnmanagedDependenciesBuilder<>(u -> new StepDeserializeStandardValue<>(conf, u));
  }

  @Override
  public <T> StepBuilder<JsonParser, T> builderInstantiateUsingDefaultConstructor(TypeConfiguration<T> conf) {
    return new UnmanagedDependenciesBuilder<>(u -> new StepInstantiateUsingDefaultConstructor<>(conf, u));
  }

  @Override
  public <T> StepBuilder<JsonParser, T> builderInstantiateUsing(CreatorConfiguration<T> creatorConf) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public StepBuilder<JsonParser, NoData> builderExpectTokenKind(JsonToken expectedTokenKind) {
    return new UnmanagedDependenciesBuilder<>(u -> new StepExpectToken(expectedTokenKind, u));
  }

  @Override
  public StepBuilder<JsonParser, NoData> builderExpectToken(
    JsonToken expectedTokenKind,
    Object expectedTokenValue
  ) {
    return new UnmanagedDependenciesBuilder<>(u -> new StepExpectToken(expectedTokenKind, expectedTokenValue, u));
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
    return new UnmanagedDependenciesBuilder<>(unmanaged ->
      new StepSetProperty<>(
        conf,
        new StepGroupOne<>(instantiationStep),
        new StepGroupOne<>(valueDeserializationStep),
        unmanaged
      )
    );
  }

}
