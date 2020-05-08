package com.plcarmel.jackson.databind1467poc.example.steps;

import com.plcarmel.jackson.databind1467poc.example.groups.DependencyGroups;
import com.plcarmel.jackson.databind1467poc.example.groups.GetDependenciesMixin;
import com.plcarmel.jackson.databind1467poc.example.groups.StepGroupMany;
import com.plcarmel.jackson.databind1467poc.example.instances.InstanceInstantiateUsingDefaultConstructor;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.TypeConfiguration;

import java.util.stream.Stream;

public class StepInstantiateUsingDefaultConstructor<T>
  implements DeserializationStep<T>, GetDependenciesMixin<DeserializationStep<?>>
{
  private final TypeConfiguration<T> conf;
  private final StepGroupMany unmanaged;

  public StepInstantiateUsingDefaultConstructor(TypeConfiguration<T> conf, StepGroupMany unmanaged) {
    this.conf = conf;
    this.unmanaged = unmanaged;
  }

  @Override
  public DeserializationStepInstance<T> instantiated(InstanceFactory factory) {
    return new InstanceInstantiateUsingDefaultConstructor<>(conf, unmanaged.instantiated(factory).getDependencies() /* tmp */);
  }

  @Override
  public DependencyGroups<DeserializationStep<?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(unmanaged));
  }
}
