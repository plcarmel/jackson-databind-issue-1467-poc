package com.plcarmel.steps.jackson.graphviz;

import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.steps.generic.instances.InstanceAlso;
import com.plcarmel.steps.graphviz.InstanceConverter;
import com.plcarmel.steps.theory.StepInstance;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.Node;

import java.util.function.Supplier;

public class AlsoConverter extends InstanceConverter<JsonParser> {

  @Override
  public boolean canConvert(StepInstance<JsonParser, ?> stepInstance) {
    return stepInstance instanceof InstanceAlso;
  }

  @Override
  public Node getNode(StepInstance<JsonParser, ?> stepInstance, Supplier<String> newId) {
    final String id = newId.get();
    final Node node = Factory.node(id).with(Label.of("&"));
    return applyColor(stepInstance, node);
  }
}
