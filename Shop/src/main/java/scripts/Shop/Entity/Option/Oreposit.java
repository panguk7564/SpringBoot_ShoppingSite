package scripts.Shop.Entity.Option;

import org.springframework.data.jpa.repository.JpaRepository;
import scripts.Shop.Entity.Product.Product;

import java.util.List;
import java.util.Optional;

public interface Oreposit extends JpaRepository<Option, Long> {
    List<Option> findByProductId(Long Id);

}
