package scripts.Shop.Entity.Option;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scripts.Shop.Entity.Product.Preposit;
import scripts.Shop.Entity.Product.Product;
import scripts.Shop.Entity.Product.ProductResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Oservice {
    private final Oreposit reposit;
    private final Preposit preposit;



    public List<OResponse.FindProductIdDto> findByProductId(Long id) {
        List<Option> optionList = reposit.findByProductId(id);
        List<OResponse.FindProductIdDto> optionRes =
                optionList.stream().map(OResponse.FindProductIdDto :: new)
                        .collect(Collectors.toList());

        return optionRes;
    }

    public List<OResponse.FindAllDto> findAll() {
        List<Option> optionList = reposit.findAll();

        List<OResponse.FindAllDto> findAllDtos =
                optionList.stream().map(OResponse.FindAllDto :: new)
                .collect(Collectors.toList());

        return findAllDtos;
    }

    public void save(Product product, Long amount) {
        Option optionEntity = Option.builder()
                .optionName(product.getProductName())
                .o_img(product.getImg())
                .price(product.getPrice())
                .quantity(amount)
                .product(product)
                .build();

        reposit.save(optionEntity);
    }

    public String add(Long id, OResponse dto){
        Optional<Product> product = preposit.findById(id);

        if(product.isPresent()){

            dto.setProduct(product.get());
            dto.setProductId(product.get().getId());

         Option option = dto.toEn();

            reposit.save(option);
            return dto.getOptionName();
        }
        else {
            return "주 상품이 없어요";
        }
    }


}
