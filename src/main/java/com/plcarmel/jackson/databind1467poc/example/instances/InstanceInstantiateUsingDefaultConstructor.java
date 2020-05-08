package com.plcarmel.jackson.databind1467poc.example.instances;

import com.plcarmel.jackson.databind1467poc.generic.groups.AreDependenciesSatisfiedMixin;
import com.plcarmel.jackson.databind1467poc.generic.groups.DependencyGroups;
import com.plcarmel.jackson.databind1467poc.generic.groups.GetDependenciesMixin;
import com.plcarmel.jackson.databind1467poc.generic.groups.InstanceGroupMany;
import com.plcarmel.jackson.databind1467poc.theory.StepInstance;
import com.plcarmel.jackson.databind1467poc.theory.TypeConfiguration;

import java.util.function.Consumer;
import java.util.stream.Stream;

public final class InstanceInstantiateUsingDefaultConstructor<TInput, TResult>
  extends InstanceHavingUnmanagedDependencies<TInput, TResult>
  implements GetDependenciesMixin<StepInstance<TInput, ?>>, AreDependenciesSatisfiedMixin<TInput, TResult>
{

  private final Class<TResult> typeClass;
  private TResult data;

  public InstanceInstantiateUsingDefaultConstructor(
    TypeConfiguration<TResult> typeConfiguration,
    InstanceGroupMany<TInput> unmanaged
  ) {
    super(unmanaged);
    typeClass = typeConfiguration.getTypeClass();
  }

  @Override
  public boolean canHandleCurrentToken(TInput parser) {
    return false;
  }

  @Override
  public void pushToken(TInput parser) {
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
  public TResult getData() {
    return data;
  }

  @Override
  public void prune(Consumer<StepInstance<TInput, ?>> onRemoved) {
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
  public DependencyGroups<StepInstance<TInput, ?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(unmanaged));
  }
}
