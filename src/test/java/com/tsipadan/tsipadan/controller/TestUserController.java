package com.tsipadan.tsipadan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsipadan.tsipadan.controller.UserController;
import com.tsipadan.tsipadan.model.User;
import com.tsipadan.tsipadan.service.api.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class TestUserController {

  /**
   * We can @Autowire MockMvc because the WebApplicationContext provides an
   * instance/bean for us
   */
  @Autowired
  private MockMvc mvc;

  /**
   * We use @MockBean because the WebApplicationContext does not provide
   * any @Component, @Service or @Repository beans instance/bean of this service
   * in its context. It only loads the beans solely required for testing the
   * controller.
   */
  @MockBean
  private UserService userService;

  @Test
  void testGettingAllUsers() throws Exception {

    final User user1 = new User("Dan", "+7(911)-846-22-11");
    final User user2 = new User("Max", "+7(952)-465-87-45");

    final Map<Integer, User> users = new HashMap<>();
    users.put(1, user1);
    users.put(2, user2);

    when(userService.getAllUsers()).thenReturn(users);

    mvc.perform(get("/users")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", Matchers.hasSize(2)))
        .andExpect(jsonPath("$.1.name", Matchers.is("Dan")))
        .andExpect(jsonPath("$.2.name", Matchers.is("Max")));
  }

  @Test
  void shouldReturn404ThenAccountNotFound() throws Exception {

    when(userService.getUserById(123)).thenReturn(null);

    mvc.perform(get("/user/123")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void testGettingUserById() throws Exception {

    final User user = new User("Dan", "+7(911)-846-22-11");

    when(userService.getUserById(1)).thenReturn(user);

    mvc.perform(get("/user/{id}", 1)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", Matchers.is("Dan")));
  }

  @Test
  void testUpdatingUser() throws Exception {

    final User user1 = new User("Dan", "+7(911)-846-22-11");
    final User user2 = new User("Ned", "+7(911)-564-21-34");
    final User newUser = new User("Max", "+7(952)-346-21-13");

    final Map<Integer, User> users = new HashMap<>();
    users.put(1, user1);
    users.put(2, user2);

    when(userService.updateUserById(2, newUser)).thenReturn(true);

    mvc.perform(put("/user/{id}", 2)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(newUser)))
        .andExpect(status().isOk());
  }

  @Test
  void testCreatingUser() throws Exception {

    final User user = new User("Dan", "+7(911)-846-22-11");

    final Map<Integer, User> users = new HashMap<>();
    users.put(1, user);

    when(userService.createUser(user)).thenReturn(users.get(1));

    mvc.perform(post("/user")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(user)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", Matchers.is("Dan")))
        .andExpect(jsonPath("$.number", Matchers.is("+7(911)-846-22-11")));
  }

  @Test
  void testDeleteUser() throws Exception {

    final User user = new User("Dan", "+7(911)-846-22-11");

    final Map<Integer, User> users = new HashMap<>();
    users.put(1, user);

    when(userService.deleteUserById(1)).thenReturn(true);

    mvc.perform(delete("/user/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void testSearchingUserByUsername() throws Exception {

    final String find = "ed";

    final User user = new User("Ned", "+7(911)-564-21-34");

    when(userService.searchByUsername(find)).thenReturn(List.of(user));

    mvc.perform(get("/user/find/{string}", find)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name", Matchers.is("Ned")))
        .andExpect(jsonPath("$[0].number", Matchers.is("+7(911)-564-21-34")));
  }

  static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
