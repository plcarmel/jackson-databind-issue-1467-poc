package com.plcarmel.jackson.databind1467poc.example.instances;

import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class InstanceBase<T> implements DeserializationStepInstance<T> {

  private final Set<DeserializationStepInstance<?>> parents = new HashSet<>();

  @Override
  public Set<DeserializationStepInstance<?>> getParents() {
    return parents;
  }

  @Override
  public void addParent(DeserializationStepInstance<?> parent) {
    parents.add(parent);
  }

  @Override
  public void removeParent(DeserializationStepInstance<?> parent) {
    parents.remove(parent);
  }

  @Override
  public void complete() {
    getDependencies().forEach(DeserializationStepInstance::complete);
  }

  @Override
  public void prune(Consumer<DeserializationStepInstance<?>> onDependencyRemoved) {
    if (isDone()) {
      new ArrayList<>(getParents()).forEach(p -> p.prune(onDependencyRemoved));
    }
  }
}
