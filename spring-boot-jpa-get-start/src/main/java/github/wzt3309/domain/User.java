package github.wzt3309.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    public User() {
    }

    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private Integer age;

}
