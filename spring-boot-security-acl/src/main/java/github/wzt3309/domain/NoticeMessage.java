package github.wzt3309.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "system_message")
@Getter
@Setter
public class NoticeMessage {
    @Id
    @Column
    private Long id;
    @Column
    private String content;
}
