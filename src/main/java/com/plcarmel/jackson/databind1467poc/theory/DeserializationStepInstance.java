package com.plcarmel.jackson.databind1467poc.theory;

import java.util.Set;
import java.util.function.Consumer;

public interface DeserializationStepInstance<T>
  extends HasDependencies<DeserializationStepInstance<?>>, AsynchronousDeserialization<T>
{
  boolean isOptional();
  boolean isDone();
  boolean areDependenciesSatisfied();

  Set<DeserializationStepInstance<?>> getParents();

  void addParent(DeserializationStepInstance<?> parent);
  void removeParent(DeserializationStepInstance<?> parent);

  void prune(Consumer<DeserializationStepInstance<?>> onRemoved);

  void complete();

}
