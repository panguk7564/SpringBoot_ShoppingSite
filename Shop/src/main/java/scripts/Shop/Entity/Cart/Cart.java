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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Option option;

    @ManyToOne(fetch = FetchType.LAZY) //-- 유저 별로 카트에 몪임
    private Uuser user;

    @Column(nullable = false)
    private Long maxQuantity;

    @Column(nullable = false)
    private Long price;

    @Builder
    public Cart(Long id, Option option, Uuser user, Long maxQuantity, Long price) {
        this.id = id;
        this.option = option;
        this.user = user;
        this.maxQuantity = maxQuantity;
        this.price = price;
    }

    public void update(Long quantity, Long price){
        this.maxQuantity = quantity;
        this.price = price;
    }
}
