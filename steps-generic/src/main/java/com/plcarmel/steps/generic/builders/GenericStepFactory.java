package com.plcarmel.steps.generic.builders;

import com.plcarmel.steps.generic.groups.steps.StepGroupMany;
import com.plcarmel.steps.generic.groups.steps.StepGroupOne;
import com.plcarmel.steps.generic.steps.StepAlso;
import com.plcarmel.steps.generic.steps.StepInstantiateUsingCreator;
import com.plcarmel.steps.generic.steps.StepInstantiateUsingDefaultConstructor;
import com.plcarmel.steps.generic.steps.StepSetProperty;
import com.plcarmel.steps.theory.*;

import static java.util.stream.Collectors.toList;

public abstract class GenericStepFactory<TInput, TToken> implements StepFactory<TInput, TToken> {

  public <TResult> StepBuilder<TInput, TResult> builderStepAlso(Step<TInput, TResult> mainDependency) {
    return new UnmanagedDependenciesBuilder<>(u -> new StepAlso<>(new StepGroupOne<>(mainDependency), u));
  }

  public <T> StepBuilder<TInput, ? extends T> builderInstantiateUsingDefaultConstructor(TypeConfiguration<T> conf) {
    return new UnmanagedDependenciesBuilder<>(u -> new StepInstantiateUsingDefaultConstructor<>(conf, u));
  }

  public <TClass> StepBuilder<TInput, ? extends TClass> builderInstantiateUsing(
    Step<TInput, NoData> upperStartObject,
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
            .map(c -> (StepBuilder<TInput, ?>) this.builderDeserializeProperty(upperStartObject, c))
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
