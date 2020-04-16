package com.fasterxml.jackson.databind1467poc.example.instances;

import com.fasterxml.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.List;

public abstract class InstanceHavingUnmanagedDependencies<T> implements DeserializationStepInstance<T> {

  private final List<DeserializationStepInstance<?>> dependencies;

  public InstanceHavingUnmanagedDependencies(List<DeserializationStepInstance<?>> dependencies) {
    this.dependencies = dependencies;
  }

  public List<DeserializationStepInstance<?>> getDependencies() {
    return dependencies;
  }

  @Override
  public boolean areDependenciesSatisfied() {
    return dependencies.stream().allMatch(DeserializationStepInstance::isOptional);
  }

  @Override
  public void update() {
    for (int i = 0; i < dependencies.size(); ) {
      if (dependencies.get(i).isDone()) dependencies.remove(i);
      else i++;
    }
  }
}
