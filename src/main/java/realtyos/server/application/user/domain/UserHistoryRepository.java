package realtyos.server.application.user.domain;

import java.util.Optional;

public interface UserHistoryRepository {
    void save(UserHistory userHistory);
    Optional<UserHistory> findByUserId(Long userId);
}
