package com.tsipadan.tsipadan.service.api;

import com.tsipadan.tsipadan.model.RecordFromPhoneBook;
import java.util.List;

/**
 * Interface by Phone book with classical CRUD operations
 * and searching records
 */
public interface PhoneBookService {

  RecordFromPhoneBook createRecordInPb(int userId, RecordFromPhoneBook recordFromPb);

  RecordFromPhoneBook getOneRecordFromPb(int userId, int recordId);

  boolean deleteRecordFromPb(int userId, int recordId);

  boolean updateRecordFromPb(int userId, int recordId, RecordFromPhoneBook record);

  List<RecordFromPhoneBook> getAllRecordsFromPb(int userId);

  List<RecordFromPhoneBook> searchRecordByNumber(String number);

}
