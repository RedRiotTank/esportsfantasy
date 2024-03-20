package htt.esportsfantasybe.repository;

import htt.esportsfantasybe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByMail(String Mail);
    Boolean existsUserByMail(String mail);

}
