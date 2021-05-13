package com.tsipadan.tsipadan.model;

import lombok.Data;

/**
 * Model of Phone book representation
 */
@Data
public class RecordFromPhoneBook {

  public static int count = 1;
  private int id;
  private String name;
  private String number;

  public RecordFromPhoneBook(final int id, final String name, final String number) {
    this.id = id;
    this.name = name;
    this.number = number;
  }

  public RecordFromPhoneBook() {
  }
}
