package scripts.Shop.Entity.Uuser;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Uservice {
    private final Ureposit ureposit;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Uuser update(Long id, URequest.JoinDTO dto){
        Optional<Uuser> findId = ureposit.findById(id);

        if(findId.isPresent()){

        Uuser uuser = findId.get();
            String enPass = passwordEncoder.encode(dto.getPassword());
            dto.setPassword(enPass);

        uuser.update(dto.getEmail(),dto.getPassword(),dto.getToken());

        ureposit.save(uuser);

        return uuser;
        }
        else {
            return null;
        }
    }

}
