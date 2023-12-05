package scripts.Shop.Entity.Uuser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import scripts.Shop.core.error.exception.Exception400;
import scripts.Shop.core.error.exception.Exception401;
import scripts.Shop.core.security.CustomUserDetails;
import scripts.Shop.core.security.JwtTokenProvider;
import scripts.Shop.core.utils.ApiUtils;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class Ucontroller {
    private final Ureposit ureposit; //-- 삭제 요망 (서비스)
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/join")
    public ResponseEntity join(@RequestBody @Valid URequest.JoinDTO dto){
        Optional<Uuser> byE = ureposit.findByEmail(dto.getEmail());

        if(byE.isPresent()){
            throw new Exception400("이미 존재하는 이멜입니다: "+ dto.getEmail());
        }
        String enPass = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(enPass);

        ureposit.save(dto.toEntity());

        return ResponseEntity.ok(ApiUtils.success("회원가입 성공"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid URequest.JoinDTO requestDTO){
        Optional<Uuser> byE = ureposit.findByEmail(requestDTO.getEmail());

        String jwt = "";

        try{
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword());

            Authentication authentication =  authenticationManager.authenticate(
                    usernamePasswordAuthenticationToken
            );

            // ** 인증 완료 값을 받아온다.
            CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();

            // ** 토큰 발급.
            jwt = JwtTokenProvider.create(customUserDetails.getUser());

        }catch (Exception e){
            // 401 반환.
            throw new Exception401("인증되지 않음.");
        }
        return ResponseEntity.ok().header(JwtTokenProvider.HEADER,jwt).body(ApiUtils.success("로그인 완료"));
        }
    }
