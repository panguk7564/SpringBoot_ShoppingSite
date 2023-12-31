package scripts.Shop.Entity.Option;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import scripts.Shop.Entity.Product.Product;

import java.util.List;

public interface Oreposit extends JpaRepository<Option, Long> {
    List<Option> findByProductId(Long Id);

    List<Option> findAllByProduct(Product product);


    Page<Option> findAllByProductId(Long id, PageRequest of);
}
