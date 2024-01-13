package tda.darkarmy.onlinemarketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tda.darkarmy.onlinemarketplace.model.Cart;
import tda.darkarmy.onlinemarketplace.model.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
