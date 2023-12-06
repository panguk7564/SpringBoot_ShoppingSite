package scripts.Shop.Entity.Cart;

import lombok.Getter;
import lombok.Setter;
import scripts.Shop.Entity.Option.Option;
import scripts.Shop.Entity.Uuser.Uuser;

public class Cartrequest {

    @Getter
    @Setter
    public static class saveDto{
        private Long optionId;
        private Long quantity;

        public Cart toEn(Option option, Uuser user){
            return Cart.builder()
                    .option(option)
                    .user(user)
                    .maxQuantity(quantity)
                    .price(option.getPrice() * quantity)
                    .build();
        }
    }
}
