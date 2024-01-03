package scripts.Shop.Entity.Cart;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scripts.Shop.Entity.Option.Option;
import scripts.Shop.Entity.Uuser.Uuser;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "cart_tb", indexes = {@Index(name = "cart_user_id_idx", columnList = "user_id"),
                                    @Index(name = "cart_option_id_idx", columnList = "option_id"),
                                    },
                                    uniqueConstraints = {
                                    @UniqueConstraint(name = "uk_cart_option_user", columnNames = {"user_id","option_id"})
                                    })

public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //--pk
    private Long id;

    private Long optionId;

    @ManyToOne(fetch = FetchType.LAZY) //-- 유저 별로 카트에 몪임
    private Uuser user;

    @Column(nullable = false) // -- 상품 수량
    private Long item_Quantity;

    @Column(nullable = false) // -- 상품 가격
    private Long price;

    @Column(nullable = false)
    private String cartedName;

    @Builder
    public Cart(Long id, Long option, Uuser user, Long item_Quantity, Long price, String cartedName) {
        this.id = id;
        this.optionId = option;
        this.user = user;
        this.item_Quantity = item_Quantity;
        this.price = price;
        this.cartedName = cartedName;
    }

    public void update(Long quantity, Long price){
        this.item_Quantity = quantity;
        this.price = price;
    }
}
