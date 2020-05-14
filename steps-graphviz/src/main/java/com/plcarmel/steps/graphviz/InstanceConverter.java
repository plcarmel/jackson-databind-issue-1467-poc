package com.plcarmel.steps.graphviz;

import com.plcarmel.steps.theory.StepInstance;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.model.Node;

import java.util.function.Supplier;

public class InstanceConverter<TInput> implements Converter<StepInstance<TInput, ?>> {

  public static int getNbOptional(StepInstance<?, ?> stepInstance) {
    return
      (stepInstance.isOptional() ? 1 : 0) +
        stepInstance.getParents().stream().mapToInt(InstanceConverter::getNbOptional).min().orElse(0);
  }

  public static Node applyColor(StepInstance<?, ?> stepInstance, Node node) {
    final int opacity = (int) Math.round(255 / Math.pow(2, getNbOptional(stepInstance)));
    String opacityStr = Integer.toHexString(opacity);
    if (opacityStr.length() == 1) opacityStr = "0" + opacityStr;
    final Color color = Color.rgba("" +  String.format("#000000%s", opacityStr));
    final Color fontColor = opacity < 15 ? Color.rgba("#0000000f") : color;
    return node.with(color, fontColor.font());
  }

  public static Node applyShape(StepInstance<?, ?> stepInstance, Node node) {
    return stepInstance.isTerminal()
      ? node.with(Shape.OCTAGON)
      : node;
  }

  public static Node applyDefaultStyles(StepInstance<?, ?> stepInstance, Node node) {
    return applyShape(stepInstance, applyColor(stepInstance, node));
  }

  @Override
  public Node getNode(StepInstance<TInput, ?> stepInstance, Supplier<String> newId) {
    return applyDefaultStyles(stepInstance, Converter.super.getNode(stepInstance, newId));
  }
}
