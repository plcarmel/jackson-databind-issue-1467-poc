package com.plcarmel.jackson.databind1467poc.example.instances;

import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.HashSet;
import java.util.Set;

public abstract class InstanceBase<T> implements DeserializationStepInstance<T> {

  private final Set<DeserializationStepInstance<?>> parents = new HashSet<>();

  protected void registerAsParent() {
    getDependencies().forEach(d -> d.addParent(this));
  }

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
  public void destroy() {
    getDependencies().forEach(d -> d.removeParent(this));
  }
}
