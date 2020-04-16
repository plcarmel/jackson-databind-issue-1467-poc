package com.fasterxml.jackson.databind1467poc.theory;

import java.util.Collection;

public interface TypeConfiguration<T> {

  boolean isStandardType();
  boolean isRequired();
  boolean isCollection();
  boolean hasCreator();

  CreatorConfiguration<T> getCreatorConfiguration();

  Collection<PropertyConfiguration<?>> getProperties();

  Class<T> getTypeClass();
}
