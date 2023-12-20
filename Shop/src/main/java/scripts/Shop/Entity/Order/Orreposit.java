package scripts.Shop.Entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import scripts.Shop.Entity.Uuser.Uuser;

public interface Orreposit extends JpaRepository<Oorder, Long> {
    void deleteByUser(Uuser user);
}
