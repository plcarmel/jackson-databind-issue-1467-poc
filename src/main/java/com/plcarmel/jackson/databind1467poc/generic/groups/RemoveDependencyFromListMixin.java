package com.plcarmel.jackson.databind1467poc.generic.groups;

import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

public interface RemoveDependencyFromListMixin<TInput, TResult>
  extends StepInstance<TInput, TResult>, HasInstanceDependencyGroups<TInput>
{
  @Override
  default void removeDependencyFromList(StepInstance<TInput, ?> dependency) {
    getDependencyGroups().removeDependencyFromList(dependency);
  }
}
