package scripts.Shop.Entity.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scripts.Shop.Entity.Img.ImgFile;
import scripts.Shop.Entity.Option.Option;
import scripts.Shop.Entity.Uuser.Uuser;

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Option> options = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<ImgFile> product_img = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "uuser")
    private Uuser uuser;

    @Builder
    public Product(Long id, String productName, String description, Long price, Uuser uuser) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.uuser = uuser;
    }

    public void update(String productName, String description, Long price) {
        this.productName = productName;
        this.description = description;
        this.price = price;
    }


}