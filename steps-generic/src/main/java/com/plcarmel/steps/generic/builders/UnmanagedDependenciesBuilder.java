package com.plcarmel.steps.generic.builders;

import com.plcarmel.steps.generic.groups.steps.StepGroupMany;
import com.plcarmel.steps.theory.Step;

import java.util.function.Function;

public class UnmanagedDependenciesBuilder<TInput, TResult> extends BuilderHavingUnmanagedDependencies<TInput, TResult> {

  private final Function<StepGroupMany<TInput>, Step<TInput, TResult>> stepConstructor;

  public UnmanagedDependenciesBuilder(Function<StepGroupMany<TInput>, Step<TInput, TResult>> stepConstructor) {
    this.stepConstructor = stepConstructor;
  }

  @Override
  public Step<TInput, TResult> build() {
    return stepConstructor.apply(new StepGroupMany<>(false, getDependencies()));
  }

}
