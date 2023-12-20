package scripts.Shop.Entity.Cart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import scripts.Shop.Entity.Uuser.Uuser;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Cartreposit extends JpaRepository<Cart,Long> {
    Page<Cart> findAllByUserId(Long id, PageRequest of); // -- 유저아이디로 찾기

    List<Cart> findByUserId(Long id);

    List<Cart> findAllByUser(Uuser user);
}
