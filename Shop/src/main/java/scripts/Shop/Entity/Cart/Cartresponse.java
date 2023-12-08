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
                this.quantity = cart.getItem_Quantity();
                this.price = cart.getPrice();
            }
        }

    }

    @Getter
    @Setter
    public static class findAllDto { //-- 상품 전체출력

        List<ProductDto> products; //-- 상품을 리스트화

        private Long totalprice;

        public findAllDto(List<Cart> cartList) { // -- 장바구니에 리스트화된 상품 담기
            this.products = cartList.stream()
                    .map(cart -> cart.getOption().getProduct()).distinct()
                    .map(product -> new ProductDto(cartList, product)).collect(Collectors.toList());

            this.totalprice = cartList.stream()
                    .mapToLong(cart -> cart.getOption().getPrice() * cart.getItem_Quantity())// -- 전체 가격
                    .sum();
        }

        @Getter
        @Setter
        public class ProductDto {
            private Long id;

            private String productName;

            List<CartDto> cartDtos; //-- 장바구니에 담긴 상품 객체화

            public ProductDto(List<Cart> cartList, Product product) { //-- 상품 옵션과 같은 id의 상품 식별
                this.id = product.getId();
                this.productName = product.getProductName();
                this.cartDtos = cartList.stream()
                        .filter(cart -> cart.getOption().getId() == product.getId())
                        .map(CartDto::new).collect(Collectors.toList());
            }
        }

        @Getter
        @Setter
        private class CartDto { //-- 장바구니에 담은 상품 객체화
            private Long id;
            private OptionDto optionDto;
            private Long price;

            public CartDto(Cart cart) { 
                this.id = cart.getId();
                this.optionDto = new OptionDto(cart.getOption());// 카트의 id와 일치하는 상품 식별
                this.price = cart.getPrice();
            }
        }

        @Getter
        @Setter
        private class OptionDto {
            private Long id;
            private String optionName;
            private Long price;

            public OptionDto(Option option) { //-- 상품 객체 식별
                this.id = option.getId();
                this.optionName = option.getOptionName();
                this.price = option.getPrice();
            }
        }

    }
}