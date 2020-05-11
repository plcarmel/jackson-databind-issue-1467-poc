package com.plcarmel.jackson.databind1467poc.generic.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.plcarmel.jackson.databind1467poc.theory.CreatorConfiguration;
import com.plcarmel.jackson.databind1467poc.theory.SettablePropertyConfiguration;
import com.plcarmel.jackson.databind1467poc.theory.TypeConfiguration;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;

import static java.util.stream.Collectors.toList;

public class BeanTypeConfiguration<TClass> implements TypeConfiguration<TClass> {

  public final Class<TClass> typeClass;
  public final CreatorConfiguration<TClass> creatorConfiguration;

  public BeanTypeConfiguration(Class<TClass> typeClass) {
    this.typeClass = typeClass;
    //noinspection unchecked
    this.creatorConfiguration =
      Arrays
        .stream(typeClass.getConstructors())
        .map(c -> (Constructor<TClass>) c)
        .filter(c -> c.getAnnotation(JsonCreator.class) != null)
        .findFirst()
        .map(ConstructorCreatorConfiguration::new)
        .orElse(null);
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
    return creatorConfiguration != null;
  }

  @Override
  public CreatorConfiguration<TClass> getCreatorConfiguration() {
    return creatorConfiguration;
  }

  @Override
  public Collection<SettablePropertyConfiguration<TClass, ?>> getProperties() {
    //noinspection unchecked
    return Arrays
      .stream(typeClass.getFields())
      .map(FieldPropertyConfiguration::new)
      .map(p -> (SettablePropertyConfiguration<TClass, ?>) p)
      .collect(toList());
  }

  @Override
  public Class<TClass> getType() {
    return typeClass;
  }
}
