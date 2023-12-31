package scripts.Shop.Entity.Option;

import lombok.Getter;
import lombok.Setter;
import scripts.Shop.Entity.Product.Product;


@Getter
@Setter
public class OResponse {

    private Long Id;

    private Product product;

    private String optionName; //-- 옵션이름

    private Long price; //-- 옵션가격

    private Long quantity; //-- 옵션수량

    public Option toEn(){
        return Option.builder()
                .product(product)
                .optionName(optionName)
                .price(price)
                .quantity(quantity)
                .build();
    }

    @Getter
    @Setter
    public static class FindProductIdDto{
        private Long Id;

        private Long productId;

        private String optionName;

        private Long price;

        private Long quantity;

        public FindProductIdDto(Option option) {
            this.Id = option.getId();
            this.productId =  option.getProduct().getId();
            this.optionName =  option.getOptionName();
            this.price =  option.getPrice();
            this.quantity =  option.getQuantity();
        }
    }

    @Getter
    @Setter
    public static class FindAllDto{
        private Long Id;

        private Long productId;

        private String optionName;

        private Long price;

        private Long quantity;

        public FindAllDto(Option option) {
            this.Id = option.getId();
            this.productId =  option.getProduct().getId();
            this.optionName =  option.getOptionName();
            this.price =  option.getPrice();
            this.quantity =  option.getQuantity();
        }
    }
}
