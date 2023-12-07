package scripts.Shop.Entity.Order;

import lombok.RequiredArgsConstructor;
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

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class Orervices {
    private final Orreposit reposit;
    private final Cartreposit creposit;
    private final ItemsReposit itemsReposit;

    @Transactional
    public Orderesponse.FindbyIdDto save(Uuser user) {
        List<Cart> cartList = creposit.findAllByUserId(user.getId());

        if(cartList.size() == 0){
            throw new Exception404("장바구니에 상품이 없어요");
        }
        Oorder order = reposit.save(Oorder.builder().user(user).build());

        List<Item> itmelist = new ArrayList<>();

        for(Cart cart : cartList){
            Item item = Item.builder()
                    .option(cart.getOption())
                    .order(order)
                    .quantity(cart.getMaxQuantity())
                    .price(cart.getOption().getPrice() * cart.getMaxQuantity())
                    .build();

            itmelist.add(item);
        }
        try {
            itemsReposit.saveAll(itmelist);
        }
        catch (Exception e){
            throw new Exception500("주문이 터졌습니다.(결제실패)");
        }

        return new Orderesponse.FindbyIdDto(order,itmelist);
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
}