package scripts.Shop.Entity.Cart;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import scripts.Shop.Entity.Option.Option;
import scripts.Shop.Entity.Option.Oservice;
import scripts.Shop.Entity.Order.Oorder;
import scripts.Shop.Entity.Uuser.Uuser;

public class Cartrequest {

    @Getter
    @Setter
    public static class orderto{
        private Long cartId;
        private Long quantity;

        public Oorder toOrderEn(Uuser user){
            return Oorder.builder()
                    .user(user)
                    .build();
        }
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class saveDto{ // -- 저장 DTO
        private Long optionId;
        private Long quantity;
        private final Oservice oservice;

        public Cart toEn(Long option, Uuser user){
            Option option1 = oservice.findById(option);

            return Cart.builder()
                    .option(option) //-- 옵션 fk
                    .user(user) //-- 유저정보 fk
                    .item_Quantity(quantity) //-- 담을수 있는 최대 수
                    .price(option1.getPrice() * quantity) // -- 전체 상품 가격
                    .cartedName(option1.getProduct().getProductName() + option1.getOptionName())
                    .build();
        }
    }

    @Getter
    @Setter
    public static class updateDto{
        private Long cartid;
        private Long quantity;
    }
}
