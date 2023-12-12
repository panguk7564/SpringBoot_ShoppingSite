package scripts.Shop.Entity.Img;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImgController {

    @GetMapping("/download/{uuid}/{imgName}") // --> 파일 다운로드 기능
    public ResponseEntity<Resource> downloadFile(@PathVariable String uuid,
                                                 @PathVariable String imgName){
        Path filepath = Paths.get("C:/Users/G/Desktop/DB_Files/"+uuid+imgName); // -- 경로설정 +파일이름(uuid + 파일이름)

        try{
            Resource ressource = new UrlResource(filepath.toUri());
            if(ressource.exists()|| ressource.isReadable()){
                return ResponseEntity.ok().header(   // -- 응답헤더에 파일 주소를 첨부하여 다운로드로 넘김
                                "Content-Disposition",
                                "attachment; fileName=\""+ ressource.getFilename()+ "\"")
                        .body(ressource);
            }
            else {
                return ResponseEntity.notFound().build();
            }

        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
