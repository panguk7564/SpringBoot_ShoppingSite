package scripts.Shop.Entity.Uuser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

import javax.servlet.http.HttpServletRequest;
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
    private final ImgService iservice;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/join") // -- 회원가입
    public ResponseEntity join(@ModelAttribute @Valid URequest dto, @RequestParam("file") MultipartFile file) throws IOException {
        valideuser(dto.getEmail());

        String enPass = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(enPass);
        service.save(dto,file);

        return ResponseEntity.ok().body(ApiUtils.success("회원가입 성공:"+ dto.getName()));
    }

    private void valideuser(String email){

        if(ureposit.findByEmail(email).isPresent()) {
            Optional<Uuser> uuser1 = ureposit.findByEmail(email);
            if (uuser1.isPresent()) {
                throw new RuntimeException("이미 있는 계정입니다.");
            }
        }
    }

    @PostMapping("/login") //-- 로그인
    public ResponseEntity<?> login(@RequestBody @Valid URequest.JoinDTO requestDTO, HttpSession session, HttpServletRequest request) {

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
            service.tokenThrower(customUserDetails.getUser().getToken(),request.getRequestURL().toString());

            Uuser logined_user =  service.findByEmail(customUserDetails.getUser().getEmail());
            System.out.println(logined_user.getName());

            session.setAttribute("loginBy", logined_user.getId());
            session.setAttribute("loginName", logined_user.getName());
            session.setAttribute("loginToken", jwt);


        } catch (Exception e) {

            throw new Exception401("인증되지 않음.");
        }
        return ResponseEntity.ok().header(JwtTokenProvider.HEADER, jwt).body(ApiUtils.success("로그인 완료"));
    }
    @PostMapping("mem/edit/{id}") //-- 회원정보 수정
    public ResponseEntity<?> user_update(@PathVariable Long id, @ModelAttribute URequest.JoinDTO dto, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        service.update(id, dto);

        if (file != null && !file.isEmpty()) {
            System.out.println("파일이요기잉네");
            iservice.user_imgupdate(id, file);
        }
        else {
            System.out.println("파일이 없습니다.");
            iservice.deleteUserimg(id);
        }

        return ResponseEntity.ok().body(dto.getEmail());
    }

    @GetMapping("/mem/delete/{id}") // -- 회원삭제시 로그인 세션만료(자동 로그아웃)
    public ResponseEntity<?> delete(@PathVariable Long id, HttpSession session){
        service.deleteById(id);
        String name = session.getAttribute("loginName").toString();
        session.invalidate();
        return ResponseEntity.ok().body("회원삭제: "+ id + "" + name);
    }
}