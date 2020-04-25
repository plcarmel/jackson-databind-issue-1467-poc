package com.plcarmel.jackson.databind1467poc.example.instances;

import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.TypeConfiguration;

import java.util.List;
import java.util.function.Consumer;

public final class InstanceInstantiateUsingDefaultConstructor<T> extends InstanceHavingUnmanagedDependencies<T> {

  private final Class<T> typeClass;
  private T data;

  public InstanceInstantiateUsingDefaultConstructor(
    TypeConfiguration<T> typeConfiguration,
    List<DeserializationStepInstance<?>> dependencies
  ) {
    super(dependencies);
    typeClass = typeConfiguration.getTypeClass();
    this.registerAsParent();
  }

  @Override
  public boolean canHandleCurrentToken(JsonParser parser) {
    return false;
  }

  @Override
  public void pushToken(JsonParser parser) {
    throw new RuntimeException("Not supposed to be called.");
  }

  @Override
  public boolean isOptional() {
    return false;
  }

  @Override
  public boolean isDone() {
    return data != null;
  }

  @Override
  public T getData() {
    return data;
  }

  @Override
  public void prune(Consumer<DeserializationStepInstance<?>> onRemoved) {
    if (data == null) {
      try {
        data = typeClass.newInstance();
      } catch (InstantiationException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
    super.prune(onRemoved);
  }

}
