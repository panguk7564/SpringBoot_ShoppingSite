package scripts.Shop.Entity.Cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import scripts.Shop.core.security.CustomUserDetails;
import scripts.Shop.core.utils.ApiUtils;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class Cartcontroller {
    private final Cartservice service;

    @PostMapping("/mem/carts/add") // --- 장바구니에 상품 추가 // -- 매개변수에 인증이 되어있지 않다면 불러오지 않음
    public ResponseEntity<?> addCartList(@RequestBody @Valid List<Cartrequest.saveDto> requestDto,
                                         Error error,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) // -- 인증후 실행
    {
        service.addCartList(requestDto, customUserDetails.getUser()); // -- 인증된 유저에게 상품 추가

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/mem/cart/delete/{id}") // -- 장바구니 삭제
    public ResponseEntity<?> delete_cart(@PathVariable Long id,
                                         Error error,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) // -- 인증후 실행
    {
        service.deletecart(id); // -- 인증된 유저에게 상품 추가

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success("장바구니 목록이 삭제됨: 번호."+id);
        return ResponseEntity.ok(apiResult);
    }
}