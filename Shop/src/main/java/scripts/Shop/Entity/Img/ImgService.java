package scripts.Shop.Entity.Img;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImgService {
    private final ImgReposit reposit;

    @Transactional
    public Long saveImg(imgDto dto){
     ImgFile imgFile = reposit.save(dto.toen());
        return imgFile.getId();
    }

    public ImgFile findByUserid(Long id) {
        Optional<ImgFile> imgFile = reposit.findByUserId(id);
        return imgFile.get();
    }

    public List<ImgFile> findAllByProdutId(Long id) {
        List<ImgFile> imgs = reposit.findAllByProductId(id);
        return  imgs;
    }
}
