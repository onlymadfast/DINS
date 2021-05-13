package com.tsipadan.tsipadan.controller;

import com.tsipadan.tsipadan.model.RecordFromPhoneBook;
import com.tsipadan.tsipadan.service.api.PhoneBookService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller to work with phone book methods
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/api")
public class PhoneBookController {

  /**
   * Service with methods from work requirements
   */
  private final PhoneBookService phoneBookService;

  @PostMapping(value = "/user/{id}/record")
  @ApiOperation("Creating record in phone book for specific user id")
  public ResponseEntity<?> createRecord(
      @PathVariable(name = "id") final int userId,
      @RequestBody final RecordFromPhoneBook record) {
    final RecordFromPhoneBook result = phoneBookService
        .createRecordInPb(userId, record);
    return result != null
        ? new ResponseEntity<>(result, HttpStatus.CREATED)
        : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @GetMapping(value = "/user/{id}/record/{recordId}")
  @ApiOperation("Getting specific record from phone book by specific user id")
  public ResponseEntity<RecordFromPhoneBook> getRecord(
      @PathVariable(name = "id") final int userId,
      @PathVariable(name = "recordId") final int recordId) {
    final RecordFromPhoneBook record = phoneBookService
        .getOneRecordFromPb(userId, recordId);
    return record != null
        ? new ResponseEntity<>(record, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping(value = "/user/{id}/records")
  @ApiOperation("Getting all records from phone book by specific user id")
  public ResponseEntity<List<RecordFromPhoneBook>> getAllRecord(
      @PathVariable(name = "id") final int userId) {
    final List<RecordFromPhoneBook> list = phoneBookService
        .getAllRecordsFromPb(userId);
    return list != null
        ? new ResponseEntity<>(list, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping(value = "/user/{id}/record/{recordId}")
  @ApiOperation("Deleting specific record in phone book by specific user id")
  public ResponseEntity<?> deleteRecord(
      @PathVariable(name = "id") final int userId,
      @PathVariable(name = "recordId") final int recordId) {
    final boolean deleted = phoneBookService
        .deleteRecordFromPb(userId, recordId);
    return deleted
        ? new ResponseEntity<>(HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @PutMapping(value = "/user/{id}/record/{recordId}")
  @ApiOperation("Updating specific record in phone book by specific user id")
  public ResponseEntity<?> updateRecord(
      @PathVariable(name = "id") final int id,
      @PathVariable(name = "recordId") final int recordId,
      @RequestBody final RecordFromPhoneBook record) {
    final boolean updateRecord = phoneBookService
        .updateRecordFromPb(id, recordId, record);
    return updateRecord
        ? new ResponseEntity<>(HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @GetMapping(value = "/user/search/{number}")
  @ApiOperation("Search records by phone number")
  public ResponseEntity<List<RecordFromPhoneBook>> searchRecordByNumber(
      @PathVariable(name = "number") final String number) {
    final List<RecordFromPhoneBook> userList = phoneBookService
        .searchRecordByNumber(number);
    return userList != null
        ? new ResponseEntity<>(userList, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}
