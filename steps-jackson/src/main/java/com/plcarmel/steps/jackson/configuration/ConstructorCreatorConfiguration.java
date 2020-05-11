package com.plcarmel.steps.jackson.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.plcarmel.steps.theory.CreatorConfiguration;
import com.plcarmel.steps.theory.PropertyConfiguration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

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
      .map((Function<Parameter, CreatorPropertyConfiguration<TClass, Object>>) CreatorPropertyConfiguration::new)
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
