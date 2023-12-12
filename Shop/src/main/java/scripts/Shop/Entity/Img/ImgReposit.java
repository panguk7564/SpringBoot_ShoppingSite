package scripts.Shop.Entity.Img;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImgReposit extends JpaRepository<ImgFile, Long> {
    Optional<ImgFile> findByUuid(String uuid);

    Optional<ImgFile> findByUserId(Long id);

    List<ImgFile> findAllByProductId(Long id);
}
