package scripts.Shop.Entity.Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import scripts.Shop.Entity.Uuser.Uuser;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Cartreposit extends JpaRepository<Cart,Long> {
    List<Cart> findAllByUserId(Long id); // -- 유저아이디로 찾기

    List<Cart> deleteAllByUserId(Long id);

    Optional<Uuser> findByUserId(Long id);
}
