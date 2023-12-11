package scripts.Shop.Entity.Img;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import scripts.Shop.Entity.Product.Product;
import scripts.Shop.Entity.Uuser.Uuser;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class imgDto {


    private Long id;

    private String imgName;
    private Long imgSize;
    private String imgType;
    private String uuid;
    private Uuser uuser;
    private Product product;

    public ImgFile toen(){
        return ImgFile.builder()
                .id(id)
                .imgName(imgName)
                .imgSize(imgSize)
                .imgType(imgType)
                .uuid(uuid)
                .uuser(uuser)
                .product(product)
                .build();
    }

}
