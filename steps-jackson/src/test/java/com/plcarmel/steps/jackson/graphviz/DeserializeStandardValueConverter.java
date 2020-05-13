package com.plcarmel.steps.jackson.graphviz;

import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.steps.graphviz.InstanceConverter;
import com.plcarmel.steps.jackson.instances.InstanceDeserializeStandardValue;
import com.plcarmel.steps.theory.StepInstance;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.Node;

import java.util.function.Supplier;

public class DeserializeStandardValueConverter extends InstanceConverter<JsonParser> {

  @Override
  public boolean canConvert(StepInstance<JsonParser, ?> stepInstance) {
    return stepInstance instanceof InstanceDeserializeStandardValue;
  }

  @Override
  public Node getNode(StepInstance<JsonParser, ?> stepInstance, Supplier<String> newId) {
    final String id = newId.get();
    final InstanceDeserializeStandardValue<?> deserializeStandardValue =
      (InstanceDeserializeStandardValue<?>) stepInstance;
    final Node node =
      Factory
        .node(id)
        .with(
          Label.of(
            String.format(
              "parse %s",
              deserializeStandardValue.getConf().getTypeConfiguration().getType().getSimpleName()
            )
          ),
          Font.name("monospace"),
          Font.size(8)
        );
    return applyColor(stepInstance, node);
  }
}
