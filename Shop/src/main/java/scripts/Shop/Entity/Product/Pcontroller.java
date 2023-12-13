package scripts.Shop.Entity.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scripts.Shop.Entity.Img.ImgService;
import scripts.Shop.Entity.Option.Oservice;
import scripts.Shop.core.security.CustomUserDetails;
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
        service.addProduct(dto,file,request);

        return ResponseEntity.ok(ApiUtils.success("등록완료"));
    }

   @GetMapping("/products")
   public ResponseEntity<?> findall(@RequestParam(defaultValue = "0")int page){

       List<ProductResponse.FindAllDto> productResponses = service.findAll(page);
       ApiUtils.ApiResult<?> apiResult = ApiUtils.success(productResponses);
       return ResponseEntity.ok(apiResult);
   }

   @GetMapping("/products/{id}")
    public ResponseEntity<?> findByid(@PathVariable Long id){
       ProductResponse.FindByIdDto productDtos = service.findByid(id);
       ApiUtils.ApiResult<?> apiResult = ApiUtils.success(productDtos);
       return ResponseEntity.ok(apiResult);
   }

   @PostMapping("/updateitem/{id}")
    public ResponseEntity<?> updateitem(@ModelAttribute ProductResponse dto, @PathVariable Long id,
                                        @RequestParam(value = "file", required = false) MultipartFile file) throws IOException
   {
        if(dto != null){
            System.out.println("업데이트중...");
            String name = service.update(id,dto);
            System.out.println(dto);

            if (file != null && !file.isEmpty()) {
                System.out.println("파일이요기잉네");
                iservice.update(id, file);
            }
            else {
                System.out.println("파일이 없습니다.");
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