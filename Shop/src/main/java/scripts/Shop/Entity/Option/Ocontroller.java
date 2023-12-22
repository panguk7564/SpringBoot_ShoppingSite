package scripts.Shop.Entity.Option;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scripts.Shop.Entity.Product.Product;
import scripts.Shop.Entity.Product.Pservice;
import scripts.Shop.core.security.CustomUserDetails;
import scripts.Shop.core.utils.ApiUtils;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class Ocontroller {
    private final Oservice service;
    private final Pservice pservice;
    /*
     *
     * @param id
     * // -- id에 관한 설명(Product ID)
     * @return
     * -- 반환값에 대한 설명(List<OResponse.FindProductIdDto>)
     */
    @PostMapping("/mem/registitem/options/save/{id}") // -- 유저 등록상품 옵션 등록
    public ResponseEntity<?> save(@ModelAttribute OResponse dto, @PathVariable Long id) {
        Product product = pservice.findByid(id);
        service.save(dto,product);

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success("옵션추가 완료 : 상품 ="+ product.getProductName());

        return ResponseEntity.ok(apiResult);
    }

    @PostMapping("/mem/registitem/option/update/save/{id}")// -- 유저 등록상품 옵션 업데이트
    public ResponseEntity<?> option_update(@PathVariable Long id, @ModelAttribute OResponse dto){
        String result = service.update(id,dto);

        return ResponseEntity.ok().body("업데이트 성공: "+ "옵션명 = "+result);
    }

    @GetMapping("/mem/registitem/option/delete/{id}") // -- 유저 등록상품 옵션 삭제
    public ResponseEntity<?> option_delete(@PathVariable Long id){
        String name = service.delete(id);
        if(name != null){
        return ResponseEntity.ok().body("삭제완료: "+name +"id: "+id);}
        else {
            return ResponseEntity.ok().body("삭제할 옵션이 없어요");
        }
    }
}
