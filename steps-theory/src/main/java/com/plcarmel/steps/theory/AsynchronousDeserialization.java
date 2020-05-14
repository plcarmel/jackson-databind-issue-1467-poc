package com.plcarmel.steps.theory;

public interface AsynchronousDeserialization<TInput, TResult>
  extends
  TokenConsumer<TInput>,
    DataSupplier<TResult>
{
}
