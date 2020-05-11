package com.plcarmel.steps.theory;

public interface InstanceFactory<TInput> {

  <TResult> StepInstance<TInput, TResult> instantiate(Step<TInput, TResult> step);

}
