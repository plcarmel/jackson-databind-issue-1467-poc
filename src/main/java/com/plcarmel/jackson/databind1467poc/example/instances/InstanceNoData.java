package com.plcarmel.jackson.databind1467poc.example.instances;

import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.False;

import java.util.List;

public abstract class InstanceNoData extends InstanceHavingUnmanagedDependencies<False> {

  public InstanceNoData(List<DeserializationStepInstance<?>> dependencies) {
    super(dependencies);
  }

  @Override
  public final False getData() {
    throw new RuntimeException(String.format("%s.%s should not be called", getClass().getSimpleName(), "getData"));
  }

}
