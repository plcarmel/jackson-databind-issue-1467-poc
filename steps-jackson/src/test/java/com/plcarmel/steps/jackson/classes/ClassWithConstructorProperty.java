package com.plcarmel.steps.jackson.classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassWithConstructorProperty {
  public final int w;
  @JsonCreator
  public ClassWithConstructorProperty(@JsonProperty("z") int x) {
    w = x;
  }
}
