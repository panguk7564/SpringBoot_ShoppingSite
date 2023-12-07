package scripts.Shop.Entity.Option;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scripts.Shop.Entity.Product.Product;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "option_tb",
        indexes = {
        @Index(name = "option_product_id_index", columnList = "product_id")
        }) //-- 불러올때 product_id 를 바로 불러옴
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY) //-- 연관관계
    private Product product;

    @Column(length = 100, nullable = false)
    private String optionName; //-- 옵션이름

    private String o_img; //-- 옵션 사진

    private Long price; //-- 옵션가격

    private Long quantity; //-- 옵션수량

    @Builder
    public Option(Long id, Product product, String optionName, String o_img, Long price, Long quantity) {
        Id = id;
        this.product = product;
        this.optionName = optionName;
        this.o_img = o_img;
        this.price = price;
        this.quantity = quantity;
    }

    public void update(String optionName, String o_img, Long price, Long stock) {
        this.optionName = optionName;
        this.o_img = o_img;
        this.price = price;
        this.quantity = stock;
    }
}
