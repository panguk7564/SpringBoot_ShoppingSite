package scripts.Shop.Entity.Uuser;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scripts.Shop.core.utils.StringArrayConverter;

import javax.persistence.*;
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

    @Convert(converter = StringArrayConverter.class)
    private List<String> roles = new ArrayList<>();

    @Builder
    public Uuser(Long id, String email, String pass,String token ,List<String> roles) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.token = token;
        this.roles = roles;
    }

    public void update(String email, String pass, String token) {
        this.email = email;
        this.pass = pass;
        this.token =  token;
    }
}
