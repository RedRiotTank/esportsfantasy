package htt.esportsfantasybe.repository;

import htt.esportsfantasybe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


/**
 * UserRepository for User model.
 * @author Alberto Plaza Montes
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    /**
     * Find a user by mail.
     * @param Mail Mail of the user.
     * @return User object.
     */
    Optional<User> findByMail(String Mail);

    /**
     * Check if a user exists by mail.
     * @param mail Mail of the user.
     * @return Boolean.
     */
    Boolean existsUserByMail(String mail);

}
