package com.plcarmel.jackson.databind1467poc.generic.instances;

import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

import java.util.HashSet;
import java.util.Set;

public abstract class InstanceParentsImpl<TInput, TResult> implements StepInstance<TInput, TResult> {

  private final Set<StepInstance<TInput, ?>> parents = new HashSet<>();

  @Override
  public Set<StepInstance<TInput, ?>> getParents() {
    return parents;
  }

  @Override
  public void addParentToSet(StepInstance<TInput, ?> parent) {
    parents.add(parent);
  }

  @Override
  public void removeParentFromSet(StepInstance<TInput, ?> parent) {
    parents.remove(parent);
  }
}
