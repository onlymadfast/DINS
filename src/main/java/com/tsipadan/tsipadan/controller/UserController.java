package com.tsipadan.tsipadan.controller;

import com.tsipadan.tsipadan.model.User;
import com.tsipadan.tsipadan.service.api.UserService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller to work with User model
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/api")
public class UserController {

  private final UserService userService;

  @PostMapping(value = "/user")
  @ApiOperation("Creating User")
  public ResponseEntity<User> createUser(
      @RequestBody final User user) {
    final User result = userService.createUser(user);
    return result != null
        ? new ResponseEntity<>(result, HttpStatus.CREATED)
        : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @GetMapping(value = "/users")
  @ApiOperation("Getting all users")
  public ResponseEntity<Map<Integer, User>> getUsers() {
    final Map<Integer, User> users = userService.getAllUsers();
    return users != null && !users.isEmpty()
        ? new ResponseEntity<>(users, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping(value = "/user/{id}")
  @ApiOperation("Getting a specific user by id")
  public ResponseEntity<User> getUserById(
      @PathVariable(name = "id") final int id) {
    final User user = userService.getUserById(id);
    return user != null
        ? new ResponseEntity<>(user, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PutMapping(value = "/user/{id}")
  @ApiOperation("Updating a specific user by id")
  public ResponseEntity<?> updateUser(
      @PathVariable(name = "id") int id,
      @RequestBody User user) {
    final boolean updateUser = userService.updateUserById(id, user);
    return updateUser
        ? new ResponseEntity<>(HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @DeleteMapping(value = "/user/{id}")
  @ApiOperation("Deleting a specific user by id")
  public ResponseEntity<?> deleteUser(
      @PathVariable(name = "id") final int id) {
    final boolean deleted = userService.deleteUserById(id);
    return deleted
        ? new ResponseEntity<>(HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @GetMapping(value = "/user/find/{string}")
  @ApiOperation("Search User by username using some string")
  public ResponseEntity<List<User>> searchUserByUsername(
      @PathVariable(name = "string") final String search) {
    final List<User> find = userService.searchByUsername(search);
    return find != null
        ? new ResponseEntity<>(find, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}
