package scripts.Shop.Entity.Img;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import scripts.Shop.Entity.Uuser.Ureposit;
import scripts.Shop.Entity.Uuser.Uuser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImgService {
    private final ImgReposit reposit;
    private final Ureposit ureposit;
    private final String filePath = "C:/Users/G/Desktop/DB_Files/";
    //private final String filePath = "C:/Users/bongd/Desktop/DB_Files/";

    @Transactional
    public Long saveImg(imgDto dto){
     ImgFile imgFile = reposit.save(dto.toen());
        return imgFile.getId();
    }

    public ImgFile findByUserid(Long id) {
        Optional<ImgFile> imgFile = reposit.findByUserId(id);
        if(imgFile.isPresent()){
            return imgFile.get();
        }
        else {
        return null;
        }
    }

    public List<ImgFile> findAllByProdutId(Long id) {
        List<ImgFile> imgs = reposit.findAllByProductId(id);
        return  imgs;
    }

    @Transactional
    public void update(Long id, MultipartFile file) throws IOException {

        if(reposit.findByUserId(id).isPresent()){
           deleteimg(id);
           System.out.println("기존 이미지 삭제");
        }

        Path uploadpath = Paths.get(filePath);

        if (!Files.exists(uploadpath)) {
            Files.createDirectories(uploadpath);
        }


        if (!file.isEmpty()) {
            // -- 파일명 추출
            String originFileName = file.getOriginalFilename();


            // -- 확장자 추출
            assert originFileName != null;
            String formatType = originFileName.substring(originFileName.lastIndexOf("."));

            System.out.println("파일명: " + originFileName);
            System.out.println("확장자: " + formatType);

            //-- UUID 생성
            String uuid = UUID.randomUUID().toString();

            String path = filePath + uuid + originFileName;

            file.transferTo(new File(path)); // -- 경로에 파일을 저장(파일이름: uuid+원본이름)

            ImgFile imgFile = ImgFile.builder() // -- 파일 객체 생성
                    .filePath(filePath)
                    .imgName(originFileName)
                    .uuid(uuid)
                    .imgType(formatType)
                    .imgSize(file.getSize())
                    .userId(id)
                    .build();

            reposit.save(imgFile);

        } else {
            System.out.println("파일이 엄서요");
           }
    }


    @Transactional
    public void deleteimg(Long id) {
        Optional<ImgFile> oldFile = reposit.findByUserId(id);
        reposit.deleteById(oldFile.get().getId());
        System.out.println(oldFile.get().getUserId());
    }
}
