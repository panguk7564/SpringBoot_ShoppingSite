package scripts.Shop.Entity.Option;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import scripts.Shop.core.security.CustomUserDetails;
import scripts.Shop.core.utils.ApiUtils;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class Ocontroller {
    private final Oservice service;

    /**
     *
     * @param id
     * // -- id에 관한 설명(Product ID)
     * @return
     * -- 반환값에 대한 설명(List<OResponse.FindProductIdDto>)
     */

    @GetMapping("/products/{id}/options") // --- 개별 옵션상품 검색
    public ResponseEntity<?> findbyPid(@PathVariable Long id){
        List<OResponse.FindProductIdDto> optionsResponse = service.findByProductId(id); //-- 수정요망
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(optionsResponse);

        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/options") //-- 전체 옵션 출력
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        System.out.println(customUserDetails.getUser());

        List<OResponse.FindAllDto> optionsResponse = service.findAll(); //-- 수정요망
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(optionsResponse);

        return ResponseEntity.ok(apiResult);
    }

    @PostMapping("/add_option/{id}")
    public ResponseEntity<?> create(@PathVariable Long id,@RequestBody OResponse dto){

        String result = service.add(id,dto);

        return ResponseEntity.ok().body("옵션 등록 성공: "+ result);
    }

    @GetMapping("/remove_option/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        String name = service.delete(id);
        if(name != null){
        return ResponseEntity.ok().body("삭제완료: "+name +"id: "+id);}
        else {
            return ResponseEntity.ok().body("삭제할 옵션이 없어요");
        }
    }

    @PostMapping("/update_option/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody OResponse dto){
        String result = service.update(id,dto);

        return ResponseEntity.ok().body("업데이트 성공: "+ result + "id: "+id);
    }


}
