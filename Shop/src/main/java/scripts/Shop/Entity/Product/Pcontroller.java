package scripts.Shop.Entity.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scripts.core.utils.ApiUtils;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Pcontroller {
    private final Pservice service;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ProductResponse.FindAllDto dto){
        Product product = service.addProduct(dto);

        return ResponseEntity.ok(ApiUtils.success(product));
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
    private ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductResponse.FindAllDto dto){
        String item = service.update(id,dto);

        return ResponseEntity.ok(ApiUtils.success("업데이트 되었습니다: "+item));
   }
}