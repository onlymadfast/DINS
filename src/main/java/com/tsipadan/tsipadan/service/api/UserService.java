package com.tsipadan.tsipadan.service.api;

import com.tsipadan.tsipadan.model.User;
import java.util.List;
import java.util.Map;

/**
 * Interface by User with classical CRUD operations
 * and searching users
 */
public interface UserService {

  Map<Integer, User> getAllUsers();

  User createUser(User newUser);

  User getUserById(int id);

  boolean deleteUserById(int id);

  boolean updateUserById(int id, User user);

  List<User> searchByUsername(String name);

}
