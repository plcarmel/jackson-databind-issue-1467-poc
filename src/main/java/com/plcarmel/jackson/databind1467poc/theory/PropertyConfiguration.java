package com.plcarmel.jackson.databind1467poc.theory;

public interface PropertyConfiguration<TClass, TProperty> {

  String getName();
  Class<TClass> getDeclaringClass();
  TypeConfiguration<TProperty> getTypeConfiguration();
  boolean isRequired();

}
