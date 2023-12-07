package scripts.Shop.Entity.Order.Items;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsReposit extends JpaRepository<Item,Long> {
    List<Item> findAllByOrderId(Long id);
}
