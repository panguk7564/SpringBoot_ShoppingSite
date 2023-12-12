package scripts.Shop.Entity.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import scripts.Shop.Entity.Img.ImgFile;
import scripts.Shop.Entity.Img.ImgReposit;
import scripts.Shop.Entity.Option.Option;
import scripts.Shop.Entity.Option.Oreposit;
import scripts.Shop.Entity.Uuser.URequest;
import scripts.Shop.Entity.Uuser.Uuser;
import scripts.Shop.core.error.exception.Exception404;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Pservice {
    private final Preposit reposit;
    private final Oreposit oreposit;
    private final ImgReposit ireposit;
    private final String filePath = "C:/Users/G/Desktop/DB_Files/";

    public List<ProductResponse.FindAllDto> findAll(int page) {
        Pageable pageable = PageRequest.of(page, 3);
        Page<Product> productPage = reposit.findAll(pageable);

        List<ProductResponse.FindAllDto> productResponses = productPage.getContent().stream().map(ProductResponse.FindAllDto::new)
                .collect(Collectors.toList());

        return productResponses;
    }

    public ProductResponse.FindByIdDto findByid(Long Id) {
        Product product = reposit.findById(Id).orElseThrow(         //-- 상품 및 옵션 검색.
                () -> new Exception404("상품이 없어요"+"상품 ID: "+Id)
        );

        List<Option> optionList = oreposit.findByProductId(product.getId());

        // -- 검색결과 반환
        return new ProductResponse.FindByIdDto(product, optionList);
    }

    @Transactional
    public Product addProduct(ProductResponse dto, MultipartFile [] files) throws IOException {

        Product product = reposit.save(dto.toEntity());
        Option option = Option.builder().optionName(dto.getProductName())
                .price(dto.getPrice())
                .product(product)
                .quantity(dto.getStock())
                .build();

        oreposit.save(option);

        for (MultipartFile file : files) {
            Path uploadpath = Paths.get(filePath);

            //-- 경로 부재시 폴더 생성
            if (!Files.exists(uploadpath)) {
                Files.createDirectories(uploadpath);
            }

            if (!file.isEmpty()) {

                String originFileName = file.getOriginalFilename();

                // -- 확장자 추출
                assert originFileName != null;
                String formatType = originFileName.substring(originFileName.lastIndexOf("."));

                System.out.println("파일명: " + originFileName);
                System.out.println("확장자: " + formatType);

                //-- UUID 생성
                String uuid = UUID.randomUUID().toString();

                String path = filePath + uuid + originFileName;

                file.transferTo(new File(path)); // -- 경로에 파일을 저장(파일이름: uuid+원본이름)

                ImgFile imgFile = ImgFile.builder() // -- 파일 객체 생성
                        .filePath(filePath)
                        .imgName(originFileName)
                        .uuid(uuid)
                        .imgType(formatType)
                        .imgSize(file.getSize())
                        .product(product)
                        .build();

                ireposit.save(imgFile);
            }
            else { System.out.println("파일이 엄서요");}
        }
        return product;
    }



    @Transactional
    public String remove(Long id) {
        reposit.deleteById(id);
        return "삭제 완료: "+ id;
    }

    public String FindItem(Long id){
       Optional<Product> name = reposit.findById(id);

       if(name.isPresent())
       {Product product = name.get();

        return product.getProductName();
       }
       else {
           return null;
       }
    }

    @Transactional
    public String update(Long id, ProductResponse.FindAllDto dto) {

        Optional<Product> product = reposit.findById(id);
        List<Option> options = oreposit.findByProductId(id);

        if(product.isPresent()){
            Product product1 = product.get();
            Option product_option1 = options.get(0);

            product1.update(dto.getProductName(),dto.getDescription(),dto.getImg(),dto.getPrice());
            product_option1.update(dto.getProductName(),dto.getImg(),dto.getPrice(),dto.getStock());

            reposit.save(product1);
            oreposit.save(product_option1);

            return product1.getProductName();
        }
        else {
            System.out.println("실패");
            return null;

        }
    }

    public List<Product> findall() {
        List<Product> productList = reposit.findAll();
        List<Product> pdto = new ArrayList<>();
        for(Product product: productList){
            pdto.add(ProductResponse.listofUser(product));
        }
        return pdto;
    }
}
