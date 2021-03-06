package com.plcarmel.steps.generic.steps;

import com.plcarmel.steps.generic.groups.*;
import com.plcarmel.steps.generic.groups.mixins.GetDependenciesMixin;
import com.plcarmel.steps.generic.groups.steps.StepGroupMany;
import com.plcarmel.steps.generic.groups.steps.StepGroupOne;
import com.plcarmel.steps.generic.instances.InstanceSetProperty;
import com.plcarmel.steps.theory.*;

import java.util.stream.Stream;

public class StepSetProperty<TInput, TClass, TValue>
  implements Step<TInput, NoData>, GetDependenciesMixin<Group<Step<TInput, ?>>, Step<TInput, ?>>
{
  private final SettablePropertyConfiguration<TClass, TValue> propertyConfiguration;
  private final StepGroupOne<TInput, ? extends TClass> instantiationStep;
  private final StepGroupOne<TInput, ? extends TValue> deserializationStep;
  private final StepGroupMany<TInput> unmanaged;

  public StepSetProperty(
    SettablePropertyConfiguration<TClass, TValue> propertyConfiguration,
    StepGroupOne<TInput, ? extends TClass> instantiationStep,
    StepGroupOne<TInput, ? extends TValue> deserializationStep,
    StepGroupMany<TInput> unmanaged
  ) {
    this.propertyConfiguration = propertyConfiguration;
    this.instantiationStep = instantiationStep;
    this.deserializationStep = deserializationStep;
    this.unmanaged = unmanaged;
  }

  @Override
  public StepInstance<TInput, NoData> instantiated(InstanceFactory<TInput> factory) {
    return new InstanceSetProperty<>(
      propertyConfiguration,
      instantiationStep.instantiated(factory),
      deserializationStep.instantiated(factory),
      unmanaged.instantiated(factory)
    );
  }

  @Override
  public DependencyGroups<Group<Step<TInput, ?>>, Step<TInput, ?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(instantiationStep, deserializationStep, unmanaged));
  }
}
