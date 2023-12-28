package scripts.Shop.Entity.Order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scripts.Shop.Entity.Uuser.Uuser;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "order_tb", indexes = {
        @Index(name = "order_user_id_idx", columnList = "user_id")
})
public class Oorder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Uuser user;

    @Column(nullable = false)
    private String orderName;

    private boolean is_payed;

    @Builder
    public Oorder(Long id, Uuser user, String orderName, boolean is_payed) {
        this.id = id;
        this.user = user;
        this.orderName = orderName;
        this.is_payed = is_payed;
    }

    public void payed(Boolean is_payed) {
        this.is_payed = is_payed;
    }//-- 결제여부 업데이트
}
