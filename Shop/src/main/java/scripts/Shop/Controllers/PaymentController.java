package scripts.Shop.Controllers;

import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import scripts.Shop.Entity.Cart.Cart;
import scripts.Shop.Entity.Cart.Cartrequest;
import scripts.Shop.Entity.Cart.Cartservice;
import scripts.Shop.Entity.Order.Items.Item;
import scripts.Shop.Entity.Order.Oorder;
import scripts.Shop.Entity.Order.Orderesponse;
import scripts.Shop.Entity.Order.Orervices;
import scripts.Shop.core.security.CustomUserDetails;

@Controller
@RequiredArgsConstructor
public class PaymentController {
    private final Orervices services;
    private final Cartservice cartservice;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String CLIENT_ID = "S2_3084233975714f5097de55fd4a540e8c";
    private final String SECRET_KEY = "28254058abd141488521056c7b489c78";


    @GetMapping("/payment")
    public String indexDemo(@AuthenticationPrincipal CustomUserDetails userDetails,
                            Model model){


        List<Cart> cartList = cartservice.findById(userDetails.getUser());
        Oorder oorder = services.ordersave(userDetails.getUser());

        Long orderId = oorder.getId();
        int amount = cartList.size();
        Cart cart0 = cartList.get(0);
        Long totalPrice = cartList.stream().mapToLong(cart -> cart.getPrice() * cart.getItem_Quantity()).sum();


        if(amount == 1){
        model.addAttribute("orderName", cart0.getCartedName());
        System.out.println(cart0.getCartedName());
        }else {
        model.addAttribute("orderName", cart0.getCartedName() +" 외 "+ (amount - 1) +" 개의 상품");
        System.out.println(cart0.getCartedName() +" 외 "+ (amount - 1) +" 개의 상품");
        }
        model.addAttribute("orderId", orderId);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("count", amount);
        model.addAttribute("clientId", CLIENT_ID);
        model.addAttribute("userName", userDetails.getUser().getName());
        model.addAttribute("userid", userDetails.getUser().getId());
        return "/payindex";
    }

    @RequestMapping(value="/cancel")
    public String cancelDemo(){
        return "/cancel";
    }

    @RequestMapping("/payed/{id}")
    public String requestPayment(
            @RequestParam String tid,
            @RequestParam Long amount,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id,
            Model model) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((CLIENT_ID + ":" + SECRET_KEY).getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> AuthenticationMap = new HashMap<>();
        AuthenticationMap.put("amount", String.valueOf(amount));

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(AuthenticationMap), headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
                "https://sandbox-api.nicepay.co.kr/v1/payments/" + tid, request, JsonNode.class);

        JsonNode responseNode = responseEntity.getBody();
        String resultCode = responseNode.get("resultCode").asText();
        model.addAttribute("resultMsg", responseNode.get("resultMsg").asText());

        System.out.println(responseNode.toPrettyString());

        if (resultCode.equalsIgnoreCase("0000")) {
            Oorder oorder = services.findOrderByid(id);
            services.save(userDetails.getUser(),oorder);
        } else {
            services.deleteById(id);
        }

        return "/payed";
    }

    @RequestMapping("/cancelAuth")
    public String requestCancel(
            @RequestParam String tid,
            @RequestParam String amount,
            Model model) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((CLIENT_ID + ":" + SECRET_KEY).getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> AuthenticationMap = new HashMap<>();
        AuthenticationMap.put("amount", amount);
        AuthenticationMap.put("reason", "test");
        AuthenticationMap.put("orderId", UUID.randomUUID().toString());

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(AuthenticationMap), headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
                "https://sandbox-api.nicepay.co.kr/v1/payments/"+ tid +"/cancel", request, JsonNode.class);

        JsonNode responseNode = responseEntity.getBody();
        String resultCode = responseNode.get("resultCode").asText();
        model.addAttribute("resultMsg", responseNode.get("resultMsg").asText());

        System.out.println(responseNode.toPrettyString());

        if (resultCode.equalsIgnoreCase("0000")) {
            // 취소 성공 비즈니스 로직 구현
        } else {
            // 취소 실패 비즈니스 로직 구현
        }

        return "/response";
    }

    @RequestMapping("/hook")
    public ResponseEntity<String> hook(@RequestBody HashMap<String, Object> hookMap) throws Exception {
        String resultCode = hookMap.get("resultCode").toString();

        System.out.println(hookMap);

        if(resultCode.equalsIgnoreCase("0000")){
            return ResponseEntity.status(HttpStatus.OK).body("ok");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}