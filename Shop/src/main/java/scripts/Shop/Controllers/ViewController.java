package scripts.Shop.Controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import scripts.Shop.Entity.Img.ImgFile;
import scripts.Shop.Entity.Img.ImgService;
import scripts.Shop.Entity.Option.Option;
import scripts.Shop.Entity.Option.Oservice;
import scripts.Shop.Entity.Product.Product;
import scripts.Shop.Entity.Product.Pservice;
import scripts.Shop.Entity.Uuser.Uservice;
import scripts.Shop.Entity.Uuser.Uuser;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final Uservice service;
    private final Pservice pservice;
    private final ImgService iservice;
    private final Oservice oservice;

    @GetMapping("/")
    public String index(){
        return "main";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/signup")
    public String signup(){return "signup";}

    @GetMapping("/img")
    public String img(){return "img";}

    @GetMapping("/mem")
    public String findmem(Model model){
        List<Uuser> memlist = service.findall();
        model.addAttribute("userlist", memlist);
        return "mem";
    }

    @GetMapping("/mem/{id}")
    public String findbyId(@PathVariable Long id, Model model){
        Uuser userDto = service.findByid(id);
        ImgFile imgFile = iservice.findByUserid(id);

        model.addAttribute("users",userDto);
        model.addAttribute("file",imgFile);



        return "details";
    }

    @GetMapping("/mem/edit/{id}")
    public String editor(@PathVariable Long id, Model model){
        Uuser user = service.findByid(id);
        model.addAttribute("user",user);
        return "userupdate";
    }

    @GetMapping("/mem/delete/{id}")
    public String delete(@PathVariable Long id){
        service.deleteById(id);
        return "redirect:/signout";
    }

    @GetMapping("/signout")
    public String logout(HttpSession session){
        Uuser uuser = (Uuser) session.getAttribute("loginBy");
        service.signout(uuser);
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/items")
    public String shop(Model model){
        List<Product> productList = pservice.findall();
        model.addAttribute("itemlist",productList);
        return "items";
    }

    @GetMapping("/item/{id}")
    public String itemdetail(@PathVariable Long id, Model model){
        Product product = pservice.findByid(id);
        Long stock = oservice.calculateSum(product);
        List<ImgFile> imges = iservice.findAllByProdutId(id);
        List<Option> optionList = oservice.findByProduct(product);

        model.addAttribute("items",product);
        model.addAttribute("files",imges);
        model.addAttribute("stock",stock);
        model.addAttribute("models",optionList);
        System.out.println(id);
        return "itemdetails";
    }

    @GetMapping("/mem/registitem/{id}") // -- 사용자가 등록한 상품목록
    public String myRegistItem(@PathVariable Long id, Model model){
        List<Product> productList = pservice.findUserRegitItem(id);
        model.addAttribute("myRegitItem",productList);
        return "useritems";
    }

    @GetMapping("/mem/registitem/detail/{id}") // -- 사용자 상품목록 상세
    public String useritemfound(@PathVariable Long id, Model model){
        Product product = pservice.findByid(id);
        List<ImgFile> imges = iservice.findAllByProdutId(id);
        Long stock = oservice.calculateSum(product);
        List<Option> optionList = oservice.findByProduct(product);

        model.addAttribute("items",product);
        model.addAttribute("files",imges);
        model.addAttribute("stock",stock);
        model.addAttribute("models",optionList);

        return "useritemdetails";
    }

    @GetMapping("/mem/registitem/update/{id}")
    public String useritemedit(@PathVariable Long id, Model model){
        Product product = pservice.findByid(id);
        model.addAttribute("item",product);

        Long stock = oservice.productStock(product);
        model.addAttribute("stock",stock);

        return "useritemedit";
    }

    @GetMapping("/mem/registitem/options/{id}") // -- 옵션 전체출력 페이지
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model, @PathVariable Long id){
        Page<Option> options = oservice.paging(pageable, id);
        System.out.println(options.getTotalElements());

        int blockLimit = 3;
        int startPage = (int)Math.ceil((double)pageable.getPageNumber()/blockLimit - 1) * blockLimit + 1;
        int endPage = ((startPage+blockLimit - 1) < options.getTotalPages()) ? (startPage + blockLimit - 1) : options.getTotalPages();

        model.addAttribute("optionList",options);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("pid",id);
        return "useritemoptionList";
    }

    @GetMapping("/mem/itemadd")
    public String additem(){
    return "itemadd";
    }

    @GetMapping("/mem/registitem/options/create/{id}")
    public String option_save(@PathVariable Long id, Model model) {
        model.addAttribute("pid",id);
        return "useritemoptionAdd";
    }

    @GetMapping("/mem/registitem/option/update/{id}")
    public String option_update(@PathVariable Long id, Model model){
       model.addAttribute("oid",id);
        return "useritemoptionUpdate";
    }
}
