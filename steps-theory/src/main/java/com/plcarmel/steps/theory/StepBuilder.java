package com.plcarmel.steps.theory;

public interface StepBuilder<TInput, TResult> extends HasDependencies<Step<TInput, ?>> {

  void addDependency(Step<TInput, ?> dependency);

  Step<TInput, TResult> build();

}
