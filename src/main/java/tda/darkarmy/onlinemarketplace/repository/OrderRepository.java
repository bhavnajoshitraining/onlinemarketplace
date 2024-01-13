package tda.darkarmy.onlinemarketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tda.darkarmy.onlinemarketplace.model.Orders;
import tda.darkarmy.onlinemarketplace.model.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser(User user);
}
