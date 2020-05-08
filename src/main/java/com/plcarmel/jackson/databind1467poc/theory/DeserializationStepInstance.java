package com.plcarmel.jackson.databind1467poc.theory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public interface DeserializationStepInstance<T>

  extends HasDependencies<DeserializationStepInstance<?>>, AsynchronousDeserialization<T> {
  boolean isOptional();
  boolean isDone();
  boolean areDependenciesSatisfied();

  Set<DeserializationStepInstance<?>> getParents();

  void addParent(DeserializationStepInstance<?> parent);
  void removeParent(DeserializationStepInstance<?> parent);

  void prune(Consumer<DeserializationStepInstance<?>> onDependencyRemoved);
  void complete();

  default void setTreeParents(Set<DeserializationStepInstance<?>> alreadyVisited) {
    if (alreadyVisited.contains(this)) return;
    final List<DeserializationStepInstance<?>> dependencies = this.getDependencies();
    dependencies.forEach(d -> d.addParent(this));
    dependencies.forEach(d -> d.setTreeParents(alreadyVisited));
  }

  default void setTreeParents() {
    setTreeParents(new HashSet<>());
  }

}
