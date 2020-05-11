package com.plcarmel.jackson.databind1467poc.theory;

import java.util.Collection;

public interface TypeConfiguration<T> {

  boolean isStandardType();
  boolean isCollection();

  default boolean hasCreator() { return getCreatorConfiguration() == null; }

  CreatorConfiguration<T> getCreatorConfiguration();

  Collection<SettablePropertyConfiguration<T, ?>> getProperties();

  Class<T> getType();
}
