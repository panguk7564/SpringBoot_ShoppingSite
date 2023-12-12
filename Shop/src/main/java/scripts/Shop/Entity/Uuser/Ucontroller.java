package scripts.Shop.Entity.Uuser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scripts.Shop.Entity.Img.ImgService;
import scripts.Shop.core.error.exception.Exception400;
import scripts.Shop.core.error.exception.Exception401;
import scripts.Shop.core.security.CustomUserDetails;
import scripts.Shop.core.security.JwtTokenProvider;
import scripts.Shop.core.utils.ApiUtils;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class Ucontroller {
    private final Ureposit ureposit; //-- 삭제 요망 (서비스)
    private final Uservice service;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/join")
    public ResponseEntity join(@ModelAttribute @Valid URequest dto, @RequestParam("file") MultipartFile file) throws IOException {
        valideuser(dto.getEmail());

        String enPass = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(enPass);

        service.save(dto,file);

        return ResponseEntity.ok().body(ApiUtils.success("회원가입 성공:"+ dto.getName()));
    }

    private void valideuser(String email){
        Optional<Uuser> uuser1 = ureposit.findByEmail(email);
        if(uuser1.isPresent()){
            throw new RuntimeException("이미 있는 계정입니다.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid URequest.JoinDTO requestDTO, HttpSession session) {

        String jwt = "";

        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword());

            Authentication authentication = authenticationManager.authenticate(
                    usernamePasswordAuthenticationToken
            );


            // ** 인증 완료 값을 받아온다.
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();


            // ** 토큰 발급.
            jwt = JwtTokenProvider.create(customUserDetails.getUser());


            requestDTO.setToken(jwt);
            service.tokensave(customUserDetails.getUser().getId(), requestDTO);
            session.setAttribute("loginBy", customUserDetails.getUsernames());
            session.setAttribute("loginToken", customUserDetails.getUserToken());

        } catch (Exception e) {

            throw new Exception401("인증되지 않음.");
        }
        return ResponseEntity.ok().header(JwtTokenProvider.HEADER, jwt).body(ApiUtils.success("로그인 완료"));
    }

    @PostMapping("/mem/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody URequest.JoinDTO dto) {
        Uuser user = service.update(id, dto);

        return ResponseEntity.ok().body(user.getEmail());
    }
}