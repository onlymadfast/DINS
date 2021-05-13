package com.tsipadan.tsipadan.service.implementation;

import com.tsipadan.tsipadan.model.RecordFromPhoneBook;
import com.tsipadan.tsipadan.model.User;
import com.tsipadan.tsipadan.repository.UserRepository;
import com.tsipadan.tsipadan.service.api.PhoneBookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * создания, получения (по id), удаления, редактирования записи в телефонной книжке
 * получения списка всех записей в телефонной книжке пользователя
 */
@Service
@RequiredArgsConstructor
public class PhoneBookServiceImpl implements PhoneBookService {

  private final UserRepository userRepository;

  @Override
  public RecordFromPhoneBook createRecordInPb(final int userId, final RecordFromPhoneBook recordFromPb) {
    User user = userRepository.getUser(userId);
    user.getPhoneBook().add(RecordFromPhoneBook.count++, recordFromPb);
    return recordFromPb;
  }

  @Override
  public RecordFromPhoneBook getOneRecordFromPb(final int userId, final int recordId) {
    return userRepository.getUser(userId).getPhoneBook().get(recordId);
  }

  @Override
  public boolean deleteRecordFromPb(final int userId, final int recordId) {
    return userRepository.getUser(userId).getPhoneBook().remove(recordId) != null;
  }

  @Override
  public boolean updateRecordFromPb(final int userId, final int recordId, final RecordFromPhoneBook record) {
    if (userRepository.getUser(userId) != null) {
      userRepository.getUser(userId).getPhoneBook().set(recordId, record);
      return true;
    }
    return false;
  }

  @Override
  public List<RecordFromPhoneBook> getAllRecordsFromPb(final int userId) {
    return userRepository.getUser(userId).getPhoneBook();
  }

  @Override
  public List<RecordFromPhoneBook> searchRecordByNumber(final String number) {
    return userRepository.searchRecordByNumber(number);
  }
}
