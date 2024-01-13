package tda.darkarmy.onlinemarketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tda.darkarmy.onlinemarketplace.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
