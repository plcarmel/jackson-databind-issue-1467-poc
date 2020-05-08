package com.plcarmel.jackson.databind1467poc.generic.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;
import com.plcarmel.jackson.databind1467poc.theory.TypeConfiguration;

import java.lang.reflect.Field;
import java.util.Arrays;

public class FieldPropertyConfiguration<TClass, TProperty> implements PropertyConfiguration<TProperty> {

  private final Field field;
  private final TypeConfiguration<TProperty> typeConfiguration;
  private final boolean isRequired;

  public FieldPropertyConfiguration(Field field) {
    this.field = field;
    isRequired =
      Arrays
        .stream(field.getAnnotationsByType(JsonProperty.class))
        .anyMatch(JsonProperty::required);
    //noinspection unchecked
    typeConfiguration =
      (TypeConfiguration<TProperty>) CachedTypeConfigurationFactory
        .getInstance()
        .getTypeConfiguration(field.getType());
  }

  @Override
  public String getName() {
    return field.getName();
  }

  public Field getField() { return field; }

  @Override
  public TypeConfiguration<TProperty> getTypeConfiguration() {
    return typeConfiguration;
  }

  @Override
  public boolean isRequired() {
    return isRequired;
  }

  @Override
  public boolean isUnwrapped() {
    return field.getAnnotationsByType(JsonUnwrapped.class).length != 0;
  }
}
