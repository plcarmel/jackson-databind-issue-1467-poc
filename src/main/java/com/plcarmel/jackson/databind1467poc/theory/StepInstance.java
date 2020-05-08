package com.plcarmel.jackson.databind1467poc.theory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public interface StepInstance<TInput, TResult>
  extends HasDependencies<StepInstance<TInput, ?>>, AsynchronousDeserialization<TInput, TResult>
{
  boolean isOptional();
  boolean areDependenciesSatisfied();
  boolean isDone();

  Set<StepInstance<TInput, ?>> getParents();

  void addParent(StepInstance<TInput, ?> parent);
  void removeParent(StepInstance<TInput, ?> parent);

  void prune(Consumer<StepInstance<TInput, ?>> onDependencyRemoved);
  void complete();

  default void setTreeParents(Set<StepInstance<TInput, ?>> alreadyVisited) {
    if (alreadyVisited.contains(this)) return;
    final List<StepInstance<TInput, ?>> dependencies = this.getDependencies();
    dependencies.forEach(d -> d.addParent(this));
    dependencies.forEach(d -> d.setTreeParents(alreadyVisited));
  }

  default void setTreeParents() {
    setTreeParents(new HashSet<>());
  }

}
