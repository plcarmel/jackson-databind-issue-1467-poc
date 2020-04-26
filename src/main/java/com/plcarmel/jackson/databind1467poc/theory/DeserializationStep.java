package com.plcarmel.jackson.databind1467poc.theory;

import java.util.Map;

public interface DeserializationStep<T> extends HasDependencies<DeserializationStep<?>> {

  DeserializationStepInstance<T> instantiated(
    Map<DeserializationStep<?>, DeserializationStepInstance<?>> alreadyInstantiated
  );

}
