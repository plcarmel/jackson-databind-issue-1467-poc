package com.plcarmel.jackson.databind1467poc.example.instances;

import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.List;
import java.util.function.Consumer;

public abstract class InstanceHavingUnmanagedDependencies<T> extends InstanceBase<T> {

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
  public void prune(Consumer<DeserializationStepInstance<?>> onRemoved) {
    for (int i = 0; i < dependencies.size(); ) {
      final DeserializationStepInstance<?> d = dependencies.get(i);
      if (d.isDone()) {
        d.removeParent(this);
        dependencies.remove(i);
        onRemoved.accept(d);
      }
      else i++;
    }
    if (isDone()) {
      getParents().forEach(p -> p.prune(onRemoved));
    }
  }
}
