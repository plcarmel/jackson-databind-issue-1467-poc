package com.plcarmel.jackson.databind1467poc.theory;

import com.fasterxml.jackson.core.JsonToken;

import static com.fasterxml.jackson.core.JsonToken.*;

public interface StepFactory<TInput> {

  <TResult> StepBuilder<TInput, TResult> builderStepAlso(Step<TInput, TResult> mainDependency);

  <TResult> StepBuilder<TInput, TResult> builderDeserializeStandardType(PropertyConfiguration<TResult> conf);

  <TResult> StepBuilder<TInput, TResult> builderInstantiateUsingDefaultConstructor(TypeConfiguration<TResult> conf);

  <TResult> StepBuilder<TInput, TResult> builderInstantiateUsing(CreatorConfiguration<TResult> creatorConf);

  StepBuilder<TInput, NoData> builderExpectTokenKind(JsonToken kind);

  StepBuilder<TInput, NoData> builderExpectToken(JsonToken kind, Object token);

  <TResult> StepBuilder<TInput, TResult> builderDeserializeArray(TypeConfiguration<TResult> conf);

  <TClass, TProperty> StepBuilder<TInput, NoData> builderSetProperty(
    PropertyConfiguration<? extends TProperty> conf,
    Step<TInput, TClass> instantiationStep,
    Step<TInput, ? extends TProperty> valueDeserializationStep
  );

  default <TProperty> StepBuilder<TInput, TProperty> builderDeserializeProperty(
    PropertyConfiguration<TProperty> conf
  ) {
    final StepBuilder<TInput, TProperty> builder = builderDeserializeValue(conf);
    if (!conf.isUnwrapped()) {
      builder.addDependency(builderExpectToken(FIELD_NAME, conf.getName()).build());
    }
    return builder;
  }

  default <TResult> StepBuilder<TInput, TResult> builderDeserializeValue(PropertyConfiguration<TResult> conf) {
    if (conf.getTypeConfiguration().isStandardType()) {
      return builderDeserializeStandardType(conf);
    }
    final TypeConfiguration<TResult> typeConf = conf.getTypeConfiguration();
    if (typeConf.isCollection()) {
      return builderDeserializeArray(typeConf);
    }
    return builderDeserializeBeanValue(typeConf, conf.isUnwrapped());
  }

  default <TResult> StepBuilder<TInput, TResult> builderDeserializeBeanValue(
    TypeConfiguration<TResult> typeConf,
    boolean unwrapped
  ) {
    final StepBuilder<TInput, TResult> builderStepInstantiate =
      typeConf.hasCreator()
      ? builderInstantiateUsing(typeConf.getCreatorConfiguration())
      : builderInstantiateUsingDefaultConstructor(typeConf);
    if (!unwrapped) {
      builderStepInstantiate.addDependency(builderExpectTokenKind(START_OBJECT).build());
    }
    final Step<TInput, TResult> stepInstantiate = builderStepInstantiate.build();
    final StepBuilder<TInput, TResult> builderRoot = builderStepAlso(stepInstantiate);
    typeConf
      .getProperties()
      .stream()
      .map(c -> this.builderSetProperty(c, stepInstantiate, builderDeserializeProperty(c).build()))
      .map(StepBuilder::build)
      .forEach(builderRoot::addDependency);
    if (!unwrapped) {
      builderRoot.addDependency(builderExpectTokenKind(END_OBJECT).build());
    }
    return builderRoot;
  }

  default <TResult> StepBuilder<TInput, TResult> builderDeserializeBeanValue(TypeConfiguration<TResult> typeConf) {
    return builderDeserializeBeanValue(typeConf, false);
  }
}
