package scripts.Shop.Entity.Cart;

import lombok.Getter;
import lombok.Setter;
import scripts.Shop.Entity.Option.Option;
import scripts.Shop.Entity.Product.Product;

import java.util.List;
import java.util.stream.Collectors;

public class Cartresponse { // -- 교차형 데이터 공유(브릿지패턴)


    @Getter
    @Setter
    public static class updateDto {

        private List<CartDto> dtoList;
        private Long totalprice;

        public updateDto(List<Cart> dtoList) { // 전체 상품 목록 객체화
            this.dtoList = dtoList.stream().map(CartDto::new).collect(Collectors.toList());
            this.totalprice = totalprice;
        }

        @Getter
        @Setter
        private class CartDto { //-- 상품 목록 객체화

            private Long cartId;

            private Long option;

            private String optionName;

            private Long quantity;

            private Long price;

            public CartDto(Cart cart) {
                this.cartId = cart.getId();
                this.option = cart.getOption().getId();
                this.optionName = cart.getOption().getOptionName();
                this.quantity = cart.getMaxQuantity();
                this.price = cart.getPrice();
            }
        }

    }

    @Getter
    @Setter
    public static class findAllDto { //-- 상품 전체출력

        List<ProductDto> products;

        private Long totalprice;

        public findAllDto(List<Cart> cartList) {
            this.products = cartList.stream()
                    .map(cart -> cart.getOption().getProduct()).distinct()
                    .map(product -> new ProductDto(cartList, product)).collect(Collectors.toList());

            this.totalprice = cartList.stream()
                    .mapToLong(cart -> cart.getOption().getPrice() * cart.getMaxQuantity())
                    .sum();
        }

        @Getter
        @Setter
        public class ProductDto {
            private Long id;

            private String productName;

            List<CartDto> cartDtos;

            public ProductDto(List<Cart> cartList, Product product) {
                this.id = product.getId();
                this.productName = product.getProductName();
                this.cartDtos = cartList.stream()
                        .filter(cart -> cart.getOption().getId() == product.getId())
                        .map(CartDto::new).collect(Collectors.toList());
            }
        }

        @Getter
        @Setter
        private class CartDto {
            private Long id;
            private OptionDto optionDto;
            private Long price;

            public CartDto(Cart cart) {
                this.id = cart.getId();
                this.optionDto = new OptionDto(cart.getOption());
                this.price = cart.getPrice();
            }
        }

        @Getter
        @Setter
        private class OptionDto {
            private Long id;
            private String optionName;
            private Long price;

            public OptionDto(Option option) {
                this.id = option.getId();
                this.optionName = option.getOptionName();
                this.price = option.getPrice();
            }
        }

    }
}