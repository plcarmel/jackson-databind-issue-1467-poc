package com.plcarmel.jackson.databind1467poc.generic.groups;

import com.plcarmel.jackson.databind1467poc.theory.InstanceFactory;
import com.plcarmel.jackson.databind1467poc.theory.Step;

public class StepGroupTwo<TInput, TFirst, TSecond>
  extends GroupTwo<
    Step<TInput, TFirst>,
    Step<TInput, TSecond>,
    Step<TInput, ?>
  >
{
  public StepGroupTwo(Step<TInput, TFirst> first, Step<TInput, TSecond> second) {
    super(first, second);
  }

  public
    InstanceGroupTwo<TInput, TFirst, ? extends TSecond>
    instantiated(InstanceFactory<TInput> factory)
  {
    return new InstanceGroupTwo<>(factory.instantiate(getFirst()), factory.instantiate(getSecond()));
  }
}
