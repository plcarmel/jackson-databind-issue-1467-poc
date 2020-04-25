package com.plcarmel.jackson.databind1467poc.example.instances;

import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public final class InstanceAlso<T> extends InstanceHavingUnmanagedDependencies<T> {

  private DeserializationStepInstance<T> mainDependency;
  private T data;

  public InstanceAlso(
    DeserializationStepInstance<T> mainDependency,
    List<DeserializationStepInstance<?>> otherDependencies
  ) {
    super(otherDependencies);
    this.mainDependency = mainDependency;
    this.registerAsParent();
  }

  @Override
  public boolean isOptional() {
    return false;
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
    return (mainDependency == null || mainDependency.isOptional()) &&
      super.areDependenciesSatisfied();
  }

  @Override
  public T getData() {
    return data;
  }

  @Override
  public boolean isDone() {
    return mainDependency == null && super.areDependenciesSatisfied();
  }

  @Override
  public void prune(Consumer<DeserializationStepInstance<?>> onRemoved) {
    if ( mainDependency != null && mainDependency.isDone()) {
      data = mainDependency.getData();
      mainDependency.removeParent(this);
      onRemoved.accept(mainDependency);
      mainDependency = null;
    }
    super.prune(onRemoved);
  }

  @Override
  public List<DeserializationStepInstance<?>> getDependencies() {
    Stream<DeserializationStepInstance<?>> s1 = mainDependency != null ? Stream.of(mainDependency) : Stream.empty();
    Stream<DeserializationStepInstance<?>> s2 = super.getDependencies().stream();
    return Stream.of(s1, s2).flatMap(l -> l).collect(toList());
  }
}
