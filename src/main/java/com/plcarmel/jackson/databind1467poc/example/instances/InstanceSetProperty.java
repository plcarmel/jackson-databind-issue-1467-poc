package com.plcarmel.jackson.databind1467poc.example.instances;

import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import static com.fasterxml.jackson.core.JsonToken.FIELD_NAME;

public class InstanceSetProperty<TClass, TProperty> extends InstanceNoData {

  private final PropertyConfiguration<TClass, TProperty> propertyConfiguration;
  private DeserializationStepInstance<TClass> instantiationStep;
  private DeserializationStepInstance<TProperty> propertyDeserializationStepInstance;

  public InstanceSetProperty(
    PropertyConfiguration<TClass, TProperty> propertyConfiguration,
    DeserializationStepInstance<TClass> instantiationStep,
    DeserializationStepInstance<TProperty> propertyDeserializationStepInstance,
    List<DeserializationStepInstance<?>> otherDependencies
  ) {
    super(otherDependencies);
    this.propertyConfiguration = propertyConfiguration;
    this.instantiationStep = instantiationStep;
    this.propertyDeserializationStepInstance = propertyDeserializationStepInstance;
  }

  @Override
  public boolean isOptional() {
    return !propertyConfiguration.isRequired();
  }

  @Override
  public boolean canHandleCurrentToken(JsonParser parser) {
    try {
      return parser.currentToken() == FIELD_NAME &&
        parser.getText().equals(propertyConfiguration.getName());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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
    super.prune(onRemoved);
    if (instantiationStep.isDone()) {
      onRemoved.accept(instantiationStep);
      instantiationStep = null;
    }
    if (propertyDeserializationStepInstance.isDone()) {
      onRemoved.accept(propertyDeserializationStepInstance);
      propertyDeserializationStepInstance = null;
    }
  }
}

