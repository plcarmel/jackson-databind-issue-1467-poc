package com.plcarmel.jackson.databind1467poc.example.instances;

import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.jackson.databind1467poc.example.configuration.FieldPropertyConfiguration;
import com.plcarmel.jackson.databind1467poc.example.groups.DependencyGroups;
import com.plcarmel.jackson.databind1467poc.example.groups.GetDependenciesMixin;
import com.plcarmel.jackson.databind1467poc.example.groups.InstanceGroupMany;
import com.plcarmel.jackson.databind1467poc.example.groups.InstanceGroupTwo;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.NoData;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class InstanceSetProperty<TClass, TProperty>
  extends InstanceBase<NoData>
  implements GetDependenciesMixin<DeserializationStepInstance<?>>
{
  private final PropertyConfiguration<? extends TProperty> propertyConfiguration;
  private InstanceGroupTwo<TClass, ? extends TProperty> managed;
  private InstanceGroupMany unmanaged;

  public InstanceSetProperty(
    PropertyConfiguration<? extends TProperty> propertyConfiguration,
    InstanceGroupTwo<TClass, ? extends TProperty> managed,
    InstanceGroupMany unmanaged
  ) {
    this.propertyConfiguration = propertyConfiguration;
    this.managed = managed;
    this.unmanaged = unmanaged;
  }

  @Override
  public DependencyGroups<DeserializationStepInstance<?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(managed, unmanaged));
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
    return getDependencies().stream().allMatch(DeserializationStepInstance::areDependenciesSatisfied);
  }

  @Override
  public boolean isDone() {
    return managed == null &&
      ( unmanaged == null ||
        unmanaged.getDependencies().stream().allMatch(DeserializationStepInstance::areDependenciesSatisfied)
      );
  }

  private void execute() {
    if (propertyConfiguration instanceof FieldPropertyConfiguration) {
      //noinspection unchecked
      final FieldPropertyConfiguration<TClass, TProperty> fpc =
        (FieldPropertyConfiguration<TClass, TProperty>) propertyConfiguration;
      try {
        fpc.getField().set(managed.getFirst().getData(), managed.getSecond().getData());
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void prune(Consumer<DeserializationStepInstance<?>> onRemoved) {
    if (managed != null) {
      managed.prune(
        () -> {
          if (!managed.getFirst().isDone() || !managed.getSecond().isDone()) {
            return false;
          }
          execute();
          managed = null;
          return true;
        },
        onRemoved,
        this
      );
    }
    if (unmanaged != null) {
      unmanaged.prune(() -> true, onRemoved, this);
      if (unmanaged.isDone()) unmanaged = null;
    }
    if (isDone()) {
      new ArrayList<>(getParents()).forEach(p -> p.prune(onRemoved));
    }
  }

  @Override
  public NoData getData() {
    throw new RuntimeException(String.format("%s.%s should not be called", getClass().getSimpleName(), "getData"));
  }
}

