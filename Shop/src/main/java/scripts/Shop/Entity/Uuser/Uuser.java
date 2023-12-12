package scripts.Shop.Entity.Uuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scripts.Shop.Entity.Img.ImgFile;
import scripts.Shop.core.utils.StringArrayConverter;

import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Uuser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String pass;

    private String token;

    private String name;

    @Convert(converter = StringArrayConverter.class)
    private List<String> roles = new ArrayList<>();



    @Builder
    public Uuser(Long id, String email, String pass, String token, String name, List<String> roles) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.token = token;
        this.name = name;
        this.roles = roles;
    }


    public void update(String name, String email, String pass) {
        this.name = name;
        this.email = email;
        this.pass = pass;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
