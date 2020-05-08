package com.plcarmel.jackson.databind1467poc.example.steps;

import com.plcarmel.jackson.databind1467poc.generic.groups.*;
import com.plcarmel.jackson.databind1467poc.example.instances.InstanceAlso;
import com.plcarmel.jackson.databind1467poc.theory.InstanceFactory;
import com.plcarmel.jackson.databind1467poc.theory.Step;
import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

import java.util.stream.Stream;

public class StepAlso<TInput, TResult>
  implements GetDependenciesMixin<Step<TInput, ?>>, Step<TInput, TResult>
{
  private final StepGroupOne<TInput, TResult> managed;
  private final StepGroupMany<TInput> unmanaged;

  public StepAlso(StepGroupOne<TInput, TResult> managed, StepGroupMany<TInput> unmanaged) {
    this.managed = managed;
    this.unmanaged = unmanaged;
  }

  @Override
  public StepInstance<TInput, TResult> instantiated(InstanceFactory<TInput> factory) {
    return new InstanceAlso<>(
      managed.instantiated(factory),
      unmanaged.instantiated(factory)
    );
  }

  @Override
  public DependencyGroups<Step<TInput, ?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(managed, unmanaged));
  }
}
