package scripts.Shop.Entity.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scripts.Shop.Entity.Img.ImgService;
import scripts.Shop.core.utils.ApiUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class Pcontroller {

    @Autowired
    private final Pservice service;
    private final ImgService iservice;

    @PostMapping("/add")
    public ResponseEntity<?>add(@ModelAttribute ProductResponse dto, @RequestParam("file") MultipartFile [] file, HttpServletRequest request) throws IOException {

        if(service.addProduct(dto,file,request) != null){
            return ResponseEntity.ok(ApiUtils.success("등록완료"));}

        else {
            return ResponseEntity.ok(ApiUtils.error("누구세요?", HttpStatus.UNAUTHORIZED));
        }
    }

   @GetMapping("/products")
   public ResponseEntity<?> findall(@RequestParam(defaultValue = "0")int page){

       List<ProductResponse.FindAllDto> productResponses = service.findAll(page);
       ApiUtils.ApiResult<?> apiResult = ApiUtils.success(productResponses);
       return ResponseEntity.ok(apiResult);
   }

   @GetMapping("/products/{id}")
    public ResponseEntity<?> findByid(@PathVariable Long id){
       Product productDtos = service.findByid(id);
       ApiUtils.ApiResult<?> apiResult = ApiUtils.success(productDtos);
       return ResponseEntity.ok(apiResult);
   }

   @PostMapping("/updateitem/{id}")
    public ResponseEntity<?> updateitem(@ModelAttribute ProductResponse dto, @PathVariable Long id,
                                        @RequestParam(value = "file", required = false) MultipartFile [] file) throws IOException
   {
        if(dto != null){
            String name = service.update(id,dto);
            if(!file[0].isEmpty()){

                System.out.println("기존파일을 삭제합니다");
                iservice.deleteProductimg(id);

                if(file != null){
                System.out.println("업데이트중...");
                iservice.update(id, file);
                }
            }
            else {
                System.out.println("파일이 첨부되지 않았습니다.");
                iservice.deleteProductimg(id);
            }



        return ResponseEntity.ok(ApiUtils.success("업데이트 성공: "+ name));}

        else {
            System.out.println("상품없음");
            return ResponseEntity.ok("상품없음");
        }
   }
    @GetMapping("/useritem/delete/{id}")
    public ResponseEntity<?> deleteitem(@PathVariable Long id){
        service.delete(id);

        return ResponseEntity.ok().body("삭제성공: "+ id);
    }
}