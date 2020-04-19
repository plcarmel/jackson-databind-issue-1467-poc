package com.plcarmel.jackson.databind1467poc.example.instances;

import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class InstanceAlso<T> extends InstanceHavingUnmanagedDependencies<T> {

  private DeserializationStepInstance<T> mainDependency;
  private T data;

  public InstanceAlso(
    DeserializationStepInstance<T> mainDependency,
    List<DeserializationStepInstance<?>> otherDependencies
  ) {
    super(otherDependencies);
    this.mainDependency = mainDependency;
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
  public void pushToken(JsonParser parser) throws IOException {
    // NOP
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
    return mainDependency == null && areDependenciesSatisfied();
  }

  @Override
  public void update() {
    super.update();
    if (mainDependency.isDone()) mainDependency = null;
  }

  @Override
  public List<DeserializationStepInstance<?>> getDependencies() {
    Stream<DeserializationStepInstance<?>> s1 = mainDependency != null ? Stream.of(mainDependency) : Stream.empty();
    Stream<DeserializationStepInstance<?>> s2 = super.getDependencies().stream();
    return Stream.of(s1, s2).flatMap(l -> l).collect(toList());
  }
}
