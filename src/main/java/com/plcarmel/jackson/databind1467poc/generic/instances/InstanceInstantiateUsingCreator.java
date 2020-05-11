package com.plcarmel.jackson.databind1467poc.generic.instances;

import com.plcarmel.jackson.databind1467poc.generic.groups.instances.InstanceDependencyGroups;
import com.plcarmel.jackson.databind1467poc.generic.groups.instances.InstanceGroupMany;
import com.plcarmel.jackson.databind1467poc.generic.instances.bases.InstanceInstantiateBase;
import com.plcarmel.jackson.databind1467poc.theory.CreatorConfiguration;
import com.plcarmel.jackson.databind1467poc.theory.DataSupplier;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public final class InstanceInstantiateUsingCreator<TInput, TResult>
  extends InstanceInstantiateBase<TInput, TResult>
{
  private TResult data;
  private final CreatorConfiguration<TResult> conf;
  private final InstanceGroupMany<TInput> parameters;

  public InstanceInstantiateUsingCreator(
    CreatorConfiguration<TResult> conf,
    InstanceGroupMany<TInput> parameters,
    InstanceGroupMany<TInput> unmanaged
  ) {
    super(unmanaged);
    this.conf = conf;
    this.parameters = parameters;
  }

  @Override
  public void execute() {
    data = conf.instantiate(parameters.getList().stream().map(DataSupplier::getData).collect(toList()));
  }

  @Override
  public TResult getData() {
    return data;
  }

  @Override
  public InstanceDependencyGroups<TInput> getDependencyGroups() {
    return new InstanceDependencyGroups<>(Stream.of(parameters, unmanaged));
  }

  @Override
  public Boolean isReadyToBeExecuted() {
    return parameters.allDone();
  }
}
