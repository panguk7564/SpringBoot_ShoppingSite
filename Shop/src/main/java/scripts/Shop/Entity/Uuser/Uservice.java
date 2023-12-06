package scripts.Shop.Entity.Uuser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class Uservice {
    private final Ureposit ureposit;

    @Transactional
    public Uuser update(Long id, URequest.JoinDTO dto){
        Optional<Uuser> findId = ureposit.findById(id);

        if(findId.isPresent()){

        Uuser uuser = findId.get();
        uuser.update(dto.getEmail(),dto.getPassword());

        ureposit.save(uuser);

        return uuser;
        }
        else {
            return null;
        }
    }
}
