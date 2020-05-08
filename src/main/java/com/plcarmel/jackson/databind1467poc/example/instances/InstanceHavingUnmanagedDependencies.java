package com.plcarmel.jackson.databind1467poc.example.instances;

import com.plcarmel.jackson.databind1467poc.generic.groups.InstanceGroupMany;
import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

import java.util.function.Consumer;

public abstract class InstanceHavingUnmanagedDependencies<TInput, TResult> extends InstanceBase<TInput, TResult> {

  protected InstanceGroupMany<TInput> unmanaged;

  public InstanceHavingUnmanagedDependencies(InstanceGroupMany<TInput> unmanaged) {
    this.unmanaged = unmanaged;
  }

  @Override
  public void prune(Consumer<StepInstance<TInput, ?>> onDependencyRemoved) {
    if (unmanaged != null) {
      unmanaged.prune(() -> true, onDependencyRemoved, this);
      if (unmanaged.isDone()) unmanaged = null;
    }
    super.prune(onDependencyRemoved);
  }
}
