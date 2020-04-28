package com.plcarmel.jackson.databind1467poc.example.steps;

import com.plcarmel.jackson.databind1467poc.example.instances.InstanceAlso;
import com.plcarmel.jackson.databind1467poc.example.structures.StructureAlso;
import com.plcarmel.jackson.databind1467poc.example.structures.StructureUnmanaged;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.List;

public class StepAlso<T>
  extends StructureAlso<DeserializationStep<T>, DeserializationStep<?>>
  implements StepUnmanagedMixin<T>
{

  public StepAlso(DeserializationStep<T> mainDependency, List<DeserializationStep<?>> unmanagedDependencies) {
    super(mainDependency, unmanagedDependencies);
  }

  @Override
  public DeserializationStepInstance<T> instantiated(InstanceFactory dependenciesInstanceFactory) {
    return new InstanceAlso<>(
      dependenciesInstanceFactory.instantiate(mainDependency),
      instantiatedDependencies(dependenciesInstanceFactory)
    );
  }

  @Override
  public StructureUnmanaged<DeserializationStep<?>> thisAsStructureUnmanaged() {
    return this;
  }
}
