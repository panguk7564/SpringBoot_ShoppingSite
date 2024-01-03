package scripts.Shop.Entity.Cart;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import scripts.Shop.Entity.Option.Option;
import scripts.Shop.Entity.Option.Oservice;
import scripts.Shop.Entity.Product.Product;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
public class Cartresponse {// -- 교차형 데이터 공유(브릿지패턴)
    private final Oservice oservice;

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
                this.option = cart.getOptionId();
                this.optionName = cart.getCartedName();
                this.quantity = cart.getItem_Quantity();
                this.price = cart.getPrice();
            }
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
                this.optionDto = new OptionDto(oservice.findById(cart.getOptionId()));// 카트의 id와 일치하는 상품 식별
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