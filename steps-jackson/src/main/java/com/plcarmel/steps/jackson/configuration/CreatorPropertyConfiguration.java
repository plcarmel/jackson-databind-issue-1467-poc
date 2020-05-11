package com.plcarmel.steps.jackson.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.plcarmel.steps.theory.PropertyConfiguration;
import com.plcarmel.steps.theory.TypeConfiguration;

import java.lang.reflect.Parameter;

public class CreatorPropertyConfiguration<TClass, TValue> implements PropertyConfiguration<TClass, TValue> {

  private final Parameter parameter;

  public CreatorPropertyConfiguration(Parameter parameter) {
    this.parameter = parameter;
    if (parameter.getAnnotationsByType(JsonProperty.class).length != 1) {
      throw new RuntimeException("Multiple JsonProperty on parameter %s");
    }
  }

  @Override
  public String getName() {
    return parameter.getAnnotationsByType(JsonProperty.class)[0].value();
  }

  @Override
  public TypeConfiguration<TValue> getTypeConfiguration() {
    //noinspection unchecked
    return (TypeConfiguration<TValue>)
      CachedTypeConfigurationFactory.getInstance().getTypeConfiguration(parameter.getClass());
  }

  @Override
  public boolean isRequired() {
    return true;
  }

  @Override
  public boolean isUnwrapped() {
    return parameter.getAnnotationsByType(JsonUnwrapped.class).length != 0;
  }

}
