package com.plcarmel.jackson.databind1467poc.example.steps;

import com.plcarmel.jackson.databind1467poc.example.instances.InstanceAlso;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.List;
import java.util.Map;

public class StepAlso<T> extends StepHavingUnmanagedDependencies<T> {

  private final DeserializationStep<T> mainDependency;

  public StepAlso(DeserializationStep<T> mainDependency, List<DeserializationStep<?>> unmanagedDependencies) {
    super(unmanagedDependencies);
    this.mainDependency = mainDependency;
  }

  @Override
  public DeserializationStepInstance<T> instantiated(
    Map<DeserializationStep<?>, DeserializationStepInstance<?>> alreadyInstantiated
  ) {
    //noinspection unchecked
    DeserializationStepInstance<T> instance = (DeserializationStepInstance<T>) alreadyInstantiated.get(this);
    if (instance != null) return instance;
    instance =
      new InstanceAlso<>(
        mainDependency.instantiated(alreadyInstantiated),
        instantiatedDependencies(alreadyInstantiated)
      );
    alreadyInstantiated.put(this, instance);
    return instance;
  }
}
