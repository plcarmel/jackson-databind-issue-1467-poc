package com.plcarmel.jackson.databind1467poc.example.steps;

import com.plcarmel.jackson.databind1467poc.generic.groups.DependencyGroups;
import com.plcarmel.jackson.databind1467poc.generic.groups.GetDependenciesMixin;
import com.plcarmel.jackson.databind1467poc.generic.groups.StepGroupMany;
import com.plcarmel.jackson.databind1467poc.example.instances.InstanceInstantiateUsingDefaultConstructor;
import com.plcarmel.jackson.databind1467poc.theory.InstanceFactory;
import com.plcarmel.jackson.databind1467poc.theory.Step;
import com.plcarmel.jackson.databind1467poc.theory.StepInstance;
import com.plcarmel.jackson.databind1467poc.theory.TypeConfiguration;

import java.util.stream.Stream;

public class StepInstantiateUsingDefaultConstructor<TInput, TResult>
  implements Step<TInput, TResult>, GetDependenciesMixin<Step<TInput, ?>>
{
  private final TypeConfiguration<TResult> conf;
  private final StepGroupMany<TInput> unmanaged;

  public StepInstantiateUsingDefaultConstructor(TypeConfiguration<TResult> conf, StepGroupMany<TInput> unmanaged) {
    this.conf = conf;
    this.unmanaged = unmanaged;
  }

  @Override
  public StepInstance<TInput, TResult> instantiated(InstanceFactory<TInput> factory) {
    return new InstanceInstantiateUsingDefaultConstructor<>(conf, unmanaged.instantiated(factory));
  }

  @Override
  public DependencyGroups<Step<TInput, ?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(unmanaged));
  }
}
