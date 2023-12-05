package scripts.Shop.Entity.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scripts.Shop.Entity.Option.Option;
import scripts.Shop.Entity.Option.Oreposit;
import scripts.Shop.core.error.exception.Exception404;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Pservice {
    private final Preposit reposit;
    private final Oreposit oreposit;

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
    public Product addProduct(ProductResponse.FindAllDto dto) {
        Product product = reposit.save(dto.toEntity());
        Option option = Option.builder().optionName(dto.getProductName())
                .price(dto.getPrice())
                .o_img(dto.getImg())
                .product(product)
                .quantity(dto.getStock())
                .build();

        oreposit.save(option);
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
}
