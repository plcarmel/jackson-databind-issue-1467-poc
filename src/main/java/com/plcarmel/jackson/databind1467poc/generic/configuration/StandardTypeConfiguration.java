package com.plcarmel.jackson.databind1467poc.generic.configuration;

import com.plcarmel.jackson.databind1467poc.theory.CreatorConfiguration;
import com.plcarmel.jackson.databind1467poc.theory.SettablePropertyConfiguration;
import com.plcarmel.jackson.databind1467poc.theory.TypeConfiguration;

import java.util.Collection;
import java.util.Collections;

import static com.plcarmel.jackson.databind1467poc.jackson.SupportedTypes.primitiveTypes;
import static com.plcarmel.jackson.databind1467poc.jackson.SupportedTypes.unboxedToBoxed;

public class StandardTypeConfiguration<TClass> implements TypeConfiguration<TClass> {

  private final Class<TClass> typeClass;

  public StandardTypeConfiguration(Class<TClass> typeClass) {
    final boolean isPrimitive = primitiveTypes.contains(typeClass);
    //noinspection unchecked
    this.typeClass = isPrimitive ? (Class<TClass>) unboxedToBoxed.get(typeClass) : typeClass;
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
  public CreatorConfiguration<TClass> getCreatorConfiguration() {
    return null;
  }

  @Override
  public Collection<SettablePropertyConfiguration<TClass, ?>> getProperties() {
    return Collections.emptyList();
  }

  @Override
  public Class<TClass> getType() {
    return typeClass;
  }
}
