package com.plcarmel.steps.theory;

public interface PropertyConfiguration<TClass, TValue> {

  String getName();
  TypeConfiguration<TValue> getTypeConfiguration();
  boolean isRequired();
  boolean isUnwrapped();

}
