package com.plcarmel.steps.jackson.configuration;

import com.plcarmel.steps.theory.PropertyConfiguration;
import com.plcarmel.steps.theory.TypeConfiguration;

public class CreatorPropertyConfiguration<TClass, TValue> implements PropertyConfiguration<TClass, TValue> {

  private final String name;
  private final TypeConfiguration<TValue> typeConfiguration;

  public CreatorPropertyConfiguration(String name, TypeConfiguration<TValue> typeConfiguration) {
    this.name = name;
    this.typeConfiguration = typeConfiguration;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public TypeConfiguration<TValue> getTypeConfiguration() {
    return typeConfiguration;
  }

  @Override
  public boolean isRequired() {
    return true;
  }

  @Override
  public boolean isUnwrapped() {
    return false;
  }

}
