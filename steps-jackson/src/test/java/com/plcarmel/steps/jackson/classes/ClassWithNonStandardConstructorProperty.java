package com.plcarmel.steps.jackson.classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassWithNonStandardConstructorProperty {
  public final ClassWithPublicFieldStandardProperty hello;
  @JsonCreator
  public ClassWithNonStandardConstructorProperty(
    @JsonProperty("world") ClassWithPublicFieldStandardProperty hello
  ) {
    this.hello = hello;
  }
}
