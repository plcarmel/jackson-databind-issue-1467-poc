package com.plcarmel.jackson.databind1467poc.generic.groups;

import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

import java.util.ArrayList;
import java.util.function.Consumer;

public interface CollapseMixin<TInput, TResult>
  extends StepInstance<TInput, TResult>, HasInstanceDependencyGroups<TInput>
{
  @Override
  default void clean(Consumer<StepInstance<TInput, ?>> onDependencyRemoved) {
    final InstanceDependencyGroups<TInput> groups = getDependencyGroups();
    groups.collapse(this, onDependencyRemoved);
    if (groups.allDone()) {
      new ArrayList<>(groups.getDependencies()).forEach(d -> {
        removeDependency(d);
        onDependencyRemoved.accept(d);
      });
    }
  }

}
