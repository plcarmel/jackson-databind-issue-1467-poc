package com.plcarmel.jackson.databind1467poc.example.builders;

import com.plcarmel.jackson.databind1467poc.example.groups.StepGroupMany;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;

import java.util.function.Function;

public class BasicBuilder<T> extends BuilderHavingUnmanagedDependencies<T> {

  private final Function<StepGroupMany, DeserializationStep<T>> stepConstructor;

  public BasicBuilder(Function<StepGroupMany, DeserializationStep<T>> stepConstructor) {
    this.stepConstructor = stepConstructor;
  }

  @Override
  public DeserializationStep<T> build() {
    return stepConstructor.apply(new StepGroupMany(getDependencies()));
  }

}
