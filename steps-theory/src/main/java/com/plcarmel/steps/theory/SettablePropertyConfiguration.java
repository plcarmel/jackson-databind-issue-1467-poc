package com.plcarmel.steps.theory;

public interface SettablePropertyConfiguration<TClass, TValue> extends PropertyConfiguration<TClass, TValue> {

  void set(TClass obj, TValue value);

}
