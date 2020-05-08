package com.plcarmel.jackson.databind1467poc.example.builders;

import com.plcarmel.jackson.databind1467poc.generic.groups.StepGroupMany;
import com.plcarmel.jackson.databind1467poc.theory.Step;

import java.util.function.Function;

public class BasicBuilder<TInput, TResult> extends BuilderHavingUnmanagedDependencies<TInput, TResult> {

  private final Function<StepGroupMany<TInput>, Step<TInput, TResult>> stepConstructor;

  public BasicBuilder(Function<StepGroupMany<TInput>, Step<TInput, TResult>> stepConstructor) {
    this.stepConstructor = stepConstructor;
  }

  @Override
  public Step<TInput, TResult> build() {
    return stepConstructor.apply(new StepGroupMany<>(getDependencies()));
  }

}
