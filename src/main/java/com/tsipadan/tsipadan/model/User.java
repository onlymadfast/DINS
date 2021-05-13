package com.tsipadan.tsipadan.model;

import java.util.List;
import lombok.Data;

/**
 * Model of User representation
 */
@Data
public class User {

  private String name;
  private String number;
  private List<RecordFromPhoneBook> phoneBook;

  public User(final String name, final String number) {
    this.name = name;
    this.number = number;
  }

  public User(final String name, final String number, final List<RecordFromPhoneBook> phoneBook) {
    this.name = name;
    this.number = number;
    this.phoneBook = phoneBook;
  }

  public User() {
  }
}
