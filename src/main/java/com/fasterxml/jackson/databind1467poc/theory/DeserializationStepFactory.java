package com.fasterxml.jackson.databind1467poc.theory;

import java.util.List;

public interface DeserializationStepFactory {

  <T> DeserializationStepBuilder<T> builderStepAlso(DeserializationStep<T> mainDependency);

  <T> DeserializationStepBuilder<T> builderDeserializeStandardType(TypeConfiguration<T> desc);

  <T> DeserializationStepBuilder<T> builderInstantiateUsingDefaultConstructor();

  <T> DeserializationStepBuilder<T> builderInstantiateUsing(CreatorConfiguration<T> creatorConf);

  DeserializationStepBuilder<False> builderReadStartOfObjectToken();

  DeserializationStepBuilder<False> builderReadEndOfObjectToken();

  DeserializationStepBuilder<String> builderReadFieldName();

  <T> DeserializationStepBuilder<List<T>> builderReadArray();

  <T> DeserializationStepBuilder<T> builderDeserializeProperty(PropertyConfiguration<T> desc);

  default <T> DeserializationStepBuilder<T> builderDeserializeValue(TypeConfiguration<T> conf) {
    if (conf.isStandardType()) {
      return builderDeserializeStandardType(conf);
    }
    final DeserializationStepBuilder<T> builderStepInstantiate =
      conf.hasCreator()
      ? builderInstantiateUsing(conf.getCreatorConfiguration())
      : builderInstantiateUsingDefaultConstructor();
    builderStepInstantiate.addDependency(builderReadStartOfObjectToken().build());
    final DeserializationStepBuilder<T> builderStepWriteProperties =
      builderStepAlso(builderStepInstantiate.build());
    conf
      .getProperties()
      .stream()
      .map(this::builderDeserializeProperty)
      .map(DependencyBuilder::build)
      .forEach(builderStepWriteProperties::addDependency);
    final DeserializationStepBuilder<T> builderRoot = builderStepAlso(builderStepWriteProperties.build());
    builderRoot.addDependency(builderReadEndOfObjectToken().build());
    return builderRoot;
  }

}
