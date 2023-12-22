package scripts.Shop.Entity.Order.Items;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scripts.Shop.Entity.Option.Option;
import scripts.Shop.Entity.Order.Oorder;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "item_tb",indexes = {
        @Index(name = "item_option_id_idx", columnList = "option_id"),
        @Index(name = "item_order_id_idx", columnList = "order_id")
})
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private Option option;

    @ManyToOne(fetch = FetchType.LAZY)
    private Oorder order;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Long price;

    @Builder
    public Item(Long id, Option option, Oorder order, Long quantity, Long price) {
        this.id = id;
        this.option = option;
        this.order = order;
        this.quantity = quantity;
        this.price = price;
    }
}
