package scripts.Shop.Entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Preposit extends JpaRepository<Product,Long> {
    List<Product> findByUserId(Long id);
}
