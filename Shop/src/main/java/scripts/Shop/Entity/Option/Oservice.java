package scripts.Shop.Entity.Option;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scripts.Shop.Entity.Product.Preposit;
import scripts.Shop.Entity.Product.Product;
import scripts.Shop.Entity.Product.ProductResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Oservice {
    private final Oreposit reposit;
    private final Preposit preposit;

    @Transactional
    public void save(OResponse dto, Product product) {
        dto.setProduct(product);
        reposit.save(dto.toEn());
    }

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



    @Transactional
    public String delete(Long id) {
        Optional<Option> optionOptional = reposit.findById(id);
        if(optionOptional.isPresent()) {

            String deleted = optionOptional.get().getOptionName();
            reposit.deleteById(id);

            return deleted;
        }
        else {
            return null;
        }
    }

    @Transactional
    public String update(Long id, OResponse dto){
        Optional<Option> option = reposit.findById(id);

        if (option.isPresent()){
            Option option1 = option.get();

            option1.update(dto.getOptionName(),dto.getPrice(),dto.getQuantity());
            reposit.save(option1);

            return option1.getOptionName();
        }
        else {
            return null;
        }
    }

    public Long calculateSum(Product product) {
        Long sum = 0L;
        List<Option> optionList = reposit.findAllByProduct(product);

        for (Option option : optionList) {
            Long value = option.getQuantity(); // Option 객체에서 특정 값을 받아옵니다.
            sum += value; // 값을 누적하여 합산합니다.
        }
        return sum;
    }

    public Long productStock(Product product){
        List<Option> optionList = reposit.findAllByProduct(product);

        return  optionList.get(0).getQuantity();
    }

    public List<Option> findByProduct(Product product) {
        return reposit.findAllByProduct(product);
    }


    public Page<Option> paging(Pageable pageable, Long id) {
        int page = pageable.getPageNumber() - 1; // - 시작 인덱스
        int size = 3; // -- 페이지 표시 게시물 개수

        Page<Option> options = reposit.findAllByProductId(id, PageRequest.of(page, size)); ///-- 전체게시물 불러오기(정렬및 조건에 맞게[page, size]출력)

        return options.map(option -> new Option( // -- 람다 人
                option.getId(),
                option.getProduct(),
                option.getOptionName(),
                option.getPrice(),
                option.getQuantity()
                ));
    }
}
