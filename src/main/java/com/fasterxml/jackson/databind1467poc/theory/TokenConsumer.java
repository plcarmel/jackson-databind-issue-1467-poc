package com.fasterxml.jackson.databind1467poc.theory;

import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;

public interface TokenConsumer {

  boolean canHandleCurrentToken(JsonParser parser);
  void pushToken(JsonParser parser) throws IOException;
  boolean isDone();

}
