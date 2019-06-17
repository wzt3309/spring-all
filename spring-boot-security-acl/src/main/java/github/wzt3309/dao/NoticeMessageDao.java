package github.wzt3309.dao;

import github.wzt3309.domain.NoticeMessage;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface NoticeMessageDao {
    @PostFilter("hasPermission(filterObject, 'READ')")
    List<NoticeMessage> findAll();

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    NoticeMessage findById(Long id);

    @PreAuthorize("hasPermission(#noticeMessage, 'WRITE')")
    NoticeMessage save(NoticeMessage noticeMessage);

}
