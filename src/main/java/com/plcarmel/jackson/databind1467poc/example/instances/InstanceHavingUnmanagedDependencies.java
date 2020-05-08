package com.plcarmel.jackson.databind1467poc.example.instances;

import com.plcarmel.jackson.databind1467poc.example.groups.InstanceGroupMany;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.ArrayList;
import java.util.function.Consumer;

public abstract class InstanceHavingUnmanagedDependencies<T> extends InstanceBase<T> {

  protected InstanceGroupMany unmanaged;

  public InstanceHavingUnmanagedDependencies(InstanceGroupMany unmanaged) {
    this.unmanaged = unmanaged;
  }

  @Override
  public void prune(Consumer<DeserializationStepInstance<?>> onDependencyRemoved) {
    if (unmanaged != null) {
      unmanaged.prune(() -> true, onDependencyRemoved, this);
      if (unmanaged.isDone()) unmanaged = null;
    }
    super.prune(onDependencyRemoved);
  }
}
