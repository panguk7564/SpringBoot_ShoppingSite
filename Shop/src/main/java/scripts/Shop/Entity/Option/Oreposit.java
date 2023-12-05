package scripts.Shop.Entity.Option;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Oreposit extends JpaRepository<Option, Long> {
    List<Option> findByProductId(Long Id);
}
