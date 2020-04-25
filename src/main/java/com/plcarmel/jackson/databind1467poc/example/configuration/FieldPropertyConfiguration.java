package com.plcarmel.jackson.databind1467poc.example.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;
import com.plcarmel.jackson.databind1467poc.theory.TypeConfiguration;

import java.lang.reflect.Field;
import java.util.Arrays;

public class FieldPropertyConfiguration<TClass, TProperty> implements PropertyConfiguration<TClass, TProperty> {

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
        .getTypeConfiguration(field.getDeclaringClass());
  }

  @Override
  public String getName() {
    return field.getName();
  }

  @Override
  public Class<TClass> getDeclaringClass() {
    //noinspection unchecked
    return (Class<TClass>) field.getDeclaringClass();
  }

  @Override
  public TypeConfiguration<TProperty> getTypeConfiguration() {
    return typeConfiguration;
  }

  @Override
  public boolean isRequired() {
    return isRequired;
  }
}
