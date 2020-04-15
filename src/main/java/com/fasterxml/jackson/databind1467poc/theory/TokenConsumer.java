package com.fasterxml.jackson.databind1467poc.theory;

import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;

public interface TokenConsumer {

  boolean canHandle(JsonToken kind, String token);
  void push(JsonToken kind, String token) throws IOException;
  boolean isDone();

}
