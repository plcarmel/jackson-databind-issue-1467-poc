package com.plcarmel.steps.jackson.configuration;

import com.plcarmel.steps.theory.CreatorConfiguration;
import com.plcarmel.steps.theory.SettablePropertyConfiguration;
import com.plcarmel.steps.theory.TypeConfiguration;

import java.util.Collection;
import java.util.Collections;

import static com.plcarmel.steps.jackson.SupportedTypes.primitiveTypes;
import static com.plcarmel.steps.jackson.SupportedTypes.unboxedToBoxed;

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
