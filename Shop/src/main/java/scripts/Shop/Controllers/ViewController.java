package scripts.Shop.Controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scripts.Shop.Entity.Img.ImgFile;
import scripts.Shop.Entity.Img.ImgService;
import scripts.Shop.Entity.Option.Option;
import scripts.Shop.Entity.Option.Oservice;
import scripts.Shop.Entity.Product.Product;
import scripts.Shop.Entity.Product.ProductResponse;
import scripts.Shop.Entity.Product.Pservice;
import scripts.Shop.Entity.Uuser.URequest;
import scripts.Shop.Entity.Uuser.Uservice;
import scripts.Shop.Entity.Uuser.Uuser;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/memedit/{id}")
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

    @GetMapping("/test")
    public String test(Model model){
        List<Product> productList = pservice.findall();

        model.addAttribute("models", productList);
        return "test";
    }


    @GetMapping("/myregistitem/{id}")
    public String myRegistItem(@PathVariable Long id, Model model){
        List<Product> productList = pservice.findUserRegitItem(id);
        model.addAttribute("myRegitItem",productList);
        return "useritems";
    }

    @GetMapping("/useritem/{id}")
    public String useritemfound(@PathVariable Long id, Model model){
        Product product = pservice.findByid(id);
        List<ImgFile> imges = iservice.findAllByProdutId(id);
        Long stock = oservice.calculateSum(product);

        model.addAttribute("items",product);
        model.addAttribute("files",imges);
        model.addAttribute("stock",stock);

        return "useritemdetails";
    }

    @GetMapping("/useritem/update/{id}")
    public String useritemedit(@PathVariable Long id, Model model){
        Product product = pservice.findByid(id);
        model.addAttribute("item",product);

        Long stock = oservice.productStock(product);
        model.addAttribute("stock",stock);

        return "useritemedit";
    }

    @GetMapping("/itemadd")
    public String additem(){
    return "itemadd";
    }


}
