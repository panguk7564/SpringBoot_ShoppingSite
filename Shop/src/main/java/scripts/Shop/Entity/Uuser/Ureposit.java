package scripts.Shop.Entity.Uuser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Ureposit extends JpaRepository<Uuser,Long> {

    Optional<Uuser> findByEmail(String email);
}
