package com.plcarmel.jackson.databind1467poc.generic.instances;

import com.plcarmel.jackson.databind1467poc.generic.configuration.FieldPropertyConfiguration;
import com.plcarmel.jackson.databind1467poc.generic.groups.*;
import com.plcarmel.jackson.databind1467poc.theory.StepInstance;
import com.plcarmel.jackson.databind1467poc.theory.NoData;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class InstanceSetProperty<TInput, TClass, TProperty>
  extends
  InstanceHavingUnmanagedDependencies<TInput, NoData>
  implements
    GetDependenciesMixin<StepInstance<TInput, ?>>,
    NoDataMixin<TInput>,
    AreDependenciesSatisfiedMixin<TInput, NoData>
{
  private final PropertyConfiguration<? extends TProperty> propertyConfiguration;
  private InstanceGroupOne<TInput, TClass> instantiationStep;
  private InstanceGroupOne<TInput, ? extends TProperty> deserializationStep;

  public InstanceSetProperty(
    PropertyConfiguration<? extends TProperty> propertyConfiguration,
    InstanceGroupOne<TInput, TClass> instantiationStep,
    InstanceGroupOne<TInput, ? extends TProperty> deserializationStep,
    InstanceGroupMany<TInput> unmanaged
  ) {
    super(unmanaged);
    this.propertyConfiguration = propertyConfiguration;
    this.instantiationStep = instantiationStep;
    this.deserializationStep = deserializationStep;
  }

  @Override
  public DependencyGroups<StepInstance<TInput, ?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(instantiationStep, deserializationStep, unmanaged));
  }

  @Override
  public boolean isOptional() {
    return !propertyConfiguration.isRequired();
  }

  @Override
  public boolean canHandleCurrentToken(TInput parser) {
    return false;
  }

  @Override
  public void pushToken(TInput parser) {
    throw new RuntimeException("Method should not be called.");
  }

  @Override
  public boolean isDone() {
    return instantiationStep == null &&
      deserializationStep == null &&
      ( unmanaged == null || unmanaged.areDependenciesSatisfied());
  }

  private void execute() {
    if (propertyConfiguration instanceof FieldPropertyConfiguration) {
      //noinspection unchecked
      final FieldPropertyConfiguration<TClass, TProperty> fpc =
        (FieldPropertyConfiguration<TClass, TProperty>) propertyConfiguration;
      try {
        fpc.getField().set(instantiationStep.get().getData(), deserializationStep.get().getData());
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void prune(Consumer<StepInstance<TInput, ?>> onDependencyRemoved) {
    if (instantiationStep != null) {
      instantiationStep.prune(this::doRemoveDependency, onDependencyRemoved, this);
      if (instantiationStep.get() == null) instantiationStep = null;
    }
    if (deserializationStep != null) {
      deserializationStep.prune(this::doRemoveDependency, onDependencyRemoved, this);
      if (deserializationStep.get() == null) deserializationStep = null;
    }
    super.prune(onDependencyRemoved);
  }

  private Boolean doRemoveDependency(StepInstance<TInput, ?> d) {
    if (instantiationStep == null || deserializationStep == null) return true;
    final InstanceGroupOne<TInput, ?> other = instantiationStep.get() == d ? deserializationStep : instantiationStep;
    if (!other.isDone()) return false;
    execute();
    return true;
  }
}

