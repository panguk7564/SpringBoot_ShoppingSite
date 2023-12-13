package scripts.Shop.Entity.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scripts.Shop.Entity.Img.ImgFile;
import scripts.Shop.Entity.Option.Option;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id//-- 상품 pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false) //-- 상품명 , 입력값 필수
    private String productName;

    @Column(length = 500)//-- 상품설명 , 입력값 필수
    private String description;
    // -- 가격
    private Long price;

    private Long userId;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Option> options = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<ImgFile> product_img = new ArrayList<>();

    @Builder
    public Product(Long id, String productName, String description, String img, Long price, Long userId, List<Option> options, List<ImgFile> product_img) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.userId = userId;
        this.options = options;
        this.product_img = product_img;
    }

    public void update(String productName, String description, Long price) {
        this.productName = productName;
        this.description = description;
        this.price = price;
    }


}