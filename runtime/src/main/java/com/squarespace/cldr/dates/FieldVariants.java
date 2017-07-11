package com.squarespace.cldr.dates;


/**
 * Holds variants of strings used for formatting fields based on field width.
 */
public class FieldVariants {

  public final String[] abbreviated;
  public final String[] narrow;
  public final String[] short_;
  public final String[] wide;

  public FieldVariants(String[] abbreviated, String[] narrow, String[] short_, String[] wide) {
    this.abbreviated = abbreviated;
    this.narrow = narrow;
    this.short_ = short_;
    this.wide = wide;
  }

}
