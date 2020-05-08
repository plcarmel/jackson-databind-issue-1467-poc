package com.plcarmel.jackson.databind1467poc.theory;

public interface InstanceFactory<TInput> {
  <TResult> StepInstance<TInput, TResult> instantiate(Step<TInput, TResult> step);
}
