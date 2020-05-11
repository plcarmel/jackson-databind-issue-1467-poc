package com.plcarmel.jackson.databind1467poc.jackson.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plcarmel.jackson.databind1467poc.theory.CreatorConfiguration;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import static com.plcarmel.jackson.databind1467poc.jackson.SupportedTypes.standardTypeClasses;
import static java.util.stream.Collectors.toList;

public class ConstructorCreatorConfiguration<TClass> implements CreatorConfiguration<TClass> {

  private final Constructor<TClass> constructor;

  public ConstructorCreatorConfiguration(Constructor<TClass> constructor) {
    this.constructor = constructor;
  }

  @Override
  public Class<TClass> getType() {
    return constructor.getDeclaringClass();
  }

  @Override
  public List<PropertyConfiguration<TClass, ?>> getParamConfigurations() {
    return Arrays
      .stream(constructor.getParameters())
      .filter(p -> p.getAnnotationsByType(JsonProperty.class).length != 0)
      .map(p -> {
        final JsonProperty[] annotations = p.getAnnotationsByType(JsonProperty.class);
        if (annotations.length != 1) {
          throw new RuntimeException("Multiple JsonProperty on parameter %s");
        }
        final Class<?> paramClass = p.getType();
        //noinspection unchecked
        return new CreatorPropertyConfiguration<TClass, Object>(
          annotations[0].value(),
          standardTypeClasses.contains(paramClass)
            ? new StandardTypeConfiguration<>((Class<Object>) paramClass)
            : new BeanTypeConfiguration<>((Class<Object>) paramClass)
        );
      })
      .collect(toList());
  }

  @Override
  public TClass instantiate(List<Object> params) {
    try {
      return constructor.newInstance(params.toArray());
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException("Call to constructor failed", e);
    }
  }
}
