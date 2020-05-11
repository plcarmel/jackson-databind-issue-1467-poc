package com.plcarmel.steps.theory;

import java.io.IOException;

public interface TokenConsumer<TInput> {

  boolean canHandleCurrentToken(TInput parser);
  void pushToken(TInput parser) throws IOException;
  boolean isDone();

}
