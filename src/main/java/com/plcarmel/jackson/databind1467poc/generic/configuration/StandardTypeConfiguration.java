package com.plcarmel.jackson.databind1467poc.generic.configuration;

import com.plcarmel.jackson.databind1467poc.theory.CreatorConfiguration;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;
import com.plcarmel.jackson.databind1467poc.theory.TypeConfiguration;

import java.util.Collection;
import java.util.Collections;

import static com.plcarmel.jackson.databind1467poc.jackson.SupportedTypes.primitiveTypes;
import static com.plcarmel.jackson.databind1467poc.jackson.SupportedTypes.unboxedToBoxed;

public class StandardTypeConfiguration<T> implements TypeConfiguration<T> {

  private final Class<T> typeClass;

  public StandardTypeConfiguration(Class<T> typeClass) {
    final boolean isPrimitive = primitiveTypes.contains(typeClass);
    //noinspection unchecked
    this.typeClass = isPrimitive ? (Class<T>) unboxedToBoxed.get(typeClass) : typeClass;
  }

  @Override
  public boolean isStandardType() {
    return true;
  }

  @Override
  public boolean isCollection() {
    return false;
  }

  @Override
  public CreatorConfiguration<T> getCreatorConfiguration() {
    return null;
  }

  @Override
  public Collection<PropertyConfiguration<?>> getProperties() {
    return Collections.emptyList();
  }

  @Override
  public Class<T> getTypeClass() {
    return typeClass;
  }
}
