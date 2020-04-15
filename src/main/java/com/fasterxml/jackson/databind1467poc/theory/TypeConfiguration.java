package com.fasterxml.jackson.databind1467poc.theory;

import java.util.Collection;

public interface TypeConfiguration<T> {

  boolean isStandardType();

  boolean hasCreator();

  CreatorConfiguration<T> getCreatorConfiguration();

  Collection<PropertyConfiguration<?>> getProperties();
}
