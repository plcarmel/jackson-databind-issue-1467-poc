package com.plcarmel.jackson.databind1467poc.theory;

import com.fasterxml.jackson.core.JsonToken;

import static com.fasterxml.jackson.core.JsonToken.*;

public interface DeserializationStepFactory {

  <T> DeserializationStepBuilder<T> builderStepAlso(DeserializationStep<T> mainDependency);

  <T> DeserializationStepBuilder<T> builderDeserializeStandardType(PropertyConfiguration<?, T> conf);

  <T> DeserializationStepBuilder<T> builderInstantiateUsingDefaultConstructor(TypeConfiguration<T> conf);

  <T> DeserializationStepBuilder<T> builderInstantiateUsing(CreatorConfiguration<T> creatorConf);

  DeserializationStepBuilder<NoData> builderExpectTokenKind(JsonToken kind);

  DeserializationStepBuilder<NoData> builderExpectToken(JsonToken kind, Object token);

  <T> DeserializationStepBuilder<T> builderDeserializeArray(TypeConfiguration<T> conf);

  <TClass, TProperty> DeserializationStepBuilder<NoData> builderSetProperty(
    PropertyConfiguration<TClass, ? extends TProperty> conf,
    DeserializationStep<TClass> instantiationStep,
    DeserializationStep<? extends TProperty> valueDeserializationStep
  );

  default <TProperty> DeserializationStepBuilder<TProperty> builderDeserializeProperty(
    PropertyConfiguration<?, TProperty> conf
  ) {
    final DeserializationStepBuilder<TProperty> builder = builderDeserializeValue(conf);
    builder.addDependency(builderExpectToken(FIELD_NAME, conf.getName()).build());
    return builder;
  }

  default <T> DeserializationStepBuilder<T> builderDeserializeValue(PropertyConfiguration<?, T> conf) {
    if (conf.getTypeConfiguration().isStandardType()) {
      return builderDeserializeStandardType(conf);
    }
    return builderDeserializeValue(conf.getTypeConfiguration());
  }

  default <T> DeserializationStepBuilder<T> builderDeserializeValue(TypeConfiguration<T> conf) {
    if (conf.isCollection()) {
      return builderDeserializeArray(conf);
    }
    final DeserializationStepBuilder<T> builderStepInstantiate =
      conf.hasCreator()
      ? builderInstantiateUsing(conf.getCreatorConfiguration())
      : builderInstantiateUsingDefaultConstructor(conf);
    builderStepInstantiate.addDependency(builderExpectTokenKind(START_OBJECT).build());
    DeserializationStep<T> stepInstantiate = builderStepInstantiate.build();
    final DeserializationStepBuilder<T> builderRoot = builderStepAlso(stepInstantiate);
    conf
      .getProperties()
      .stream()
      .map(c -> this.builderSetProperty(c, stepInstantiate, builderDeserializeProperty(c).build()))
      .map(DeserializationStepBuilder::build)
      .forEach(builderRoot::addDependency);
    builderRoot.addDependency(builderExpectTokenKind(END_OBJECT).build());
    return builderRoot;
  }

}
