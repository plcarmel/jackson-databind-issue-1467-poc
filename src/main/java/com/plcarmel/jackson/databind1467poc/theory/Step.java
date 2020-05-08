package com.plcarmel.jackson.databind1467poc.theory;

import java.util.HashMap;
import java.util.Map;

public interface Step<TInput, TResult> extends HasDependencies<Step<TInput, ?>> {

  StepInstance<TInput, TResult> instantiated(InstanceFactory<TInput> factory);

  default StepInstance<TInput, TResult> instantiated(
    Map<Step<TInput, ?>, StepInstance<TInput, ?>> alreadyInstantiated
  ) {
    //noinspection unchecked
    StepInstance<TInput, TResult> instance =
      (StepInstance<TInput, TResult>) alreadyInstantiated.get(this);
    if (instance != null) return instance;
    instance =
      instantiated(
        new InstanceFactory<TInput>() {
          @Override
          public <U> StepInstance<TInput, U> instantiate(Step<TInput, U> step) {
            return step.instantiated(alreadyInstantiated);
          }
        }
      );
    alreadyInstantiated.put(this, instance);
    return instance;
  }

  default StepInstance<TInput, TResult> instantiated() {
    return instantiated(new HashMap<>());
  }

}
