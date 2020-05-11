package com.plcarmel.jackson.databind1467poc.generic.configuration;

import com.plcarmel.jackson.databind1467poc.jackson.configuration.BeanTypeConfiguration;
import com.plcarmel.jackson.databind1467poc.jackson.configuration.StandardTypeConfiguration;
import com.plcarmel.jackson.databind1467poc.theory.TypeConfiguration;

import java.util.HashMap;
import java.util.Map;

import static com.plcarmel.jackson.databind1467poc.jackson.SupportedTypes.standardTypeClasses;

public class CachedTypeConfigurationFactory {

  private final static CachedTypeConfigurationFactory instance = new CachedTypeConfigurationFactory();

  public static CachedTypeConfigurationFactory getInstance() { return instance; }

  private final Map<Class<?>, TypeConfiguration<?>> cache = new HashMap<>();

  private CachedTypeConfigurationFactory() {}

  public <T> TypeConfiguration<T> getTypeConfiguration(Class<T> typeClass) {
    //noinspection unchecked
    final TypeConfiguration<T> fromCache = (TypeConfiguration<T>) cache.get(typeClass);
    if (fromCache != null) {
      return fromCache;
    }
    final TypeConfiguration<T> conf =
      standardTypeClasses.contains(typeClass)
      ? new StandardTypeConfiguration<>(typeClass)
      : new BeanTypeConfiguration<>(typeClass);
    cache.put(typeClass, conf);
    return conf;
  }

}
