package scripts.Shop.Entity.Order.Items;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scripts.Shop.Entity.Option.Option;
import scripts.Shop.Entity.Option.Oservice;
import scripts.Shop.Entity.Order.Oorder;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemsReposit reposit;
    private final Oservice oservice;

    public void payedItemStockAdjust(Oorder oorder){ // -- 주문 수량에 맞게 해당 상품의 재고 -
        List<Item> itemList = reposit.findAllByOrderId(oorder.getId());

        for (Item item : itemList) {
            Option option = item.getOption();
            oservice.quantity_update(option.getId(), item.getQuantity());
        }
    }
}
