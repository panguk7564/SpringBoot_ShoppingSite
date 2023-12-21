package scripts.Shop.Entity.Order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scripts.Shop.Entity.Cart.Cart;
import scripts.Shop.Entity.Cart.Cartreposit;
import scripts.Shop.Entity.Option.Oreposit;
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
public class Orervices {
    private final Orreposit reposit;
    private final Cartreposit creposit;
    private final ItemsReposit itemsReposit;

    @Transactional
    public Oorder ordersave(Uuser user){
        List<Cart> cartList = creposit.findByUserId(user.getId());

        if(cartList.size() == 0){
            throw new Exception404("장바구니에 상품이 없어요");
        }

        return reposit.save(Oorder.builder().user(user).build());
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
                    .option(cart.getOption())
                    .order(order)
                    .quantity(cart.getItem_Quantity())
                    .price(cart.getOption().getPrice() * cart.getItem_Quantity())
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

    public Oorder findOrderByid(Long id) {
        Optional<Oorder> optionalOorder = reposit.findById(id);

        if(optionalOorder.isPresent()){
            return optionalOorder.get();
        }
        else {
            return null;
        }
    }

    public Orderesponse.FindbyIdDto findByid(Long id) {
        Oorder order = reposit.findById(id).orElseThrow(
        () -> new Exception404("해당 주문내역 없음: 주문ID: "+ id));

        List<Item> itemList = itemsReposit.findAllByOrderId(id);

        return new Orderesponse.FindbyIdDto(order,itemList);
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
    public void deleteById(Long id) {
        itemsReposit.deleteById(id);
    }
}