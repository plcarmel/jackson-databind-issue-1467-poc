package com.plcarmel.jackson.databind1467poc.generic.builders;

import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.jackson.databind1467poc.generic.groups.steps.StepGroupMany;
import com.plcarmel.jackson.databind1467poc.generic.groups.steps.StepGroupOne;
import com.plcarmel.jackson.databind1467poc.generic.steps.StepAlso;
import com.plcarmel.jackson.databind1467poc.generic.steps.StepInstantiateUsingCreator;
import com.plcarmel.jackson.databind1467poc.generic.steps.StepInstantiateUsingDefaultConstructor;
import com.plcarmel.jackson.databind1467poc.generic.steps.StepSetProperty;
import com.plcarmel.jackson.databind1467poc.theory.*;

import static java.util.stream.Collectors.toList;

public abstract class GenericStepFactory<TInput> implements StepFactory<TInput> {

  public <TResult> StepBuilder<TInput, TResult> builderStepAlso(Step<TInput, TResult> mainDependency) {
    return new UnmanagedDependenciesBuilder<>(u -> new StepAlso<>(new StepGroupOne<>(mainDependency), u));
  }

  public <T> StepBuilder<TInput, ? extends T> builderInstantiateUsingDefaultConstructor(TypeConfiguration<T> conf) {
    return new UnmanagedDependenciesBuilder<>(u -> new StepInstantiateUsingDefaultConstructor<>(conf, u));
  }

  public <TClass> StepBuilder<TInput, ? extends TClass> builderInstantiateUsing(
    CreatorConfiguration<TClass> creatorConf
  ) {
    return new UnmanagedDependenciesBuilder<>(unmanaged ->
      new StepInstantiateUsingCreator<>(
        creatorConf,
        new StepGroupMany<>(
          true,
          creatorConf
            .getParamConfigurations()
            .stream()
            .map(this::builderDeserializeProperty)
            .map(StepBuilder::build)
            .collect(toList())
        ),
        unmanaged
      )
    );
  }

  @Override
  public <TClass, TValue> StepBuilder<TInput, NoData> builderSetProperty(
    SettablePropertyConfiguration<TClass, TValue> conf,
    Step<TInput, ? extends TClass> instantiationStep,
    Step<TInput, ? extends TValue> valueDeserializationStep
  ) {
    return new UnmanagedDependenciesBuilder<>(unmanaged ->
      new StepSetProperty<>(
        conf,
        new StepGroupOne<>(instantiationStep),
        new StepGroupOne<>(valueDeserializationStep),
        unmanaged
      )
    );
  }

  @Override
  public <T> StepBuilder<TInput, T> builderDeserializeArray(TypeConfiguration<T> conf) {
    throw new RuntimeException("Not implemented");
  }

}
