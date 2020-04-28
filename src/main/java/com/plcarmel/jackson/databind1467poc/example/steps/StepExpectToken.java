package com.plcarmel.jackson.databind1467poc.example.steps;

import com.fasterxml.jackson.core.JsonToken;
import com.plcarmel.jackson.databind1467poc.example.instances.InstanceExpectToken;
import com.plcarmel.jackson.databind1467poc.example.structures.StructureUnmanaged;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.NoData;

import java.util.List;

public class StepExpectToken
  extends StructureUnmanaged<DeserializationStep<?>>
  implements StepUnmanagedMixin<NoData>
{

  private final JsonToken expectedTokenKind;
  private final Object expectedTokenValue;
  private final boolean useTokenValue;

  public StepExpectToken(
    JsonToken expectedTokenKind,
    Object expectedTokenValue,
    List<DeserializationStep<?>> dependencies
  ) {
    super(dependencies);
    this.expectedTokenKind = expectedTokenKind;
    this.expectedTokenValue = expectedTokenValue;
    useTokenValue = true;
  }

  public StepExpectToken(
    JsonToken expectedTokenKind,
    List<DeserializationStep<?>> dependencies
  ) {
    super(dependencies);
    this.expectedTokenKind = expectedTokenKind;
    this.expectedTokenValue = null;
    useTokenValue = false;
  }

  @Override
  public DeserializationStepInstance<NoData> instantiated(InstanceFactory dependenciesInstanceFactory) {
    return useTokenValue
      ? new InstanceExpectToken(
        expectedTokenKind,
        expectedTokenValue,
        instantiatedDependencies(dependenciesInstanceFactory)
      )
      : new InstanceExpectToken(
        expectedTokenKind,
        instantiatedDependencies(dependenciesInstanceFactory)
      );
  }

  @Override
  public StructureUnmanaged<DeserializationStep<?>> thisAsStructureUnmanaged() {
    return this;
  }
}
