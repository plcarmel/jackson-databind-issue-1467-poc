package com.plcarmel.jackson.databind1467poc.example.steps;

import com.plcarmel.jackson.databind1467poc.example.structures.StructureUnmanaged;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.List;

import static java.util.stream.Collectors.toList;

public interface StepUnmanagedMixin<T> extends DeserializationStep<T> {

  StructureUnmanaged<DeserializationStep<?>> thisAsStructureUnmanaged();

  default List<DeserializationStepInstance<?>> instantiatedDependencies(
    InstanceFactory dependenciesInstanceFactory
  ) {
    return thisAsStructureUnmanaged()
      .unmanagedDependencies
      .stream()
      .map(dependenciesInstanceFactory::instantiate)
      .collect(toList());
  }
}
