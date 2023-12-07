package scripts.Shop.Entity.Order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import scripts.Shop.core.security.CustomUserDetails;
import scripts.Shop.core.utils.ApiUtils;

@RestController
@RequiredArgsConstructor
public class Orcontroller {
    private final Orervices services;

    @PostMapping("/orders/save")
    public ResponseEntity<?> ordersave(@AuthenticationPrincipal CustomUserDetails userDetails){
        Orderesponse.FindbyIdDto findbyIdDto = services.save(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success("주문중: "+findbyIdDto));
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