package scripts.Shop.Entity.Cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import scripts.Shop.core.security.CustomUserDetails;
import scripts.Shop.core.utils.ApiUtils;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class Cartcontroller {
    private final Cartservice service;

    @PostMapping("/carts/add") // --- 장바구니에 상품 추가
    public ResponseEntity<?> addCartList(@RequestBody @Valid List<Cartrequest.saveDto> saveDtos,
                                         Error error,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails)
    {
        service.addCartList(saveDtos, customUserDetails.getUser());

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/carts") // -- 장바구니 불러오기
    public ResponseEntity<?> carts(@AuthenticationPrincipal CustomUserDetails customUserDetails){ // -- 매개변수에 인증이 되어있지 않다면 불러오지 않음
        Cartresponse.findAllDto dto = service.findAll();

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(dto);
        return ResponseEntity.ok(apiResult);
    }


}
