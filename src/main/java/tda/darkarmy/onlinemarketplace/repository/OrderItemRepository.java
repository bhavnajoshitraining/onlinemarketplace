package tda.darkarmy.onlinemarketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tda.darkarmy.onlinemarketplace.model.OrderItem;
import tda.darkarmy.onlinemarketplace.model.Product;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByProduct(Product product);
}
