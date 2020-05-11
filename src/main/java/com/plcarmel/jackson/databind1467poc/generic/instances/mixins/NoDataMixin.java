package com.plcarmel.jackson.databind1467poc.generic.instances.mixins;

import com.plcarmel.jackson.databind1467poc.theory.StepInstance;
import com.plcarmel.jackson.databind1467poc.theory.NoData;

public interface NoDataMixin<TInput> extends StepInstance<TInput, NoData> {

  @Override
  default NoData getData() {
    throw new RuntimeException(String.format("%s.%s should not be called", getClass().getSimpleName(), "getData"));
  }

}
