package com.plcarmel.jackson.databind1467poc.example.instances;

import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.jackson.databind1467poc.example.configuration.FieldPropertyConfiguration;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class InstanceSetProperty<TClass, TProperty> extends InstanceNoData {

  private final PropertyConfiguration<? extends TProperty> propertyConfiguration;
  private DeserializationStepInstance<TClass> instantiationStep;
  private DeserializationStepInstance<? extends TProperty> propertyDeserializationStepInstance;

  public InstanceSetProperty(
    PropertyConfiguration<? extends TProperty> propertyConfiguration,
    DeserializationStepInstance<TClass> instantiationStep,
    DeserializationStepInstance<? extends TProperty> propertyDeserializationStepInstance,
    List<DeserializationStepInstance<?>> otherDependencies
  ) {
    super(otherDependencies);
    this.propertyConfiguration = propertyConfiguration;
    this.instantiationStep = instantiationStep;
    this.propertyDeserializationStepInstance = propertyDeserializationStepInstance;
  }

  @Override
  public List<DeserializationStepInstance<?>> getUnmanagedDependencies() {
    return Stream.of(
      super.getUnmanagedDependencies().stream(),
      Stream.of(instantiationStep, propertyDeserializationStepInstance)
    ).flatMap(s -> s) .collect(toList());
  }

  @Override
  public boolean isOptional() {
    return !propertyConfiguration.isRequired();
  }

  @Override
  public boolean canHandleCurrentToken(JsonParser parser) {
    return false;
  }

  @Override
  public void pushToken(JsonParser parser) {
    throw new RuntimeException("Method should not be called.");
  }

  @Override
  public boolean areDependenciesSatisfied() {
    return instantiationStep.areDependenciesSatisfied() &&
      propertyDeserializationStepInstance.areDependenciesSatisfied() &&
      super.areDependenciesSatisfied();
  }

  @Override
  public boolean isDone() {
    return instantiationStep == null && propertyDeserializationStepInstance == null && super.areDependenciesSatisfied();
  }

  @Override
  public void prune(Consumer<DeserializationStepInstance<?>> onRemoved) {
    if (
      instantiationStep != null &&
      propertyDeserializationStepInstance != null &&
      instantiationStep.isDone() &&
      propertyDeserializationStepInstance.isDone()
    ) {
      if (propertyConfiguration instanceof FieldPropertyConfiguration) {
        //noinspection unchecked
        FieldPropertyConfiguration<TClass, TProperty> fpc =
          (FieldPropertyConfiguration<TClass, TProperty>) propertyConfiguration;
        try {
          fpc.getField().set(instantiationStep.getData(), propertyDeserializationStepInstance.getData());
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
      onRemoved.accept(instantiationStep);
      instantiationStep = null;
      onRemoved.accept(propertyDeserializationStepInstance);
      propertyDeserializationStepInstance = null;
    }
    super.prune(onRemoved);
  }
}

