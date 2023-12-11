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

    // -- 상품이미지
    private String img;

    // -- 가격
    private Long price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Option> options = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<ImgFile> product_img = new ArrayList<>();

    @Builder
    public Product(Long id, String productName, String description, String img, Long price, List<Option> options) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.img = img;
        this.price = price;
        this.options = options;
    }


    public void update(String productName, String description, String img, Long price) {
        this.productName = productName;
        this.description = description;
        this.img = img;
        this.price = price;
    }


}