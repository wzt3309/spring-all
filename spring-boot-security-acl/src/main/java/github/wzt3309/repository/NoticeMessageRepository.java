package github.wzt3309.repository;

import github.wzt3309.domain.NoticeMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeMessageRepository extends JpaRepository<NoticeMessage, Long> {
    @Override
    List<NoticeMessage> findAll();

    @Override
    Optional<NoticeMessage> findById(Long id);

    @Override
    <S extends NoticeMessage> S save(S s);
}
