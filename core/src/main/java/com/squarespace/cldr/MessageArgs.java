package com.squarespace.cldr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Holds a list of positional and named arguments for use by MessageFormat.
 */
public class MessageArgs {

  private final List<MessageArg> args = new ArrayList<>();

  private Map<String, MessageArg> map;

  /**
   * Return a positional argument, using a zero-based index, or null
   * if not enough arguments exist.
   */
  public MessageArg get(int index) {
    return index < args.size() ? args.get(index) : null;
  }

  /**
   * Return a named argument or null if does not exist.
   */
  public MessageArg get(String name) {
    return map == null ? null : map.get(name);
  }

  /**
   * Add a positional argument.
   */
  public void add(MessageArg arg) {
    args.add(arg);
  }

  /**
   * Add a positional argument.
   */
  public void add(String value) {
    args.add(new MessageArg(value));
  }

  /**
   * Add a named argument. Also accessible by index.
   */
  public void add(String name, MessageArg arg) {
    args.add(arg);
    if (map == null) {
      map = new HashMap<>();
    }
    map.put(name, arg);
  }

  /**
   * Add a named argument. Also accessible by index.
   */
  public void add(String name, String value) {
    add(name, new MessageArg(value));
  }

  /**
   * Returns the number of arguments.
   */
  public int count() {
    return args.size();
  }

  /**
   * Clears the arguments.
   */
  public void reset() {
    map.clear();
    args.clear();
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {

    private final MessageArgs args = new MessageArgs();

    public Builder add(String name, MessageArg arg) {
      args.add(name, arg);
      return this;
    }

    public Builder add(String name, String value) {
      args.add(name, value);
      return this;
    }

    public Builder add(MessageArg arg) {
      args.add(arg);
      return this;
    }

    public Builder add(String value) {
      args.add(value);
      return this;
    }

    public MessageArgs build() {
      return args;
    }
  }

}
