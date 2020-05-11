package com.plcarmel.jackson.databind1467poc.theory;

public interface PropertyConfiguration<TClass, TValue> {

  String getName();
  TypeConfiguration<TValue> getTypeConfiguration();
  boolean isRequired();
  boolean isUnwrapped();

}
