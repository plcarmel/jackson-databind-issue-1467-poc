package com.plcarmel.jackson.databind1467poc.theory;

public interface PropertyConfiguration<TProperty> {

  String getName();
  TypeConfiguration<TProperty> getTypeConfiguration();
  boolean isRequired();
  boolean isUnwrapped();

}
