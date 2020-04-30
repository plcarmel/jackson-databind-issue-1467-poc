package com.plcarmel.jackson.databind1467poc.example.steps;

import com.plcarmel.jackson.databind1467poc.example.groups.*;
import com.plcarmel.jackson.databind1467poc.example.instances.InstanceAlso;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StepAlso<T>
  implements HasDependencyGroupsMixin<DeserializationStep<?>>, DeserializationStep<T>
{
  private final GroupOne<DeserializationStep<T>, DeserializationStep<?>> mainDependency;
  private final StepGroupMany unmanaged;

  public StepAlso(
    GroupOne<DeserializationStep<T>, DeserializationStep<?>> mainDependency,
    StepGroupMany unmanaged
  ) {
    this.mainDependency = mainDependency;
    this.unmanaged = unmanaged;
  }

  @Override
  public DeserializationStepInstance<T> instantiated(InstanceFactory factory) {
    return new InstanceAlso<>(
      factory.instantiate(mainDependency.getMain()),
      unmanaged.instantiated(factory)
    );
  }

  @Override
  public DependencyGroups<DeserializationStep<?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(mainDependency, unmanaged));
  }
}
