package com.plcarmel.jackson.databind1467poc.generic.instances;

import com.plcarmel.jackson.databind1467poc.generic.groups.*;
import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

import java.util.function.Consumer;
import java.util.stream.Stream;

public final class InstanceAlso<TInput, TResult>
  extends InstanceHavingUnmanagedDependencies<TInput, TResult>
  implements GetDependenciesMixin<StepInstance<TInput, ?>>, AreDependenciesSatisfiedMixin<TInput, TResult>
{
  private InstanceGroupOne<TInput, TResult> managed;
  private TResult data;

  public InstanceAlso(
    InstanceGroupOne<TInput, TResult> managed,
    InstanceGroupMany<TInput> unmanaged
  ) {
    super(unmanaged);
    this.managed = managed;
  }

  @Override
  public boolean isOptional() {
    return false;
  }

  @Override
  public boolean canHandleCurrentToken(TInput parser) {
    return false;
  }

  @Override
  public void pushToken(TInput input) {
    throw new RuntimeException("Method should not be called.");
  }

  @Override
  public TResult getData() {
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
  public void prune(Consumer<StepInstance<TInput, ?>> onDependencyRemoved) {
    if (managed != null) {
      managed.prune(() -> { execute(); return true; }, onDependencyRemoved, this);
      if (managed.isDone()) managed = null;
    }
    super.prune(onDependencyRemoved);
  }

  @Override
  public DependencyGroups<StepInstance<TInput, ?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(managed, unmanaged));
  }

}
