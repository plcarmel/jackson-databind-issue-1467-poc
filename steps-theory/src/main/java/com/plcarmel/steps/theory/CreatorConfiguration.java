package com.plcarmel.steps.theory;

import java.util.List;

public interface CreatorConfiguration<TClass> {

  Class<TClass> getType();

  List<PropertyConfiguration<TClass, ?>> getParamConfigurations();

  TClass instantiate(List<Object> params);

}
