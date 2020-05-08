package com.plcarmel.jackson.databind1467poc.example.instances;

import com.plcarmel.jackson.databind1467poc.example.configuration.FieldPropertyConfiguration;
import com.plcarmel.jackson.databind1467poc.generic.groups.*;
import com.plcarmel.jackson.databind1467poc.generic.instances.NoDataMixin;
import com.plcarmel.jackson.databind1467poc.theory.StepInstance;
import com.plcarmel.jackson.databind1467poc.theory.NoData;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class InstanceSetProperty<TInput, TClass, TProperty>
  extends
  InstanceHavingUnmanagedDependencies<TInput, NoData>
  implements
    GetDependenciesMixin<StepInstance<TInput, ?>>,
    NoDataMixin<TInput>,
    AreDependenciesSatisfiedMixin<TInput, NoData>
{
  private final PropertyConfiguration<? extends TProperty> propertyConfiguration;
  private InstanceGroupTwo<TInput, TClass, ? extends TProperty> managed;

  public InstanceSetProperty(
    PropertyConfiguration<? extends TProperty> propertyConfiguration,
    InstanceGroupTwo<TInput, TClass, ? extends TProperty> managed,
    InstanceGroupMany<TInput> unmanaged
  ) {
    super(unmanaged);
    this.propertyConfiguration = propertyConfiguration;
    this.managed = managed;
  }

  @Override
  public DependencyGroups<StepInstance<TInput, ?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(managed, unmanaged));
  }

  @Override
  public boolean isOptional() {
    return !propertyConfiguration.isRequired();
  }

  @Override
  public boolean canHandleCurrentToken(TInput parser) {
    return false;
  }

  @Override
  public void pushToken(TInput parser) {
    throw new RuntimeException("Method should not be called.");
  }

  @Override
  public boolean areDependenciesSatisfied() {
    return (managed == null || managed.areDependenciesSatisfied())
      && (unmanaged == null || unmanaged.areDependenciesSatisfied());
  }

  @Override
  public boolean isDone() {
    return managed == null &&
      ( unmanaged == null ||
        unmanaged.getDependencies().stream().allMatch(StepInstance::areDependenciesSatisfied)
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
  public void prune(Consumer<StepInstance<TInput, ?>> onDependencyRemoved) {
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
        onDependencyRemoved,
        this
      );
    }
    super.prune(onDependencyRemoved);
  }
}

