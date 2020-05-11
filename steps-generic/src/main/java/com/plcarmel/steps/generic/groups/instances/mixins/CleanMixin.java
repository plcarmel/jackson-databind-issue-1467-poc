package com.plcarmel.steps.generic.groups.instances.mixins;

import com.plcarmel.steps.generic.groups.instances.InstanceDependencyGroups;
import com.plcarmel.steps.theory.StepInstance;

import java.util.ArrayList;
import java.util.function.Consumer;

public interface CleanMixin<TInput, TResult>
  extends StepInstance<TInput, TResult>, HasInstanceDependencyGroups<TInput>
{
  @Override
  default void clean(Consumer<StepInstance<TInput, ?>> onDependencyRemoved) {
    final InstanceDependencyGroups<TInput> groups = getDependencyGroups();
    groups.clean(this, onDependencyRemoved);
    if (groups.allDone()) {
      new ArrayList<>(groups.getDependencies()).forEach(d -> {
        removeDependency(d);
        onDependencyRemoved.accept(d);
      });
    }
  }

}
