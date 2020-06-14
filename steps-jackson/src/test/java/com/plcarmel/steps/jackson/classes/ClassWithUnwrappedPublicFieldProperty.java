package com.plcarmel.steps.jackson.classes;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class ClassWithUnwrappedPublicFieldProperty {
  @JsonUnwrapped
  public ClassWithPublicFieldStandardProperty y;
}
