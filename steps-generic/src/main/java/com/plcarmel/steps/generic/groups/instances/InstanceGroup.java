package com.plcarmel.steps.generic.groups.instances;

import com.plcarmel.steps.generic.groups.Group;
import com.plcarmel.steps.theory.StepInstance;

import java.util.ArrayList;
import java.util.function.Consumer;

public interface InstanceGroup<TInput> extends Group<StepInstance<TInput, ?>> {

  void removeDependencyFromList(StepInstance<TInput, ?> dependency);

  boolean anyDone();
  boolean allDone();

  default void clean(
    StepInstance<TInput, ?> stepInstance,
    Consumer<StepInstance<TInput, ?>> onDependencyRemoved
  ) {
    if (!isManaged()) {
      new ArrayList<>(getDependencies()).stream().filter(StepInstance::isDone).forEach(d -> {
        removeDependencyFromList(d);
        d.removeParentFromSet(stepInstance);
        onDependencyRemoved.accept(d);
      });
    }
  }

}
