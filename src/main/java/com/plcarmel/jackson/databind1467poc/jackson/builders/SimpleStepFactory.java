package com.plcarmel.jackson.databind1467poc.jackson.builders;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.plcarmel.jackson.databind1467poc.generic.builders.StepFactory;
import com.plcarmel.jackson.databind1467poc.generic.builders.UnmanagedDependenciesBuilder;
import com.plcarmel.jackson.databind1467poc.generic.groups.StepGroupMany;
import com.plcarmel.jackson.databind1467poc.generic.groups.StepGroupOne;
import com.plcarmel.jackson.databind1467poc.generic.steps.StepInstantiateUsingCreator;
import com.plcarmel.jackson.databind1467poc.jackson.steps.*;
import com.plcarmel.jackson.databind1467poc.generic.steps.StepAlso;
import com.plcarmel.jackson.databind1467poc.generic.steps.StepInstantiateUsingDefaultConstructor;
import com.plcarmel.jackson.databind1467poc.generic.steps.StepSetProperty;
import com.plcarmel.jackson.databind1467poc.theory.*;

import static java.util.stream.Collectors.toList;

public class SimpleStepFactory implements StepFactory<JsonParser> {

  private static final SimpleStepFactory instance = new SimpleStepFactory();

  public static SimpleStepFactory getInstance() { return instance; }

  private SimpleStepFactory() {}

  @Override
  public <TResult> StepBuilder<JsonParser, TResult> builderStepAlso(Step<JsonParser, TResult> mainDependency) {
    return new UnmanagedDependenciesBuilder<>(u -> new StepAlso<>(new StepGroupOne<>(mainDependency), u));
  }

  @Override
  public
    <TValue>
    StepBuilder<JsonParser, TValue>
    builderDeserializeStandardType(PropertyConfiguration<?, TValue> conf)
  {
    return new UnmanagedDependenciesBuilder<>(u -> new StepDeserializeStandardValue<>(conf, u));
  }

  @Override
  public <T> StepBuilder<JsonParser, ? extends T> builderInstantiateUsingDefaultConstructor(
    TypeConfiguration<T> conf
  ) {
    return new UnmanagedDependenciesBuilder<>(u -> new StepInstantiateUsingDefaultConstructor<>(conf, u));
  }

  @Override
  public <TClass> StepBuilder<JsonParser, ? extends TClass> builderInstantiateUsing(
    CreatorConfiguration<TClass> creatorConf
  ) {
    return new UnmanagedDependenciesBuilder<>(unmanaged ->
      new StepInstantiateUsingCreator<>(
        creatorConf,
        new StepGroupMany<>(
          true,
          creatorConf
            .getParamConfigurations()
            .stream()
            .map(this::builderDeserializeProperty)
            .map(StepBuilder::build)
            .collect(toList())
        ),
        unmanaged
      )
    );
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
  public <T> StepBuilder<JsonParser, T> builderDeserializeArray(TypeConfiguration<T> conf) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public <TClass, TValue> StepBuilder<JsonParser, NoData> builderSetProperty(
    SettablePropertyConfiguration<TClass, TValue> conf,
    Step<JsonParser, ? extends TClass> instantiationStep,
    Step<JsonParser, ? extends TValue> valueDeserializationStep
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
