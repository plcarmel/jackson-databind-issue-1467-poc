package com.plcarmel.jackson.databind1467poc.example.groups;

import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.HasDependencies;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface InstanceGroup extends HasDependencies<DeserializationStepInstance<?>> {

  default boolean isDone() {
    return getDependencies().stream().allMatch(DeserializationStepInstance::isDone);
  }

  default boolean areDependenciesSatisfied() {
    return getDependencies().stream().allMatch(d -> d.isOptional() || d.areDependenciesSatisfied());
  }

  void prune(
    Supplier<Boolean> doRemoveDependency,
    Consumer<DeserializationStepInstance<?>> onDependencyRemoved,
    DeserializationStepInstance<?> ref
  );

}
