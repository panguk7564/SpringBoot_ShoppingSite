package scripts.Shop.Entity.Order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scripts.Shop.Entity.Cart.Cart;
import scripts.Shop.Entity.Cart.Cartreposit;
import scripts.Shop.Entity.Option.Option;
import scripts.Shop.Entity.Option.Oreposit;
import scripts.Shop.Entity.Option.Oservice;
import scripts.Shop.Entity.Order.Items.Item;
import scripts.Shop.Entity.Order.Items.ItemsReposit;
import scripts.Shop.Entity.Uuser.Uuser;
import scripts.Shop.core.error.exception.Exception404;
import scripts.Shop.core.error.exception.Exception500;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class Ordservices {
    private final Orreposit reposit;
    private final Cartreposit creposit;
    private final ItemsReposit itemsReposit;
    private final Oservice oservice;

    @Transactional
    public Oorder ordersave(Uuser user,String name){
        List<Cart> cartList = creposit.findByUserId(user.getId());

        if(cartList.size() == 0){
            throw new Exception404("장바구니에 상품이 없어요");
        }

        return reposit.save(Oorder.builder().user(user).orderName(name).build());
    }


    @Transactional
    public Orderesponse.FindbyIdDto save(Uuser user, Oorder oorder) {

        List<Cart> cartList = creposit.findByUserId(user.getId());
        Optional<Oorder> Optionalorder = reposit.findById(oorder.getId());


        if(Optionalorder.isPresent()){
            Oorder order = Optionalorder.get();


        List<Item> itmelist = new ArrayList<>();

        for (Cart cart : cartList) {
            Item item = Item.builder()
                    .option(oservice.findById(cart.getOptionId()))
                    .order(order)
                    .quantity(cart.getItem_Quantity())
                    .price(oservice.findById(cart.getOptionId()).getPrice())
                    .build();

            if (item.getOption().getQuantity() == 0 || item.getOption().getQuantity() <= cart.getItem_Quantity()) {
                reposit.deleteByUser(user);
                throw new Exception404("주문하려는 상품의 재고가 부족합니다.");
            }

            itmelist.add(item);
        }
        try {
            itemsReposit.saveAll(itmelist);
        } catch (Exception e) {
            throw new Exception500("주문이 터졌습니다.(결제실패)");
        }

        return new Orderesponse.FindbyIdDto(order, itmelist);}
        else {
            throw new Exception500("주문이 터졌습니다.(결제실패)");
        }
    }

    public Page paging(Long id,Pageable pageable) {
        int page = pageable.getPageNumber() - 1; // - 시작 인덱스
        int size = 5; // -- 페이지 표시 게시물 개수

        Page<Oorder> orders = reposit.findAllByUserId(id,PageRequest.of(page, size)); ///-- 전체게시물 불러오기(정렬및 조건에 맞게[page, size]출력)

        return orders.map(order -> new Oorder( // -- 람다 人
                order.getId(),
                order.getUser(),
                order.getOrderName(),
                order.is_payed()
        ));
    }

    public Oorder findOrderByid(Long id) {
        Optional<Oorder> optionalOorder = reposit.findById(id);

        if(optionalOorder.isPresent()){
            return optionalOorder.get();
        }
        else {
            System.out.println("없노");
            return null;
        }
    }

    public Orderesponse.FindbyIdDto findByid(Long id) { //-- 모든 장바구니 객체를 주문으로 불러옴
        Oorder order = reposit.findById(id).orElseThrow(
        () -> new Exception404("해당 주문내역 없음: 주문ID: "+ id));

        List<Item> itemList = itemsReposit.findAllByOrderId(id);

        return new Orderesponse.FindbyIdDto(order,itemList);
    }

    @Transactional
    public void payed(Oorder order){
        order.payed(true);
        reposit.save(order);
    }

    @Transactional
    public void delete() {
        try{System.out.println("모두 지워버리겠다.");
            itemsReposit.deleteAll();}
        catch (Exception e){
            throw new Exception500("삭제오류: "+e.getMessage());
        }
    }

    @Transactional
    public void cart_deleteById(Long id) {
        itemsReposit.deleteByOrderId(id); //-- OrderId 에 포함된 Item 삭제
    }

    @Transactional
    public void deleteOrder(Long id){
        cart_deleteById(id);
        reposit.deleteById(id);
    } //-- ORder 삭제
}