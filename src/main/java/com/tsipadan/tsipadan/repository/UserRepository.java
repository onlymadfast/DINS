package com.tsipadan.tsipadan.repository;

import com.tsipadan.tsipadan.model.RecordFromPhoneBook;
import com.tsipadan.tsipadan.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

/**
 * User repository and methods to working with.
 * All data in project saved in RunTime
 */
@Repository
public class UserRepository {

  private static int userCount = 1;

  private final Map<Integer, User> userMap = new HashMap<>();

  public Map<Integer, User> getUsersMap() {
    return userMap;
  }

  public User getUser(final int userId) {
    return userMap.get(userId);
  }

  public void putToUserMap(final User newUser) {
    userMap.put(userCount++, newUser);
  }

  public void updateUser(final int userId, final User user) {
    userMap.put(userId, user);
  }

  public List<User> searchByUsername(final String name) {
    return userMap.values().stream()
        .filter(str -> str.getName().contains(name))
        .collect(Collectors.toList());
  }

  public List<RecordFromPhoneBook> searchRecordByNumber(final String number) {
    return userMap.values().stream()
        .map(user -> user.getPhoneBook().stream()
            .filter(recordFromPhoneBook -> recordFromPhoneBook
                .getNumber().contains(number))
            .collect(Collectors.toList())
        )
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }

}
