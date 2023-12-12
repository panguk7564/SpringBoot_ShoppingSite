package scripts.Shop.Entity.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scripts.Shop.Entity.Option.Oservice;
import scripts.Shop.core.security.CustomUserDetails;
import scripts.Shop.core.utils.ApiUtils;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class Pcontroller {

    @Autowired
    private final Pservice service;
    private final Oservice oservice;

    @PostMapping("/add")
    public ResponseEntity<?>add(@ModelAttribute ProductResponse dto, @RequestParam("file") MultipartFile [] files) throws IOException {
        service.addProduct(dto,files);

        return ResponseEntity.ok(ApiUtils.success("등록완료"));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id){
        String removed_item = service.FindItem(id);
        service.remove(id);
        return ResponseEntity.ok(ApiUtils.success(removed_item +" is removed"));
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

   @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductResponse.FindAllDto dto){
       String name = service.update(id.longValue(),dto);
       System.out.println(dto);

        if(dto != null && name != null){
        return ResponseEntity.ok(ApiUtils.success("업데이트 성공: "+ name));}
        else {
            System.out.println("상품없음");
            return ResponseEntity.ok("상품없음");
        }
   }
}