package com.plcarmel.jackson.databind1467poc.theory;

public interface PropertyConfiguration<T> {

  String getName();
  TypeConfiguration<T> getTypeConfiguration();
  boolean isRequired();

}
