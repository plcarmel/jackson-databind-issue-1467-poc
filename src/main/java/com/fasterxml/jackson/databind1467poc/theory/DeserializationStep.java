package com.fasterxml.jackson.databind1467poc.theory;

public interface DeserializationStep<T> extends HasDependencies<DeserializationStep<?>> {

  DeserializationStepInstance<T> instantiated();

}
