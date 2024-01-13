package tda.darkarmy.onlinemarketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tda.darkarmy.onlinemarketplace.model.CartItem;
import tda.darkarmy.onlinemarketplace.model.Product;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByProduct(Product product);

}
