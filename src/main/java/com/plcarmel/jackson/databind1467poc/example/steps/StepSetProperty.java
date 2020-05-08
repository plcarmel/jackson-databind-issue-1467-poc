package com.plcarmel.jackson.databind1467poc.example.steps;

import com.plcarmel.jackson.databind1467poc.generic.groups.*;
import com.plcarmel.jackson.databind1467poc.example.instances.InstanceSetProperty;
import com.plcarmel.jackson.databind1467poc.theory.*;

import java.util.stream.Stream;

public class StepSetProperty<TInput, TClass, TProperty>
  implements Step<TInput, NoData>, GetDependenciesMixin<Step<TInput, ?>>
{
  private final PropertyConfiguration<? extends TProperty> propertyConfiguration;
  private final StepGroupTwo<TInput, TClass, ? extends TProperty> managed;
  private final StepGroupMany<TInput> unmanaged;

  public StepSetProperty(
    PropertyConfiguration<? extends TProperty> propertyConfiguration,
    StepGroupTwo<TInput, TClass, ? extends TProperty> managed,
    StepGroupMany<TInput> unmanaged
  ) {
    this.propertyConfiguration = propertyConfiguration;
    this.managed = managed;
    this.unmanaged = unmanaged;
  }

  @Override
  public StepInstance<TInput, NoData> instantiated(InstanceFactory<TInput> factory) {
    return new InstanceSetProperty<>(
      propertyConfiguration,
      managed.instantiated(factory),
      unmanaged.instantiated(factory)
    );
  }

  @Override
  public DependencyGroups<Step<TInput, ?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(managed, unmanaged));
  }
}
