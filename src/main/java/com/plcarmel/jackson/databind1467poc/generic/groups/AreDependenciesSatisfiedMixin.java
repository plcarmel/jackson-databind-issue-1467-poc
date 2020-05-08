package com.plcarmel.jackson.databind1467poc.generic.groups;

import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

public interface AreDependenciesSatisfiedMixin<TInput, TResult> extends StepInstance<TInput, TResult>
{
  DependencyGroups<StepInstance<TInput, ?>> getDependencyGroups();

  @Override
  default boolean areDependenciesSatisfied() {
    return getDependencyGroups()
      .getDependencies()
      .stream()
      .allMatch(StepInstance::areDependenciesSatisfied);
  }
}
