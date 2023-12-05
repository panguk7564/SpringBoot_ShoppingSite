package scripts.Shop.Entity.Option;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import scripts.Shop.Entity.Product.ProductResponse;
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

    @GetMapping("/options") //-- 존체 옵션 출력
    public ResponseEntity<?> findAll(){
        List<OResponse.FindAllDto> optionsResponse = service.findAll(); //-- 수정요망
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(optionsResponse);

        return ResponseEntity.ok(apiResult);
    }
}
