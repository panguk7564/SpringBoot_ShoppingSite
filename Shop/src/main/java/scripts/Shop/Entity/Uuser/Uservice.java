package scripts.Shop.Entity.Uuser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import scripts.Shop.Entity.Img.ImgFile;
import scripts.Shop.Entity.Img.ImgReposit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Uservice {
    private final Ureposit ureposit;
    private final ImgReposit freposit;

    private final PasswordEncoder passwordEncoder;
    private final HttpClient client = HttpClientBuilder.create().build();
    private HttpPost post = null;

    @Transactional
    public Uuser update(Long id, URequest.JoinDTO dto){
        Optional<Uuser> findId = ureposit.findById(id);

        if(findId.isPresent()){

        Uuser uuser = findId.get();
            String enPass = passwordEncoder.encode(dto.getPassword());
            dto.setPassword(enPass);

        uuser.update(dto.getEmail(),dto.getPassword(), dto.getImg());

        ureposit.save(uuser);

        return uuser;
        }
        else {
            return null;
        }
    }

    @Transactional
    public Uuser tokensave(Long id, URequest.JoinDTO requestDTO){
        Optional<Uuser> findId = ureposit.findById(id);

        if(findId.isPresent()){

            Uuser uuser = findId.get();
            uuser.setToken(requestDTO.getToken());

            ureposit.save(uuser);
            return uuser;
        }
        else {
            return null;
        }
    }


    public List<Uuser> findall() {
        List<Uuser> memlist = ureposit.findAll();
        List<Uuser> memedto = new ArrayList<>();
        for(Uuser uuser1: memlist){
            memedto.add(URequest.listofUser(uuser1));
        }
        return memedto;
    }

    public Uuser findByid(Long id) {
        Optional<Uuser> byid = ureposit.findById(id);
        if(byid.isPresent()){
            return URequest.listofUser(byid.get());
        }
        else {return null;}
    }

    @Transactional
    public void deleteById(Long id) {
        ureposit.deleteById(id);
        System.out.println("잘가시게" + id);
    }

    public String tokencall(Long id){
        Optional<Uuser> user = ureposit.findById(id);
        String token = user.get().getToken();
        return token;
    }

    public JsonNode tokenThrower(String token, String url) {

        try{
            System.out.println(token);
            post = new HttpPost("http://localhost:8080"+url);
            post.addHeader("Authorization", token);

            final HttpResponse response = client.execute(post);

            return jsonResponse(response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public JsonNode jsonResponse(HttpResponse response){
        try{
            JsonNode returnNode = null;
            ObjectMapper mapper = new ObjectMapper();
            returnNode = mapper.readTree(response.getEntity().getContent());

            return  returnNode;
        } catch (Exception e){
            e.printStackTrace();
        } return null;
    }

    @Transactional
    public void save(URequest.JoinDTO dto){

        Uuser uuser = dto.toEntity();
        ureposit.save(uuser);
/*
            String originFileName = file.getName();

            // -- 확장자 추출
            assert originFileName != null;
            String formatType = originFileName.substring(originFileName.lastIndexOf("."));

            System.out.println("파일명: " + originFileName);
            System.out.println("확장자: " + formatType);

            //-- UUID 생성
            String uuid = UUID.randomUUID().toString();



            ImgFile img = ImgFile.builder() // -- 파일 객체 생성
                    .imgName(originFileName)
                    .imgSize(file.getSize())
                    .imgType(formatType)
                    .uuid(uuid)
                    .uuser(uuser)
                    .build();



            freposit.save(img);
*/
    }

    @Transactional
    public void save_nofile(URequest.JoinDTO dto) {
        ureposit.save(dto.toEntity());
    }
}
