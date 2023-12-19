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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Uuser user;

    @Builder
    public Oorder(Long id, Uuser user) {
        this.id = id;
        this.user = user;
    }
}
