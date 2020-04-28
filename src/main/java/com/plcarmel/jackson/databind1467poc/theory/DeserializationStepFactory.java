package com.plcarmel.jackson.databind1467poc.theory;

import com.fasterxml.jackson.core.JsonToken;

import static com.fasterxml.jackson.core.JsonToken.*;

public interface DeserializationStepFactory {

  <T> DeserializationStepBuilder<T> builderStepAlso(DeserializationStep<T> mainDependency);

  <T> DeserializationStepBuilder<T> builderDeserializeStandardType(PropertyConfiguration<T> conf);

  <T> DeserializationStepBuilder<T> builderInstantiateUsingDefaultConstructor(TypeConfiguration<T> conf);

  <T> DeserializationStepBuilder<T> builderInstantiateUsing(CreatorConfiguration<T> creatorConf);

  DeserializationStepBuilder<NoData> builderExpectTokenKind(JsonToken kind);

  DeserializationStepBuilder<NoData> builderExpectToken(JsonToken kind, Object token);

  <T> DeserializationStepBuilder<T> builderDeserializeArray(TypeConfiguration<T> conf);

  <TClass, TProperty> DeserializationStepBuilder<NoData> builderSetProperty(
    PropertyConfiguration<? extends TProperty> conf,
    DeserializationStep<TClass> instantiationStep,
    DeserializationStep<? extends TProperty> valueDeserializationStep
  );

  default <TProperty> DeserializationStepBuilder<TProperty> builderDeserializeProperty(
    PropertyConfiguration<TProperty> conf
  ) {
    final DeserializationStepBuilder<TProperty> builder = builderDeserializeValue(conf);
    if (!conf.isUnwrapped()) {
      builder.addDependency(builderExpectToken(FIELD_NAME, conf.getName()).build());
    }
    return builder;
  }

  default <T> DeserializationStepBuilder<T> builderDeserializeValue(PropertyConfiguration<T> conf) {
    if (conf.getTypeConfiguration().isStandardType()) {
      return builderDeserializeStandardType(conf);
    }
    final TypeConfiguration<T> typeConf = conf.getTypeConfiguration();
    if (typeConf.isCollection()) {
      return builderDeserializeArray(typeConf);
    }
    return builderDeserializeBeanValue(typeConf, conf.isUnwrapped());
  }

  default <T> DeserializationStepBuilder<T> builderDeserializeBeanValue(
    TypeConfiguration<T> typeConf,
    boolean unwrapped
  ) {
    final DeserializationStepBuilder<T> builderStepInstantiate =
      typeConf.hasCreator()
      ? builderInstantiateUsing(typeConf.getCreatorConfiguration())
      : builderInstantiateUsingDefaultConstructor(typeConf);
    if (!unwrapped) {
      builderStepInstantiate.addDependency(builderExpectTokenKind(START_OBJECT).build());
    }
    DeserializationStep<T> stepInstantiate = builderStepInstantiate.build();
    final DeserializationStepBuilder<T> builderRoot = builderStepAlso(stepInstantiate);
    typeConf
      .getProperties()
      .stream()
      .map(c -> this.builderSetProperty(c, stepInstantiate, builderDeserializeProperty(c).build()))
      .map(DeserializationStepBuilder::build)
      .forEach(builderRoot::addDependency);
    if (!unwrapped) {
      builderRoot.addDependency(builderExpectTokenKind(END_OBJECT).build());
    }
    return builderRoot;
  }

  default <T> DeserializationStepBuilder<T> builderDeserializeBeanValue(TypeConfiguration<T> typeConf) {
    return builderDeserializeBeanValue(typeConf, false);
  }
}
