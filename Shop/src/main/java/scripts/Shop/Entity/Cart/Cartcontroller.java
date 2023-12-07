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

    @PostMapping("/carts/add") // --- 장바구니에 상품 추가 // -- 매개변수에 인증이 되어있지 않다면 불러오지 않음
    public ResponseEntity<?> addCartList(@RequestBody @Valid List<Cartrequest.saveDto> requestDto,
                                         Error error,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) // -- 인증후 실행
    {   System.out.println("0o0");
        service.addCartList(requestDto, customUserDetails.getUser()); // -- 인증된 유저에게 상품 추가

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/carts/update") // -- 장바구니 업데이트
    public ResponseEntity<?> update(@RequestBody @Valid List<Cartrequest.updateDto> requestDto,
                                    Error error,
                                    @AuthenticationPrincipal CustomUserDetails customUserDetails){

        Cartresponse.updateDto updatedto = service.update(requestDto, customUserDetails.getUser()); //--인증된 유저의 상품 업데이트

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(updatedto);
        return ResponseEntity.ok(apiResult);
    }


    @GetMapping("/carts") // -- 장바구니 불러오기
    public ResponseEntity<?> carts(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        Cartresponse.findAllDto dto = service.findAll();//-- 인증된 유저의 상품 전체 불러오기

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(dto);
        return ResponseEntity.ok(apiResult);
    }


}
