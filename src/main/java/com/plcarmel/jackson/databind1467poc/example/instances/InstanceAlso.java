package com.plcarmel.jackson.databind1467poc.example.instances;

import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.jackson.databind1467poc.example.groups.DependencyGroups;
import com.plcarmel.jackson.databind1467poc.example.groups.GetDependenciesMixin;
import com.plcarmel.jackson.databind1467poc.example.groups.InstanceGroupMany;
import com.plcarmel.jackson.databind1467poc.example.groups.InstanceGroupOne;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.function.Consumer;
import java.util.stream.Stream;

public final class InstanceAlso<T>
  extends InstanceHavingUnmanagedDependencies<T>
  implements GetDependenciesMixin<DeserializationStepInstance<?>>
{
  private InstanceGroupOne<T> managed;
  private T data;

  public InstanceAlso(
    InstanceGroupOne<T> managed,
    InstanceGroupMany unmanaged
  ) {
    super(unmanaged);
    this.managed = managed;
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
    return (managed == null || managed.areDependenciesSatisfied()) &&
      (unmanaged == null || unmanaged.areDependenciesSatisfied());
  }

  @Override
  public T getData() {
    return data;
  }

  @Override
  public boolean isDone() {
    return managed == null && (unmanaged == null || unmanaged.areDependenciesSatisfied());
  }

  private void execute() {
    data = managed.getMain().getData();
  }

  @Override
  public void prune(Consumer<DeserializationStepInstance<?>> onDependencyRemoved) {
    if (managed != null) {
      managed.prune(() -> { execute(); return true; }, onDependencyRemoved, this);
      if (managed.isDone()) managed = null;
    }
    super.prune(onDependencyRemoved);
  }

  @Override
  public DependencyGroups<DeserializationStepInstance<?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(managed, unmanaged));
  }

}
