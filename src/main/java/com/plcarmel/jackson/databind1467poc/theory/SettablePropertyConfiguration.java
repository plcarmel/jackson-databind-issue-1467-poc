package com.plcarmel.jackson.databind1467poc.theory;

public interface SettablePropertyConfiguration<TClass, TValue> extends PropertyConfiguration<TClass, TValue> {

  void set(TClass obj, TValue value);
}
