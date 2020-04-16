package com.fasterxml.jackson.databind1467poc.theory;

public interface DeserializationStepBuilder<T> extends HasDependencies<DeserializationStep<?>>
{
  void addDependency(DeserializationStep<?> dependency);

  DeserializationStep<T> build();
}
