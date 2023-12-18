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

    @PostMapping("/useritem/options/save/{id}") // -- 옵션 등록
    public ResponseEntity<?> save(@ModelAttribute OResponse dto, @PathVariable Long id) {
        Product product = pservice.findByid(id);
        service.save(dto,product);

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success("옵션추가 완료 : 상품 ="+ product.getProductName());

        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/products/{id}/options") // --- 개별 옵션상품 검색
    public ResponseEntity<?> findbyPid(@PathVariable Long id){
        List<OResponse.FindProductIdDto> optionsResponse = service.findByProductId(id); //-- 수정요망
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(optionsResponse);

        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/options") //-- 전체 옵션 출력
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        System.out.println(customUserDetails.getUser().getId());

        List<OResponse.FindAllDto> optionsResponse = service.findAll();
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(optionsResponse);

        return ResponseEntity.ok(apiResult);
    }

    @PostMapping("/mem/registitem/option/update/save/{id}")// --옵션 업데이트
    public ResponseEntity<?> option_update(@PathVariable Long id, @ModelAttribute OResponse dto){
        String result = service.update(id,dto);

        return ResponseEntity.ok().body("업데이트 성공: "+ "옵션명 = "+result);
    }

    @GetMapping("/mem/registitem/option/delete/{id}") // --옵션 삭제
    public ResponseEntity<?> option_delete(@PathVariable Long id){
        String name = service.delete(id);
        if(name != null){
        return ResponseEntity.ok().body("삭제완료: "+name +"id: "+id);}
        else {
            return ResponseEntity.ok().body("삭제할 옵션이 없어요");
        }
    }


}
