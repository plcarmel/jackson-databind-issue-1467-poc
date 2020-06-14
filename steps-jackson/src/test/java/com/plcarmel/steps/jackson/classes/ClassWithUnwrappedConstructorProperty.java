package com.plcarmel.steps.jackson.classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class ClassWithUnwrappedConstructorProperty {
  public final ClassWithPublicFieldStandardProperty hello;
  @JsonCreator
  public ClassWithUnwrappedConstructorProperty(
    @JsonUnwrapped @JsonProperty("world") ClassWithPublicFieldStandardProperty hello
  ) {
    this.hello = hello;
  }
}
