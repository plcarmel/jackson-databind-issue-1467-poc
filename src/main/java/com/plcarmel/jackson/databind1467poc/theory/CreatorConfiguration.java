package com.plcarmel.jackson.databind1467poc.theory;

import java.util.List;

public interface CreatorConfiguration<TClass> {

  Class<TClass> getType();

  List<PropertyConfiguration<TClass, ?>> getParamConfigurations();

  TClass instantiate(List<Object> params);

}
