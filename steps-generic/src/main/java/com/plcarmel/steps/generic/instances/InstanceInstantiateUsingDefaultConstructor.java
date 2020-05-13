package com.plcarmel.steps.generic.instances;

import com.plcarmel.steps.generic.groups.instances.InstanceDependencyGroups;
import com.plcarmel.steps.generic.groups.instances.InstanceGroupMany;
import com.plcarmel.steps.generic.instances.bases.InstanceInstantiateBase;
import com.plcarmel.steps.theory.TypeConfiguration;

import java.util.stream.Stream;

public final class InstanceInstantiateUsingDefaultConstructor<TInput, TResult>
  extends InstanceInstantiateBase<TInput, TResult>
{
  private final TypeConfiguration<TResult> typeConfiguration;

  public TypeConfiguration<TResult> getTypeConfiguration() {
    return typeConfiguration;
  }

  public InstanceInstantiateUsingDefaultConstructor(
    TypeConfiguration<TResult> typeConfiguration,
    InstanceGroupMany<TInput> unmanaged
  ) {
    super(unmanaged);
    this.typeConfiguration = typeConfiguration;
  }

  @Override
  public void execute() {
    try {
      data = typeConfiguration.getType().newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public InstanceDependencyGroups<TInput> getDependencyGroups() {
    return new InstanceDependencyGroups<>(Stream.of(unmanaged));
  }

  @Override
  public Boolean isReadyToBeExecuted() {
    return !hasBeenExecuted();
  }
}
