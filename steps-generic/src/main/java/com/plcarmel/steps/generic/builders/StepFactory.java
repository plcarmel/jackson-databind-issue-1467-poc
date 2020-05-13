package com.plcarmel.steps.generic.builders;

import com.plcarmel.steps.theory.*;

public interface StepFactory<TInput, TToken> {

  <TResult> StepBuilder<TInput, TResult> builderStepAlso(Step<TInput, TResult> mainDependency);

  <TResult> StepBuilder<TInput, TResult> builderDeserializeStandardType(PropertyConfiguration<?, TResult> conf);

  <TResult> StepBuilder<TInput, ? extends TResult> builderInstantiateUsingDefaultConstructor(
    TypeConfiguration<TResult> conf
  );

  <TResult> StepBuilder<TInput, ? extends TResult> builderInstantiateUsing(
    Step<TInput, NoData> upperStartObject,
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
    Step<TInput, NoData> upperStartObject,
    SettablePropertyConfiguration<TClass, TValue> conf,
    Step<TInput, ? extends TClass> instantiationStep
  ) {
    return this.builderSetProperty(
      conf,
      instantiationStep,
      builderDeserializeProperty(upperStartObject, conf).build()
    );
  }

  TToken tokenFieldName();
  TToken tokenStartObject();
  TToken tokenEndObject();

  default <TClass, TValue> StepBuilder<TInput, ? extends TValue> builderDeserializeProperty(
    Step<TInput, NoData> upperStartObject,
    PropertyConfiguration<TClass, TValue> conf
  ) {
    final StepBuilder<TInput, ? extends TValue> builder = builderDeserializeValue(upperStartObject, conf);
    if (!conf.isUnwrapped()) {
      builder.addDependency(builderExpectToken(tokenFieldName(), conf.getName(), true).build());
    }
    return builder;
  }

  default <TResult> StepBuilder<TInput, ? extends TResult> builderDeserializeValue(
    Step<TInput, NoData> upperStartObject,
    PropertyConfiguration<?, TResult> conf
  ) {
    if (conf.getTypeConfiguration().isStandardType()) {
      return builderDeserializeStandardType(conf);
    }
    final TypeConfiguration<TResult> typeConf = conf.getTypeConfiguration();
    if (typeConf.isCollection()) {
      return builderDeserializeArray(typeConf);
    }
    return builderDeserializeBeanValue(upperStartObject, typeConf, conf.isUnwrapped());
  }

  default <TResult> StepBuilder<TInput, ? extends TResult> builderDeserializeBeanValue(
    Step<TInput, NoData> upperStartObject,
    TypeConfiguration<TResult> typeConf,
    boolean unwrapped
  ) {
    if (!unwrapped) {
      final StepBuilder<TInput, NoData> expectStartToken = builderExpectTokenKind(tokenStartObject());
      if (upperStartObject != null) {
        expectStartToken.addDependency(upperStartObject);
      }
      upperStartObject = expectStartToken.build();
    }
    final StepBuilder<TInput, ? extends TResult> builderStepInstantiate =
      typeConf.hasCreator()
        ? builderInstantiateUsing(upperStartObject, typeConf.getCreatorConfiguration())
        : builderInstantiateUsingDefaultConstructor(typeConf);
    builderStepInstantiate.addDependency(upperStartObject);
    final Step<TInput, ? extends TResult> stepInstantiate = builderStepInstantiate.build();
    final StepBuilder<TInput, ? extends TResult> builderRoot = builderStepAlso(stepInstantiate);
    final Step<TInput, NoData> finalUpperStartObject = upperStartObject;
    typeConf
      .getProperties()
      .stream()
      .map(c -> builderSetProperty(finalUpperStartObject, c, stepInstantiate))
      .map(StepBuilder::build)
      .forEach(builderRoot::addDependency);
    if (!unwrapped) {
      final StepBuilder<TInput, NoData> builderExpectEndObject = builderExpectTokenKind(tokenEndObject());
      builderExpectEndObject.addDependency(stepInstantiate);
      builderRoot.addDependency(builderExpectEndObject.build());
    }
    return builderRoot;
  }

  default <TResult> StepBuilder<TInput, ? extends TResult> builderDeserializeBeanValue(
    TypeConfiguration<TResult> typeConf
  ) {
    return builderDeserializeBeanValue(null, typeConf, false);
  }
}
