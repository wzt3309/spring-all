package github.wzt3309.dao.impl;

import github.wzt3309.dao.NoticeMessageDao;
import github.wzt3309.domain.NoticeMessage;
import github.wzt3309.repository.NoticeMessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NoticeMessageDaoImpl implements NoticeMessageDao {
    private NoticeMessageRepository noticeMessageRepository;

    @Override
    public List<NoticeMessage> findAll() {
        return noticeMessageRepository.findAll();
    }

    @Override
    public NoticeMessage findById(Long id) {
        return noticeMessageRepository.findById(id).orElse(null);
    }

    @Override
    public NoticeMessage save(NoticeMessage noticeMessage) {
        return noticeMessageRepository.save(noticeMessage);
    }
}
