package scripts.Shop.Entity.Cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Cartreposit extends JpaRepository<Cart,Long> {
    List<Cart> findAllByUserId(Long id);
}
