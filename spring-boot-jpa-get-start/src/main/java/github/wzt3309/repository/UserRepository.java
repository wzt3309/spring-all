package github.wzt3309.repository;

import github.wzt3309.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    List<User> findAll();

    @Override
    <S extends User> S save(S entity);

    @Override
    Optional<User> findById(Long aLong);

    Optional<User> findByName(String name);
}
