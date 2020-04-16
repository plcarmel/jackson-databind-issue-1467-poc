package com.fasterxml.jackson.databind1467poc.theory;

public interface PropertyConfiguration<T> {

  String getName();
  TypeConfiguration<T> getTypeConfiguration();

}
