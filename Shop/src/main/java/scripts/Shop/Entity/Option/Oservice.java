package scripts.Shop.Entity.Option;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Oservice {
    private final Oreposit reposit;


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
}
