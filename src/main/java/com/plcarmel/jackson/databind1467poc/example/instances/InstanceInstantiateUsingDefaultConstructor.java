package com.plcarmel.jackson.databind1467poc.example.instances;

import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.TypeConfiguration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class InstanceInstantiateUsingDefaultConstructor<T> extends InstanceHavingUnmanagedDependencies<T> {

  private final Constructor<T> constructor;
  private T data;

  public InstanceInstantiateUsingDefaultConstructor(
    TypeConfiguration<T> typeConfiguration,
    List<DeserializationStepInstance<?>> dependencies
  ) {
    super(dependencies);
    try { constructor = typeConfiguration.getTypeClass().getConstructor(); }
    catch(NoSuchMethodException e) { throw new RuntimeException(e); }
  }

  @Override
  public boolean canHandleCurrentToken(JsonParser parser) {
    return false;
  }

  @Override
  public void pushToken(JsonParser parser) {
    throw new RuntimeException("Not supposed to be called.");
  }

  @Override
  public boolean isOptional() {
    return false;
  }

  @Override
  public boolean isDone() {
    return data != null;
  }

  @Override
  public T getData() {
    return data;
  }

  @Override
  public void update() {
    super.update();
    if (data == null) {
      try {
        data = constructor.newInstance();
      } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
  }

}
