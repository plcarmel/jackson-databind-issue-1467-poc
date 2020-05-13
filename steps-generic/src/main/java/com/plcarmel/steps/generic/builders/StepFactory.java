package com.plcarmel.steps.generic.builders;

import com.plcarmel.steps.theory.*;

public interface StepFactory<TInput, TToken> {

  <TResult> StepBuilder<TInput, TResult> builderStepAlso(Step<TInput, TResult> mainDependency);

  <TResult> StepBuilder<TInput, TResult> builderDeserializeStandardType(PropertyConfiguration<?, TResult> conf);

  <TResult> StepBuilder<TInput, ? extends TResult> builderInstantiateUsingDefaultConstructor(
    TypeConfiguration<TResult> conf
  );

  <TResult> StepBuilder<TInput, ? extends TResult> builderInstantiateUsing(
    Step<TInput, ?> triggerDependency,
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
    Step<TInput, ?> triggerDependency,
    SettablePropertyConfiguration<TClass, TValue> conf,
    Step<TInput, ? extends TClass> instantiationStep
  ) {
    return this.builderSetProperty(
      conf,
      instantiationStep,
      builderDeserializeProperty(triggerDependency, conf).build()
    );
  }

  TToken tokenFieldName();
  TToken tokenStartObject();
  TToken tokenEndObject();

  default <TClass, TValue> StepBuilder<TInput, ? extends TValue> builderDeserializeProperty(
    Step<TInput, ?> triggerDependency,
    PropertyConfiguration<TClass, TValue> conf
  ) {
    Step<TInput, ?> expectFieldName = null;
    if (!conf.isUnwrapped()) {
      StepBuilder<TInput, ?> expectFieldNameBuilder = builderExpectToken(tokenFieldName(), conf.getName(), true);
      if (triggerDependency != null) expectFieldNameBuilder.addDependency(triggerDependency);
      expectFieldName = expectFieldNameBuilder.build();
    }
    final StepBuilder<TInput, ? extends TValue> builder = builderDeserializeValue(expectFieldName, conf);
    if (expectFieldName != null) {
      builder.addDependency(expectFieldName);
    }
    return builder;
  }

  default <TResult> StepBuilder<TInput, ? extends TResult> builderDeserializeValue(
    Step<TInput, ?> triggerDependency,
    PropertyConfiguration<?, TResult> conf
  ) {
    if (conf.getTypeConfiguration().isStandardType()) {
      return builderDeserializeStandardType(conf);
    }
    final TypeConfiguration<TResult> typeConf = conf.getTypeConfiguration();
    if (typeConf.isCollection()) {
      return builderDeserializeArray(typeConf);
    }
    return builderDeserializeBeanValue(triggerDependency, typeConf, conf.isUnwrapped());
  }

  default <TResult> StepBuilder<TInput, ? extends TResult> builderDeserializeBeanValue(
    Step<TInput, ?> triggerDependency,
    TypeConfiguration<TResult> typeConf,
    boolean unwrapped
  ) {
    if (!unwrapped) {
      final StepBuilder<TInput, NoData> expectStartToken = builderExpectTokenKind(tokenStartObject());
      if (triggerDependency != null) {
        expectStartToken.addDependency(triggerDependency);
      }
      triggerDependency = expectStartToken.build();
    }
    final StepBuilder<TInput, ? extends TResult> builderStepInstantiate =
      typeConf.hasCreator()
        ? builderInstantiateUsing(triggerDependency, typeConf.getCreatorConfiguration())
        : builderInstantiateUsingDefaultConstructor(typeConf);
    if (triggerDependency != null) {
      builderStepInstantiate.addDependency(triggerDependency);
    }
    final Step<TInput, ? extends TResult> stepInstantiate = builderStepInstantiate.build();
    final StepBuilder<TInput, ? extends TResult> builderRoot = builderStepAlso(stepInstantiate);
    final Step<TInput, ?> finalTriggerDependency = triggerDependency;
    typeConf
      .getProperties()
      .stream()
      .map(c -> builderSetProperty(finalTriggerDependency, c, stepInstantiate))
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
