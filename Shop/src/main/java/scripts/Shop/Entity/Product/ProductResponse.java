package scripts.Shop.Entity.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scripts.Shop.Entity.Option.Option;

import java.util.List;
import java.util.stream.Collectors;


public class ProductResponse {

    @NoArgsConstructor
    @Getter
    @Setter
    public static class FindAllDto{
        //-- pk
        private Long Id;
        // -- 상품명
        private String productName;
        //-- 상품설명
        private String description;
        // -- 상품이미지
        private String img;
        //-- 상품 이미지
        private Long price;
        // -- 상품 가격
        private Long stock;


        public Product toEntity(){
            return Product.builder()
                    .id(Id)
                    .productName(productName)
                    .description(description)
                    .img(img)
                    .price(price)
                    .build();
        }

        public FindAllDto(Product product) { //-- 이렇게 해도 된다.(빌더를 쓰기 불편할때 ?)
            this.Id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.img = product.getImg();
            this.price = product.getPrice();
            this.stock = getStock();
        }
    }

    @Getter
    @Setter
    public static class FindByIdDto{
        //-- pk
        private Long Id;
        // -- 상품명
        private String productName;
        //-- 상품설명
        private String description;
        // -- 상품이미지
        private String img;
        //-- 상품 이미지
        private Long price;
        // -- 상품 가격
        private List<OptionDto> optionList;

        public FindByIdDto(Product product, List<Option> optionsList) {
            this.Id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.img = product.getImg();
            this.price = product.getPrice();
            this.optionList = optionsList.stream().map(OptionDto::new)
                    .collect(Collectors.toList());
        }
    }

    @Setter
    @Getter
    public static class OptionDto {
        private Long id;
        private String optionName;
        private Long price;
        private Long quantity;

        public OptionDto(Option option) {
            this.id = option.getId();
            this.optionName = option.getOptionName();
            this.price = option.getPrice();
            this.quantity = option.getQuantity();

        }
    }


}
