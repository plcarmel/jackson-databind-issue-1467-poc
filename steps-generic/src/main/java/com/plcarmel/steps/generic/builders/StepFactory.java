package com.plcarmel.steps.generic.builders;

import com.plcarmel.steps.theory.*;

public interface StepFactory<TInput, TToken> {

  <TResult> StepBuilder<TInput, TResult> builderStepAlso(Step<TInput, TResult> mainDependency);

  <TResult> StepBuilder<TInput, TResult> builderDeserializeStandardType(PropertyConfiguration<?, TResult> conf);

  <TResult> StepBuilder<TInput, ? extends TResult> builderInstantiateUsingDefaultConstructor(
    TypeConfiguration<TResult> conf
  );

  <TResult> StepBuilder<TInput, ? extends TResult> builderInstantiateUsing(
    CreatorConfiguration<TResult> creatorConf
  );

  StepBuilder<TInput, NoData> builderExpectTokenKind(TToken kind);

  StepBuilder<TInput, NoData> builderExpectToken(TToken kind, Object token, boolean isOptional);

  <TResult> StepBuilder<TInput, TResult> builderDeserializeArray(TypeConfiguration<TResult> conf);

  <TClass, TValue> StepBuilder<TInput, NoData> builderSetProperty(
    SettablePropertyConfiguration<TClass, TValue> conf,
    Step<TInput, ? extends TClass> instantiationStep,
    Step<TInput, ? extends TValue> valueDeserializationStep
  );

  default <TClass, TValue> StepBuilder<TInput, NoData> builderSetProperty(
    SettablePropertyConfiguration<TClass, TValue> conf,
    Step<TInput, ? extends TClass> instantiationStep
  ) {
    return this.builderSetProperty(conf, instantiationStep, builderDeserializeProperty(conf).build());
  }

  TToken tokenFieldName();
  TToken tokenStartObject();
  TToken tokenEndObject();

  default <TClass, TValue> StepBuilder<TInput, ? extends TValue> builderDeserializeProperty(
    PropertyConfiguration<TClass, TValue> conf
  ) {
    final StepBuilder<TInput, ? extends TValue> builder = builderDeserializeValue(conf);
    if (!conf.isUnwrapped()) {
      builder.addDependency(builderExpectToken(tokenFieldName(), conf.getName(), true).build());
    }
    return builder;
  }

  default <TResult> StepBuilder<TInput, ? extends TResult> builderDeserializeValue(
    PropertyConfiguration<?, TResult> conf
  ) {
    if (conf.getTypeConfiguration().isStandardType()) {
      return builderDeserializeStandardType(conf);
    }
    final TypeConfiguration<TResult> typeConf = conf.getTypeConfiguration();
    if (typeConf.isCollection()) {
      return builderDeserializeArray(typeConf);
    }
    return builderDeserializeBeanValue(typeConf, conf.isUnwrapped());
  }

  default <TResult> StepBuilder<TInput, ? extends TResult> builderDeserializeBeanValue(
    TypeConfiguration<TResult> typeConf,
    boolean unwrapped
  ) {
    final StepBuilder<TInput, ? extends TResult> builderStepInstantiate =
      typeConf.hasCreator()
      ? builderInstantiateUsing(typeConf.getCreatorConfiguration())
      : builderInstantiateUsingDefaultConstructor(typeConf);
    if (!unwrapped) {
      builderStepInstantiate.addDependency(builderExpectTokenKind(tokenStartObject()).build());
    }
    final Step<TInput, ? extends TResult> stepInstantiate = builderStepInstantiate.build();
    final StepBuilder<TInput, ? extends TResult> builderRoot = builderStepAlso(stepInstantiate);
    typeConf
      .getProperties()
      .stream()
      .map(c -> builderSetProperty(c, stepInstantiate))
      .map(StepBuilder::build)
      .forEach(builderRoot::addDependency);
    if (!unwrapped) {
      builderRoot.addDependency(builderExpectTokenKind(tokenEndObject()).build());
    }
    return builderRoot;
  }

  default <TResult> StepBuilder<TInput, ? extends TResult> builderDeserializeBeanValue(
    TypeConfiguration<TResult> typeConf
  ) {
    return builderDeserializeBeanValue(typeConf, false);
  }
}
