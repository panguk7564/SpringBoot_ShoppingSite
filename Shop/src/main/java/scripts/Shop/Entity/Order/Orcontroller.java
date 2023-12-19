package scripts.Shop.Entity.Order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import scripts.Shop.Entity.Cart.Cart;
import scripts.Shop.Entity.Cart.Cartrequest;
import scripts.Shop.Entity.Cart.Cartservice;
import scripts.Shop.core.security.CustomUserDetails;
import scripts.Shop.core.utils.ApiUtils;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class Orcontroller {
    private final Orervices services;
    private final Cartservice cartservice;

    /*
    @PostMapping("/orders/save")
    public ResponseEntity<?> ordersave(@AuthenticationPrincipal CustomUserDetails userDetails){
        Orderesponse.FindbyIdDto findbyIdDto = services.save(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success("주문중: "+findbyIdDto));
    }

     */

    @PostMapping("/mem/cart/order")
    public ResponseEntity<?> order(@RequestBody @Valid Cartrequest.orderto dto,
                                   @AuthenticationPrincipal CustomUserDetails userDetails){

        Long id = dto.getCartId();


        ApiUtils.ApiResult<?> apiResult = ApiUtils.success("");
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findByid(@PathVariable Long id){
        Orderesponse.FindbyIdDto findbyIdDto = services.findByid(id);

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(findbyIdDto);
        return ResponseEntity.ok(apiResult);
    }

    @PostMapping("/orders/clear")
    public ResponseEntity<?> clear() {
        services.delete();

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success("삭제");
        return ResponseEntity.ok(apiResult);
    }
}