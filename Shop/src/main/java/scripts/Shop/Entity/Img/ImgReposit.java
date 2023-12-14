package scripts.Shop.Entity.Img;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImgReposit extends JpaRepository<ImgFile, Long> {

    List<ImgFile> findAllByProductId(Long id);

    Optional<ImgFile> findByUuserId(Long id);
}
