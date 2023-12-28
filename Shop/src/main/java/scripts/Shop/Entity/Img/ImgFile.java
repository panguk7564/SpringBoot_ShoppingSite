package scripts.Shop.Entity.Img;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scripts.Shop.Entity.Product.Product;
import scripts.Shop.Entity.Uuser.Uuser;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class ImgFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imgName;
    private Long imgSize;
    private String imgType;
    @Column(nullable = false)
    private String uuid;
    private String filePath;


    @ManyToOne
    @JoinColumn(name = "product" )
    private Product product;

    @OneToOne
    @JoinColumn(name = "uuser_id")
    private Uuser uuser;

    @Builder
    public ImgFile(Long id, String imgName, Long imgSize, String imgType, String uuid, String filePath, Product product, Uuser uuser) {
        this.id = id;
        this.imgName = imgName;
        this.imgSize = imgSize;
        this.imgType = imgType;
        this.uuid = uuid;
        this.filePath = filePath;
        this.product = product;
        this.uuser = uuser;
    }
}
