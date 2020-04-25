package com.plcarmel.jackson.databind1467poc.example.instances;

import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import static com.fasterxml.jackson.core.JsonToken.FIELD_NAME;

public class InstanceDeserializeProperty<TClass, TProperty> extends InstanceHavingUnmanagedDependencies<TProperty> {

  private final PropertyConfiguration<TClass, TProperty> propertyConfiguration;
  private DeserializationStepInstance<TProperty> valueDeserializationStep;
  private TProperty data;

  public InstanceDeserializeProperty(
    PropertyConfiguration<TClass, TProperty> propertyConfiguration,
    DeserializationStepInstance<TProperty> valueDeserializationStep,
    List<DeserializationStepInstance<?>> otherDependencies
  ) {
    super(otherDependencies);
    this.propertyConfiguration = propertyConfiguration;
    this.valueDeserializationStep = valueDeserializationStep;
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
    return valueDeserializationStep.areDependenciesSatisfied() &&
      super.areDependenciesSatisfied();
  }

  @Override
  public boolean isDone() {
    return valueDeserializationStep == null && super.areDependenciesSatisfied();
  }

  @Override
  public void prune(Consumer<DeserializationStepInstance<?>> onRemoved) {
    super.prune(onRemoved);
    if (valueDeserializationStep.isDone()) {
      data = valueDeserializationStep.getData();
      onRemoved.accept(valueDeserializationStep);
      valueDeserializationStep = null;
    }
  }

  @Override
  public TProperty getData() {
    return data;
  }
}

