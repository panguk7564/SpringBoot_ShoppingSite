package scripts.Shop.Entity.Order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scripts.Shop.Entity.Cart.Cartservice;
import scripts.Shop.core.utils.ApiUtils;

@RestController
@RequiredArgsConstructor
public class Orcontroller {
    private final Ordservices services;


/*
    @PostMapping("/mem/cart/order")
    public ResponseEntity<?> order(@AuthenticationPrincipal CustomUserDetails userDetails){

        Orderesponse.FindbyIdDto idDto = services.save(userDetails.getUser());
        int amount = idDto.getProductDtos().size();


        System.out.println("주문번호:"+ idDto.getId()+ " 주문상품수 :"+amount+ " 총 결제금액: "+idDto.getTotalPrice());

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success("");
        return ResponseEntity.ok(apiResult);
    }

 */

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
    @GetMapping("/mem/order/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        services.deleteOrder(id);

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(id + " 주문내역 삭제");
        return ResponseEntity.ok(apiResult);
    }
}