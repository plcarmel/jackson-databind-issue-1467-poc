package com.fasterxml.jackson.databind1467poc.theory;

public interface DeserializationStepInstance<T>
  extends HasDependencies<DeserializationStepInstance<?>>, TokenConsumer
{
  boolean isOptional();
  boolean isDone();
  boolean areDependenciesSatisfied();

  T getData();

  void update();

}
