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

    private String imgName;
    private Long imgSize;
    private String imgType;
    private String uuid;
    private String filePath;
    private Long userId;


    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;



    @Builder
    public ImgFile(Long id, String imgName, Long imgSize, String imgType, String uuid, String filePath, Long userId, Product product) {
        this.id = id;
        this.imgName = imgName;
        this.imgSize = imgSize;
        this.imgType = imgType;
        this.uuid = uuid;
        this.filePath = filePath;
        this.userId = userId;
        this.product = product;
    }
}
