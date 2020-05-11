package com.plcarmel.steps.generic.groups.instances.mixins;

import com.plcarmel.steps.theory.StepInstance;

public interface RemoveDependencyFromListMixin<TInput, TResult>
  extends StepInstance<TInput, TResult>, HasInstanceDependencyGroups<TInput>
{
  @Override
  default void removeDependencyFromList(StepInstance<TInput, ?> dependency) {
    getDependencyGroups().removeDependencyFromList(dependency);
  }
}
