package com.plcarmel.steps.generic.instances.mixins;

import com.plcarmel.steps.theory.StepInstance;
import com.plcarmel.steps.theory.NoData;

public interface NoDataMixin<TInput> extends StepInstance<TInput, NoData> {

  @Override
  default NoData getData() {
    throw new RuntimeException(String.format("%s.%s should not be called", getClass().getSimpleName(), "getData"));
  }

}
