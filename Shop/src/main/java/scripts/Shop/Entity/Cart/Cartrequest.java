package scripts.Shop.Entity.Cart;

import lombok.Getter;
import lombok.Setter;
import scripts.Shop.Entity.Option.Option;
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
    public static class saveDto{ // -- 저장 DTO
        private Long optionId;
        private Long quantity;

        public Cart toEn(Option option, Uuser user){
            return Cart.builder()
                    .option(option) //-- 옵션 fk
                    .user(user) //-- 유저정보 fk
                    .item_Quantity(quantity) //-- 담을수 있는 최대 수
                    .price(option.getPrice() * quantity) // -- 전체 상품 가격
                    .cartedName(option.getProduct().getProductName() + option.getOptionName())
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
