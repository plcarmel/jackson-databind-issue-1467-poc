package com.plcarmel.jackson.databind1467poc.example.instances;

import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.NoData;

import java.util.List;

public abstract class InstanceNoData extends InstanceHavingUnmanagedDependencies<NoData> {

  public InstanceNoData(List<DeserializationStepInstance<?>> dependencies) {
    super(dependencies);
  }

  @Override
  public final NoData getData() {
    throw new RuntimeException(String.format("%s.%s should not be called", getClass().getSimpleName(), "getData"));
  }

}
