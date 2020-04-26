package com.plcarmel.jackson.databind1467poc.example.steps;

import com.plcarmel.jackson.databind1467poc.example.instances.InstanceDeserializeStandardValue;
import com.plcarmel.jackson.databind1467poc.example.instances.InstanceInstantiateUsingDefaultConstructor;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.TypeConfiguration;

import java.util.List;
import java.util.Map;

public class StepInstantiateUsingDefaultConstructor<T> extends StepHavingUnmanagedDependencies<T> {

  private final TypeConfiguration<T> conf;

  public StepInstantiateUsingDefaultConstructor(TypeConfiguration<T> conf, List<DeserializationStep<?>> dependencies) {
    super(dependencies);
    this.conf = conf;
  }

  @Override
  public DeserializationStepInstance<T> instantiated(
    Map<DeserializationStep<?>, DeserializationStepInstance<?>> alreadyInstantiated
  ) {
    //noinspection unchecked
    DeserializationStepInstance<T> instance = (DeserializationStepInstance<T>) alreadyInstantiated.get(this);
    if (instance != null) return instance;
    instance = new InstanceInstantiateUsingDefaultConstructor<>(conf, instantiatedDependencies(alreadyInstantiated));
    alreadyInstantiated.put(this, instance);
    return instance;
  }
}
