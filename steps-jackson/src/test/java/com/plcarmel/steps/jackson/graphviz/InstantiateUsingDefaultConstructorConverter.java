package com.plcarmel.steps.jackson.graphviz;

import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.steps.generic.instances.InstanceInstantiateUsingDefaultConstructor;
import com.plcarmel.steps.graphviz.InstanceConverter;
import com.plcarmel.steps.theory.StepInstance;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.Node;

import java.util.function.Supplier;

public class InstantiateUsingDefaultConstructorConverter extends InstanceConverter<JsonParser> {

  @Override
  public boolean canConvert(StepInstance<JsonParser, ?> stepInstance) {
    return stepInstance instanceof InstanceInstantiateUsingDefaultConstructor;
  }

  @Override
  public Node getNode(StepInstance<JsonParser, ?> stepInstance, Supplier<String> newId) {
    final String id = newId.get();
    //noinspection unchecked
    final InstanceInstantiateUsingDefaultConstructor<JsonParser, ?> instantiate =
      (InstanceInstantiateUsingDefaultConstructor<JsonParser, ?>) stepInstance;
    final Node node =
      Factory
        .node(id)
        .with(
          Label.of(String.format("new %s()", instantiate.getTypeConfiguration().getType().getSimpleName())),
          Font.name("monospace"),
          Font.size(10)
        );
    return applyDefaultStyles(stepInstance, node);
  }
}
