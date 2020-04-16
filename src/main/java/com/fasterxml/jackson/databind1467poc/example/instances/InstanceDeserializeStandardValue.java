package com.fasterxml.jackson.databind1467poc.example.instances;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.fasterxml.jackson.databind1467poc.theory.TypeConfiguration;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class InstanceDeserializeStandardValue<T> extends InstanceHavingUnmanagedDependencies<T> {

  private final TypeConfiguration<T> conf;
  private T data;
  private boolean isDone = false;

  public InstanceDeserializeStandardValue(
    TypeConfiguration<T> conf,
    List<DeserializationStepInstance<?>> dependencies
  ) {
    super(dependencies);
    this.conf = conf;
  }

  @Override
  public boolean canHandleCurrentToken(JsonParser parser) {
    return parser.getCurrentToken().isScalarValue();
  }

  @Override
  public void pushToken(JsonParser parser) throws IOException {
    // Of course, this would be replaced with the current implementation of jackson-databind, which is much
    // more sophisticated than this. We are not supporting dates, among other things.
    final JsonParseException noAppropriateType = new JsonParseException(parser, "No appropriate type.");
    switch (parser.currentToken()) {
      case NOT_AVAILABLE:
        break;
      case VALUE_NULL:
        if (conf.isRequired()) throw new JsonParseException(parser, "Value is required");
        data = null;
        isDone = true;
        parser.nextToken();
        break;
      case VALUE_NUMBER_FLOAT:
        if (conf.getTypeClass() == float.class || conf.getTypeClass() == Float.class) {
          //noinspection unchecked
          data = (T) (Float) parser.getFloatValue();
        } else if (conf.getTypeClass() == double.class || conf.getTypeClass() == Double.class) {
          //noinspection unchecked
          data = (T) (Double) parser.getDoubleValue();
        } else if (conf.getTypeClass() == BigDecimal.class) {
          //noinspection unchecked
          data = (T) parser.getDecimalValue();
        } else if (conf.getTypeClass() == Number.class) {
          //noinspection unchecked
          data = (T) parser.getNumberValue();
        } else if (conf.getTypeClass() == String.class) {
          //noinspection unchecked
          data = (T) parser.getValueAsString();
        } else throw noAppropriateType;
        isDone = true;
        parser.nextToken();
        break;
      case VALUE_NUMBER_INT:
        if (conf.getTypeClass() == int.class || conf.getTypeClass() == Integer.class) {
          //noinspection unchecked
          data = (T) (Integer) parser.getIntValue();
        } else if (conf.getTypeClass() == long.class || conf.getTypeClass() == Long.class) {
          //noinspection unchecked
          data = (T) (Long) parser.getLongValue();
        } else if (conf.getTypeClass() == byte.class || conf.getTypeClass() == Byte.class) {
          //noinspection unchecked
          data = (T) (Byte) parser.getByteValue();
        } else if (conf.getTypeClass() == BigInteger.class) {
          //noinspection unchecked
          data = (T) parser.getBigIntegerValue();
        } else if (conf.getTypeClass() == Number.class) {
          //noinspection unchecked
          data = (T) parser.getNumberValue();
        } else if (conf.getTypeClass() == String.class) {
          //noinspection unchecked
          data = (T) parser.getValueAsString();
        } else throw noAppropriateType;
        isDone = true;
        parser.nextToken();
        break;
    case VALUE_STRING:
      if (conf.getTypeClass() == int.class || conf.getTypeClass() == Integer.class) {
        //noinspection unchecked
        data = (T) (Integer) parser.getValueAsInt();
      } else if (conf.getTypeClass() == long.class || conf.getTypeClass() == Long.class) {
        //noinspection unchecked
        data = (T) (Long) parser.getValueAsLong();
      } else if (conf.getTypeClass() == byte.class || conf.getTypeClass() == Byte.class) {
        //noinspection unchecked
        data = (T) (Byte) parser.getByteValue();
      } else if (conf.getTypeClass() == BigInteger.class) {
        //noinspection unchecked
        data = (T) parser.getBigIntegerValue();
      } else if (conf.getTypeClass() == Number.class) {
        //noinspection unchecked
        data = (T) parser.getNumberValue();
      } else if (conf.getTypeClass() == String.class) {
        //noinspection unchecked
        data = (T) parser.getText();
      } else throw noAppropriateType;
      isDone = true;
      parser.nextToken();
      break;
    default:
      throw new JsonParseException(parser, "Unexpected token " + parser.getCurrentToken());
    }
  }

  @Override
  public boolean isOptional() {
    return false;
  }

  @Override
  public boolean isDone() {
    return isDone;
  }

  @Override
  public T getData() {
    return data;
  }
}
