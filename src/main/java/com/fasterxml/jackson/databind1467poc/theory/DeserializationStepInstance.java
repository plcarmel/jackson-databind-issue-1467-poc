package com.fasterxml.jackson.databind1467poc.theory;

public interface DeserializationStepInstance<T>
  extends HasDependencies<DeserializationStepInstance<?>>, TokenConsumer
{

  boolean isDone();

  T getData();

  void executeLocally();

}
