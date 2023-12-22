package scripts.Shop.Entity.Order.Items;

import org.springframework.data.jpa.repository.JpaRepository;
import scripts.Shop.Entity.Order.Oorder;

import java.util.List;
import java.util.Optional;

public interface ItemsReposit extends JpaRepository<Item,Long> {
    List<Item> findAllByOrderId(Long id);

    void deleteByOrderId(Long id);
}
