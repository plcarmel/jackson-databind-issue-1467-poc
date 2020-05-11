package com.plcarmel.steps.theory;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public interface StepInstance<TInput, TResult>
  extends HasDependencies<StepInstance<TInput, ?>>, AsynchronousDeserialization<TInput, TResult>
{
  Set<StepInstance<TInput, ?>> getParents();

  /**
   * @return
   *   Returns true if the step is required for the task to be performed at all. If a non-optional dependency
   *   remains after the EOF is encountered, the input is deemed invalid and the task is not performed.
   */
  boolean isOptional();

  /**
   * @return
   *   Returns true if the computation or side effect of the step can be performed. It can be true only when there
   *   are no dependency left and hasTokenBeenReceived() is true.
   *   Returns null in case it is not an executable step.
   */
  Boolean isReadyToBeExecuted();

  boolean hasTokenBeenReceived();

  Boolean hasBeenExecuted();

  /**
   * @return
   *   Returns true if computation has been fully performed, which can be true only when there are no dependency left.
   */
  default boolean isDone() {
    return getDependencies().isEmpty() &&
      hasTokenBeenReceived() &&
      Optional.ofNullable(hasBeenExecuted()).orElse(true);
  }

  void execute();

  /**
   * Remove dependencies that are not needed anymore.
   *
   * @param onDependencyRemoved
   *   Called for each dependency that is removed.
   */
  void clean(Consumer<StepInstance<TInput, ?>> onDependencyRemoved);

  /**
   * @param force
   *   If true, this is the last call and the step should not wait for optional dependencies.
   */
  default void clean(Consumer<StepInstance<TInput, ?>> onDependencyRemoved, boolean force) {
    if (force) {
      new ArrayList<>(getDependencies())
        .stream()
        .filter(d -> !d.isDone())
        .filter(StepInstance::isOptional)
        .forEach(d -> {
          removeDependency(d);
          onDependencyRemoved.accept(d);
        });
    }
    clean(onDependencyRemoved);
  }

  default void recurse(Consumer<StepInstance<TInput, ?>> onDependencyRemoved) {
    if (Optional.ofNullable(isReadyToBeExecuted()).orElse(false)) {
      execute();
    }
    clean(onDependencyRemoved, false);
    if (isDone()) {
      new ArrayList<>(getParents()).forEach(p -> p.recurse(onDependencyRemoved));
    }
  }

  void addParentToSet(StepInstance<TInput, ?> parent);
  void removeParentFromSet(StepInstance<TInput, ?> parent);
  void removeDependencyFromList(StepInstance<TInput, ?> dependency);

  default void registerAsParent() {
    getDependencies().forEach(d -> d.addParentToSet(this));
  }

  default void removeDependency(StepInstance<TInput, ?> dependency) {
    removeDependencyFromList(dependency);
    dependency.removeParentFromSet(this);
  }

}
