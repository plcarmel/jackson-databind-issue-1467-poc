package com.plcarmel.jackson.databind1467poc.example.steps;

import com.plcarmel.jackson.databind1467poc.example.groups.*;
import com.plcarmel.jackson.databind1467poc.example.instances.InstanceAlso;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.stream.Stream;

public class StepAlso<T>
  implements HasDependencyGroupsMixin<DeserializationStep<?>>, DeserializationStep<T>
{
  private final StepGroupOne<T> mainDependency;
  private final StepGroupMany unmanaged;

  public StepAlso(StepGroupOne<T> mainDependency, StepGroupMany unmanaged) {
    this.mainDependency = mainDependency;
    this.unmanaged = unmanaged;
  }

  @Override
  public DeserializationStepInstance<T> instantiated(InstanceFactory factory) {
    return new InstanceAlso<>(
      mainDependency.instantiatedMain(factory),
      unmanaged.instantiated(factory)
    );
  }

  @Override
  public DependencyGroups<DeserializationStep<?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(mainDependency, unmanaged));
  }
}
