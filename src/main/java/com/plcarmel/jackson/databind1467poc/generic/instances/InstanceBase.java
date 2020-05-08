package com.plcarmel.jackson.databind1467poc.generic.instances;

import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class InstanceBase<TInput, TResult> implements StepInstance<TInput, TResult> {

  private final Set<StepInstance<TInput, ?>> parents = new HashSet<>();

  @Override
  public Set<StepInstance<TInput, ?>> getParents() {
    return parents;
  }

  @Override
  public void addParent(StepInstance<TInput, ?> parent) {
    parents.add(parent);
  }

  @Override
  public void removeParent(StepInstance<TInput, ?> parent) {
    parents.remove(parent);
  }

  @Override
  public void complete() {
    getDependencies().forEach(StepInstance::complete);
  }

  @Override
  public void prune(Consumer<StepInstance<TInput, ?>> onDependencyRemoved) {
    if (isDone()) {
      new ArrayList<>(getParents()).forEach(p -> p.prune(onDependencyRemoved));
    }
  }
}
