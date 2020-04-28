package com.plcarmel.jackson.databind1467poc.theory;

import java.util.Map;

public interface DeserializationStep<T> extends HasDependencies<DeserializationStep<?>> {

  interface InstanceFactory {
    <U> DeserializationStepInstance<U> instantiate(DeserializationStep<U> step);
  }

  DeserializationStepInstance<T> instantiated(InstanceFactory dependenciesInstanceFactory);

  default DeserializationStepInstance<T> instantiated(
    Map<DeserializationStep<?>, DeserializationStepInstance<?>> alreadyInstantiated
  ) {
    //noinspection unchecked
    DeserializationStepInstance<T> instance = (DeserializationStepInstance<T>) alreadyInstantiated.get(this);
    if (instance != null) return instance;
    instance =
      instantiated(
        new InstanceFactory() {
          @Override
          public <U> DeserializationStepInstance<U> instantiate(DeserializationStep<U> step) {
            return step.instantiated(alreadyInstantiated);
          }
        }
      );
    alreadyInstantiated.put(this, instance);
    return instance;
  }

}
