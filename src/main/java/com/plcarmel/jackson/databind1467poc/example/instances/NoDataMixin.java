package com.plcarmel.jackson.databind1467poc.example.instances;

import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.NoData;

interface NoDataMixin extends DeserializationStepInstance<NoData> {

  @Override
  default NoData getData() {
    throw new RuntimeException(String.format("%s.%s should not be called", getClass().getSimpleName(), "getData"));
  }

}
