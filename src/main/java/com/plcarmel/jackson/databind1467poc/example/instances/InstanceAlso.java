package com.plcarmel.jackson.databind1467poc.example.instances;

import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.jackson.databind1467poc.example.groups.DependencyGroups;
import com.plcarmel.jackson.databind1467poc.example.groups.GetDependenciesMixin;
import com.plcarmel.jackson.databind1467poc.example.groups.InstanceGroupMany;
import com.plcarmel.jackson.databind1467poc.example.groups.InstanceGroupOne;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class InstanceAlso<T> extends InstanceBase<T>
  implements DeserializationStepInstance<T>, GetDependenciesMixin<DeserializationStepInstance<?>> {

  private InstanceGroupOne<T> managed;
  private InstanceGroupMany unmanaged;
  private T data;

  public InstanceAlso(
    InstanceGroupOne<T> managed,
    InstanceGroupMany unmanaged
  ) {
    this.managed = managed;
    this.unmanaged = unmanaged;
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
    return getDependencies().stream().allMatch(d -> d.isOptional() || d.areDependenciesSatisfied());
  }

  @Override
  public T getData() {
    return data;
  }

  @Override
  public boolean isDone() {
    return managed == null &&
      ( unmanaged == null ||
        unmanaged.getDependencies().stream().allMatch(d -> d.isOptional() || d.areDependenciesSatisfied())
      );
  }

  @Override
  public void prune(Consumer<DeserializationStepInstance<?>> onRemoved) {
    if (managed != null) {
      managed.prune(() -> { data = managed.getMain().getData(); return true; }, onRemoved, this);
      if (managed.getDependencies().stream().allMatch(DeserializationStepInstance::isDone)) {
        managed = null;
      }
    }
    if (unmanaged != null) {
      unmanaged.prune(() -> true, onRemoved, this);
      if (unmanaged.getDependencies().stream().allMatch(DeserializationStepInstance::isDone)) {
        unmanaged = null;
      }
    }
    if (isDone()) {
      new ArrayList<>(getParents()).forEach(p -> p.prune(onRemoved));
    }
  }

  @Override
  public DependencyGroups<DeserializationStepInstance<?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(managed, unmanaged));
  }
}
