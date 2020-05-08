package com.plcarmel.jackson.databind1467poc.generic.groups;

import com.plcarmel.jackson.databind1467poc.theory.StepInstance;
import com.plcarmel.jackson.databind1467poc.theory.HasDependencies;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface InstanceGroup<TInput> extends HasDependencies<StepInstance<TInput, ?>> {

  default boolean isDone() {
    return getDependencies().stream().allMatch(StepInstance::isDone);
  }

  default boolean areDependenciesSatisfied() {
    return getDependencies().stream().allMatch(d -> d.isOptional() || d.areDependenciesSatisfied());
  }

  void prune(
    Predicate<StepInstance<TInput, ?>> doRemoveDependency,
    Consumer<StepInstance<TInput, ?>> onDependencyRemoved,
    StepInstance<TInput, ?> ref
  );

}
