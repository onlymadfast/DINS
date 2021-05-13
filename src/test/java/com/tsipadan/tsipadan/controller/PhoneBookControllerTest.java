package com.tsipadan.tsipadan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsipadan.tsipadan.model.RecordFromPhoneBook;
import com.tsipadan.tsipadan.model.User;
import com.tsipadan.tsipadan.service.api.PhoneBookService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PhoneBookController.class)
class PhoneBookControllerTest {
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
  private PhoneBookService phoneBookService;

  @Test
  void testGettingAllRecords() throws Exception {

    final RecordFromPhoneBook record = new RecordFromPhoneBook(1, "Dan", "+7(952)-465-87-45");
    final RecordFromPhoneBook record2 = new RecordFromPhoneBook(2, "Max", "+7(911)-846-22-11");
    final User user = new User("Vasya", "+7(952)-123-56-56", List.of(record, record2));

    final Map<Integer, User> users = new HashMap<>();
    users.put(1, user);

    when(phoneBookService.getAllRecordsFromPb(1)).thenReturn(List.of(record, record2));

    mvc.perform(get("/user/{id}/records", 1)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.*", Matchers.hasSize(2)))
        .andExpect(jsonPath("$[0].name", Matchers.is("Dan")))
        .andExpect(jsonPath("$[1].name", Matchers.is("Max")));
  }

  @Test
  void testGettingRecord() throws Exception {

    final RecordFromPhoneBook record = new RecordFromPhoneBook(1, "Dan", "+7(952)-465-87-45");
    final RecordFromPhoneBook record2 = new RecordFromPhoneBook(2, "Max", "+7(911)-846-22-11");
    final User user = new User("Vasya", "+7(952)-123-56-56", List.of(record, record2));

    final Map<Integer, User> users = new HashMap<>();
    users.put(1, user);

    when(phoneBookService.getOneRecordFromPb(1, 2)).thenReturn(record2);

    mvc.perform(get("/user/{id}/record/{recordId}", 1, 2)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", Matchers.is("Max")))
        .andExpect(jsonPath("$.number", Matchers.is("+7(911)-846-22-11")));
  }

  @Test
  void testUpdateRecord() throws Exception {

    final RecordFromPhoneBook record = new RecordFromPhoneBook(1, "Dan", "+7(952)-465-87-45");
    final RecordFromPhoneBook record2 = new RecordFromPhoneBook(2, "Max", "+7(911)-846-22-11");
    final User user = new User("Vasya", "+7(952)-123-56-56", List.of(record, record2));

    final RecordFromPhoneBook record3 = new RecordFromPhoneBook(1, "Dan", "+7(952)-465-87-45");
    final RecordFromPhoneBook record4 = new RecordFromPhoneBook(2, "Max", "+7(911)-846-22-11");
    final User user2 = new User("Fedya", "+7(952)-567-99-88", List.of(record3, record4));

    final RecordFromPhoneBook newRecord = new RecordFromPhoneBook(2, "Franck", "+7(952)-346-21-13");

    final Map<Integer, User> users = new HashMap<>();
    users.put(1, user);
    users.put(2, user2);

    when(phoneBookService.updateRecordFromPb(2, 2, newRecord)).thenReturn(true);

    mvc.perform(put("/user/{id}/record/{recordId}", 2, 2)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(newRecord)))
        .andExpect(status().isOk());
  }

  @Test
  void testCreatingRecord() throws Exception {

    final RecordFromPhoneBook record = new RecordFromPhoneBook(1, "Dan", "+7(952)-465-87-45");
    final RecordFromPhoneBook record2 = new RecordFromPhoneBook(2, "Max", "+7(911)-846-22-11");

    final User user = new User("Vasya", "+7(952)-123-56-56");

    final Map<Integer, User> users = new HashMap<>();
    users.put(1, user);

    when(phoneBookService.createRecordInPb(1, record2)).thenReturn(record2);

    mvc.perform(post("/user/{id}/record", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(record2)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", Matchers.is(2)))
        .andExpect(jsonPath("$.name", Matchers.is("Max")))
        .andExpect(jsonPath("$.number", Matchers.is("+7(911)-846-22-11")));
  }

  @Test
  void testDeleteRecord() throws Exception {

    final RecordFromPhoneBook record = new RecordFromPhoneBook(1, "Dan", "+7(952)-465-87-45");
    final RecordFromPhoneBook record2 = new RecordFromPhoneBook(2, "Max", "+7(911)-846-22-11");
    final User user = new User("Vasya", "+7(952)-123-56-56", List.of(record, record2));

    final RecordFromPhoneBook record3 = new RecordFromPhoneBook(1, "Dan", "+7(952)-465-87-45");
    final RecordFromPhoneBook record4 = new RecordFromPhoneBook(2, "Max", "+7(911)-846-22-11");
    final User user2 = new User("Fedya", "+7(952)-567-99-88", List.of(record3, record4));

    final Map<Integer, User> users = new HashMap<>();
    users.put(1, user);
    users.put(2, user2);

    when(phoneBookService.deleteRecordFromPb(2, 1)).thenReturn(true);

    mvc.perform(delete("/user/{id}/record/{recordId}", 2, 1)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void testSearchRecordByNumber() throws Exception {

    final String find = "+7(911)-8";

    final RecordFromPhoneBook record = new RecordFromPhoneBook(1, "Dan1", "+7(952)-465-87-45");
    final RecordFromPhoneBook record2 = new RecordFromPhoneBook(2, "Max1", "+7(911)-846-22-11");
    final User user = new User("Vasya", "+7(952)-123-56-56", List.of(record, record2));

    final RecordFromPhoneBook record3 = new RecordFromPhoneBook(1, "Dan2", "+7(952)-465-87-45");
    final RecordFromPhoneBook record4 = new RecordFromPhoneBook(2, "Max2", "+7(911)-846-25-44");
    final User user2 = new User("Fedya", "+7(952)-567-99-88", List.of(record3, record4));

    final Map<Integer, User> users = new HashMap<>();
    users.put(1, user);
    users.put(2, user2);

    when(phoneBookService.searchRecordByNumber(find)).thenReturn(List.of(record2, record4));

    mvc.perform(get("/user/search/{number}", find)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name", Matchers.is("Max1")))
        .andExpect(jsonPath("$[0].number", Matchers.is("+7(911)-846-22-11")))
        .andExpect(jsonPath("$[1].name", Matchers.is("Max2")))
        .andExpect(jsonPath("$[1].number", Matchers.is("+7(911)-846-25-44")));
  }

  static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}