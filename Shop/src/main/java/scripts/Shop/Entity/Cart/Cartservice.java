package scripts.Shop.Entity.Cart;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scripts.Shop.Entity.Option.Option;
import scripts.Shop.Entity.Option.Oreposit;
import scripts.Shop.Entity.Uuser.Ureposit;
import scripts.Shop.Entity.Uuser.Uuser;
import scripts.Shop.core.error.exception.Exception400;
import scripts.Shop.core.error.exception.Exception404;
import scripts.Shop.core.error.exception.Exception500;

import java.util.*;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class Cartservice {

    private final Cartreposit cartreposit;
    private final Oreposit oreposit;

    public List<Cart> findAllById(Long id) {
        List<Cart> cartList = cartreposit.findByUserId(id);

        if(!cartList.isEmpty()){
            return cartList;
        }
        else {
            return null;
        }
    }


    @Transactional
    public void addCartList(List<Cartrequest.saveDto> saveDtos, Uuser user) { // -- 장바구니 담기

        List<Cart> cartList = saveDtos.stream().map(cartdto -> //-- 상품 존재유무 확인
        {
            Option option = oreposit.findById(cartdto.getOptionId()).orElseThrow(
                    () -> new Exception404("해당 상품 옵션 못찾음: "+ cartdto.getOptionId()));

            if(option.getQuantity() == 0){
                throw new Exception400("해당 상품재고 없음");
            }
            return cartdto.toEn(option, user);

        }).collect(Collectors.toList());

        List<Cart> usercart = cartreposit.findAllByUser(user);


        cartList.forEach( cart -> {
            try {
                boolean hasSameValue = usercart.stream().anyMatch(otherCart -> {
                    //
                    return cart.getOption().equals(otherCart.getOption());
                });

                if (hasSameValue) {
                    System.out.println("이미있는 상품");
                    throw new Exception400("이미 장바구니에 있습니다");

                } else {
                    cartreposit.save(cart);
                }
            }
            catch (Exception e){
                throw new Exception500("장바구니에서 흘려 내렸습니다: "+e.getMessage());
            }
        });
    }

    public Page<Cart> paging(Pageable pageable, Long id) {
        int page = pageable.getPageNumber() - 1; // - 시작 인덱스
        int size = 5; // -- 페이지 표시 게시물 개수

        Page<Cart> carts = cartreposit.findAllByUserId(id, PageRequest.of(page, size)); ///-- 전체게시물 불러오기(정렬및 조건에 맞게[page, size]출력)

        return carts.map(option -> new Cart( // -- 람다 人
                option.getId(),
                option.getOption(),
                option.getUser(),
                option.getItem_Quantity(),
                option.getPrice(),
                option.getCartedName()
        ));
    }

    public List<Cart> findById(Uuser user) {
        if(!cartreposit.findAllByUser(user).isEmpty()){

            List<Cart> cartList = cartreposit.findAllByUser(user);
            return cartList;
        }
        else {return null;}
    }

    @Transactional
    public Cartresponse.updateDto update(List<Cartrequest.updateDto> requestDto, Uuser user) { // -- 상품 업데이트

        List<Cart> cartList = cartreposit.findByUserId(user.getId());

        List<Long> cartId = cartList.stream().map(cart -> cart.getId()).collect(Collectors.toList());
        List<Long> requestIds = requestDto.stream().map(dto -> dto.getCartid()).collect(Collectors.toList());

        if(cartId.size() == 0){
            throw new Exception404("장바구니에 상품이 없어요");
        }
        if(requestIds.stream().distinct().count() != requestIds.size()){
            throw new Exception400("동일한 카트 ID입니다.");
        }

        for(Long requestid : requestIds){
            if(!cartId.contains(requestid)){
                throw new Exception400("카트에 없는 상품입니다: "+requestid);
            }
        }

        for(Cartrequest.updateDto updateDto : requestDto){
            for(Cart cart : cartList){
                if(cart.getId() == updateDto.getCartid()){ //-- 수량에 비례한 가격 업데이트
                    cart.update(updateDto.getQuantity(),cart.getOption().getPrice() * updateDto.getQuantity());
                }
            }
        }
        return new Cartresponse.updateDto(cartList);
    }

    @Transactional
    public void deletecart(Long cartid) { // -- 상품 아이디별 삭제
            cartreposit.deleteById(cartid);
    }

    @Transactional
    public void deleteAll(Uuser user){
        cartreposit.deleteAllByUser(user);
    }
}