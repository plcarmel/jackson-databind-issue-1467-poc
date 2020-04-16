package com.fasterxml.jackson.databind1467poc.theory;

import com.fasterxml.jackson.core.JsonToken;

import static com.fasterxml.jackson.core.JsonToken.*;

public interface DeserializationStepFactory {

  <T> DeserializationStepBuilder<T> builderStepAlso(DeserializationStep<T> mainDependency);

  <T> DeserializationStepBuilder<T> builderDeserializeStandardType(TypeConfiguration<T> desc);

  <T> DeserializationStepBuilder<T> builderInstantiateUsingDefaultConstructor(TypeConfiguration<T> desc);

  <T> DeserializationStepBuilder<T> builderInstantiateUsing(CreatorConfiguration<T> creatorConf);

  DeserializationStepBuilder<False> builderExpectTokenKind(JsonToken kind);

  DeserializationStepBuilder<False> builderExpectToken(JsonToken kind, Object token);

  <T> DeserializationStepBuilder<T> builderDeserializeArray(TypeConfiguration<T> conf);

  default <T> DeserializationStepBuilder<T> builderDeserializeProperty(PropertyConfiguration<T> desc) {
    final DeserializationStepBuilder<T> builder = builderDeserializeValue(desc.getTypeConfiguration());
    builder.addDependency(builderExpectToken(FIELD_NAME, desc.getName()).build());
    return builder;
  }

  default <T> DeserializationStepBuilder<T> builderDeserializeValue(TypeConfiguration<T> conf) {
    if (conf.isStandardType()) {
      return builderDeserializeStandardType(conf);
    }
    if (conf.isCollection()) {
      return builderDeserializeArray(conf);
    }
    final DeserializationStepBuilder<T> builderStepInstantiate =
      conf.hasCreator()
      ? builderInstantiateUsing(conf.getCreatorConfiguration())
      : builderInstantiateUsingDefaultConstructor(conf);
    builderStepInstantiate.addDependency(builderExpectTokenKind(START_OBJECT).build());
    final DeserializationStepBuilder<T> builderStepWriteProperties =
      builderStepAlso(builderStepInstantiate.build());
    conf
      .getProperties()
      .stream()
      .map(this::builderDeserializeProperty)
      .map(DeserializationStepBuilder::build)
      .forEach(builderStepWriteProperties::addDependency);
    final DeserializationStepBuilder<T> builderRoot = builderStepAlso(builderStepWriteProperties.build());
    builderRoot.addDependency(builderExpectTokenKind(END_OBJECT).build());
    return builderRoot;
  }

}
