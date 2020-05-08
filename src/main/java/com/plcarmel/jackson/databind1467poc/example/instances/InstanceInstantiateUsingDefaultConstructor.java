package com.plcarmel.jackson.databind1467poc.example.instances;

import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.jackson.databind1467poc.example.groups.DependencyGroups;
import com.plcarmel.jackson.databind1467poc.example.groups.GetDependenciesMixin;
import com.plcarmel.jackson.databind1467poc.example.groups.InstanceGroupMany;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.TypeConfiguration;

import java.util.function.Consumer;
import java.util.stream.Stream;

public final class InstanceInstantiateUsingDefaultConstructor<T>
  extends InstanceHavingUnmanagedDependencies<T>
  implements GetDependenciesMixin<DeserializationStepInstance<?>>
{

  private final Class<T> typeClass;
  private T data;

  public InstanceInstantiateUsingDefaultConstructor(
    TypeConfiguration<T> typeConfiguration,
    InstanceGroupMany unmanaged
  ) {
    super(unmanaged);
    typeClass = typeConfiguration.getTypeClass();
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
  public boolean areDependenciesSatisfied() {
    return unmanaged == null || unmanaged.areDependenciesSatisfied();
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

  @Override
  public DependencyGroups<DeserializationStepInstance<?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(unmanaged));
  }
}
