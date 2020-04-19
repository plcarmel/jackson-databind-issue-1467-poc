package com.plcarmel.jackson.databind1467poc.example.configuration;

import com.plcarmel.jackson.databind1467poc.theory.CreatorConfiguration;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;
import com.plcarmel.jackson.databind1467poc.theory.TypeConfiguration;

import java.util.Arrays;
import java.util.Collection;

import static java.util.stream.Collectors.toList;

public class BeanTypeConfiguration<T> implements TypeConfiguration<T> {

  public final Class<T> typeClass;

  public BeanTypeConfiguration(Class<T> typeClass) {
    this.typeClass = typeClass;
  }

  @Override
  public boolean isStandardType() {
    return false;
  }

  @Override
  public boolean isCollection() {
    return Collection.class.isAssignableFrom(typeClass);
  }

  @Override
  public boolean hasCreator() {
    return false;
  }

  @Override
  public CreatorConfiguration<T> getCreatorConfiguration() {
    throw new RuntimeException("Method not implemented");
  }

  @Override
  public Collection<PropertyConfiguration<?>> getProperties() {
    return Arrays.stream(typeClass.getFields()).map(FieldPropertyConfiguration::new).collect(toList());
  }

  @Override
  public Class<T> getTypeClass() {
    return typeClass;
  }
}
