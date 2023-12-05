package scripts.Shop.Entity.Option;

import lombok.Getter;
import lombok.Setter;
import scripts.Shop.Entity.Product.Product;


@Getter
@Setter
public class Odto {

    private Long Id;

    private Product product;

    private String optionName; //-- 옵션이름

    private String o_img; //-- 옵션 사진

    private Long price; //-- 옵션가격

    private Long quantity; //-- 옵션수량

    public Option toEn(){
        return Option.builder()
                .id(Id)
                .product(product)
                .optionName(optionName)
                .o_img(o_img)
                .price(price)
                .quantity(quantity)
                .build();
    }
}
