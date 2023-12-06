package scripts.Shop.Entity.Cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scripts.Shop.Entity.Option.Option;
import scripts.Shop.Entity.Option.Oreposit;
import scripts.Shop.Entity.Uuser.Uuser;
import scripts.Shop.core.error.exception.Exception400;
import scripts.Shop.core.error.exception.Exception404;
import scripts.Shop.core.error.exception.Exception500;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class Cartservice {

    private final Cartreposit cartreposit;
    private final Oreposit oreposit;

    public Cartresponse.findAllDto findAll() {
        List<Cart> cartList = cartreposit.findAll();
        return new Cartresponse.findAllDto(cartList);
    }


    @Transactional
    public void addCartList(List<Cartrequest.saveDto> saveDtos, Uuser user) { // -- 장바구니 담기

        Set<Long> optionId = new HashSet<>(); //-- 동일한 데이터를 묶어줌

        for(Cartrequest.saveDto cart : saveDtos){ // -- 동일상품 예외처리
            if(!optionId.add(cart.getOptionId()));
            throw new Exception400("동일 상품 옵션 중복됨: "+cart.getOptionId());
        }

        List<Cart> cartList = saveDtos.stream().map(cartdto -> //-- 상품 존재유무 확인
        {
            Option option = oreposit.findById(cartdto.getOptionId()).orElseThrow(
                    () -> new Exception404("해당 상품 옵션 못찾음: "+ cartdto.getOptionId()));

            return cartdto.toEn(option, user);

        }).collect(Collectors.toList());

        cartList.forEach( cart -> {

            try {
                cartreposit.save(cart);
            }
            catch (Exception e){
                throw new Exception500("장바구니에서 흘려 내렸습니다: "+e.getMessage());
            }
        });
    }

}
