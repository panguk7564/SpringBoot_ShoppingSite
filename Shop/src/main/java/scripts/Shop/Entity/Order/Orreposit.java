package scripts.Shop.Entity.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import scripts.Shop.Entity.Cart.Cart;
import scripts.Shop.Entity.Uuser.Uuser;

public interface Orreposit extends JpaRepository<Oorder, Long> {
    void deleteByUser(Uuser user);

    Page<Oorder> findAllByUserId(Long id, PageRequest of);
}
