package com.tsipadan.tsipadan.service.implementation;

import com.tsipadan.tsipadan.model.User;
import com.tsipadan.tsipadan.repository.UserRepository;
import com.tsipadan.tsipadan.service.api.UserService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * получения списка всех пользователей (владельцев телефонных книжек)
 * создания, получения (по id), удаления, редактирования пользователя
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public Map<Integer, User> getAllUsers() {
    return userRepository.getUsersMap();
  }

  @Override
  public User createUser(final User newUser) {
    userRepository.putToUserMap(newUser);
    return newUser;
  }

  @Override
  public User getUserById(final int id) {
    return userRepository.getUsersMap().get(id);
  }

  @Override
  public boolean deleteUserById(final int id) {
    return userRepository.getUsersMap().remove(id) != null;
  }

  @Override
  public boolean updateUserById(final int id, final User user) {
    if (userRepository.getUsersMap().containsKey(id)) {
      userRepository.updateUser(id, user);
      return true;
    }
    return false;
  }

  @Override
  public List<User> searchByUsername(final String name) {
    return userRepository.searchByUsername(name);
  }
}
