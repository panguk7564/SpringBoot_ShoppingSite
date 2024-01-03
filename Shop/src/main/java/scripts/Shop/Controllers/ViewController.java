package scripts.Shop.Controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import scripts.Shop.Entity.Cart.Cart;
import scripts.Shop.Entity.Cart.Cartservice;
import scripts.Shop.Entity.Img.ImgFile;
import scripts.Shop.Entity.Img.ImgService;
import scripts.Shop.Entity.Option.Option;
import scripts.Shop.Entity.Option.Oservice;
import scripts.Shop.Entity.Order.Items.ItemService;
import scripts.Shop.Entity.Order.Oorder;
import scripts.Shop.Entity.Order.Ordservices;
import scripts.Shop.Entity.Product.Product;
import scripts.Shop.Entity.Product.Pservice;
import scripts.Shop.Entity.Uuser.Uservice;
import scripts.Shop.Entity.Uuser.Uuser;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final Uservice service;
    private final Pservice pservice;
    private final ImgService iservice;
    private final Oservice oservice;
    private final Ordservices orservice;
    private final Cartservice cservice;
    private final ItemService itemService;

    @GetMapping("/") // -- 메인화면
    public String index(){
        return "main";
    }

    @GetMapping("/login") // -- 로그인
    public String login(){
        return "login";
    }

    @GetMapping("/signup") // -- 회원가입
    public String signup(){return "signup";}

    @GetMapping("/items") // -- 상점
    public String shop(Model model){
        List<Product> productList = pservice.findall();
        model.addAttribute("itemlist",productList);
        return "items";
    }

    @GetMapping("/item/{id}") //-- 상품 상세출력
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

    //------------------------------------------ mem : 회원(로그인 한 유저) 만 엑세스 가능

    @GetMapping("/signout")// -- 로그아웃 (세션 만료 및 토큰 삭제)
    public String logout(HttpSession session){
        Long userid = (Long) session.getAttribute("loginBy");
        service.signout(userid);
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/mem/{id}") //-- 회원상세페이지 출력
    public String findbyId(@PathVariable Long id, Model model){
        Uuser userDto = service.findByid(id);
        ImgFile imgFile = iservice.findByUserid(id);

        model.addAttribute("users",userDto);
        model.addAttribute("file",imgFile);

        return "details";
    }

    @GetMapping("/mem/edit/{id}") // -- 회원 정보 수정페이지 출력
    public String editor(@PathVariable Long id, Model model){
        Uuser user = service.findByid(id);
        model.addAttribute("user",user);
        return "userupdate";
    }

    @GetMapping("/mem/registitem/{id}") // -- 사용자가 등록한 상품목록페이지 출력
    public String myRegistItem(@PathVariable Long id, Model model){
        List<Product> productList = pservice.findUserRegitItem(id);
        model.addAttribute("myRegitItem",productList);
        return "useritems";
    }

    @GetMapping("/mem/registitem/detail/{id}") // -- 사용자 상품목록 상세페이지 출력
    public String useritem_findAll(@PathVariable Long id, Model model){
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

    @GetMapping("/mem/registitem/update/{id}") //-- 사용자 등록 상품 수정페이지 출력
    public String useritem_edit(@PathVariable Long id, Model model){
        Product product = pservice.findByid(id);
        model.addAttribute("item",product);

        Long stock = oservice.productStock(product);
        model.addAttribute("stock",stock);

        return "useritemedit";
    }

    @GetMapping("/mem/registitem/options/{id}") // -- 사용자 등록 상품 옵션 전체출력 페이지 출력
    public String option_paging(@PageableDefault(page = 1) Pageable pageable, Model model, @PathVariable Long id){
        Page<Option> options = oservice.paging(pageable, id);

        int blockLimit = 3;
        int startPage = (int)Math.ceil((double)pageable.getPageNumber()/blockLimit - 1) * blockLimit + 1;
        int endPage = ((startPage+blockLimit - 1) < options.getTotalPages()) ? (startPage + blockLimit - 1) : options.getTotalPages();

        model.addAttribute("optionList",options);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("pid",id);
        return "useritemoptionList";
    }

    @GetMapping("/mem/itemadd") // -- 사용자 상품 등록페이지 출력
    public String additem(){
    return "itemadd";
    }

    @GetMapping("/mem/registitem/options/create/{id}") //-- 사용자 상품 옵션 등록페이지 출력 {상품ID}
    public String option_save(@PathVariable Long id, Model model) {
        model.addAttribute("pid",id);
        return "useritemoptionAdd";
    }

    @GetMapping("/mem/registitem/option/update/{id}") // -- 사용자 상품 옵션 수정페이지 출력
    public String option_update(@PathVariable Long id, Model model){
        Option option = oservice.findById(id);
        model.addAttribute("option",option);
        model.addAttribute("oid",id);
        return "useritemoptionUpdate";
    }

    @GetMapping("/mem/cart/{id}") //-- 장바구니 상품 전체 출력페이지
    public String cart_paging(@PageableDefault(page = 1) Pageable pageable, Model model, @PathVariable Long id){
        Page<Cart> carts = cservice.paging(pageable, id);

        int blockLimit = 3;
        int startPage = (int)Math.ceil((double)pageable.getPageNumber()/blockLimit - 1) * blockLimit + 1;
        int endPage = ((startPage+blockLimit - 1) < carts.getTotalPages()) ? (startPage + blockLimit - 1) : carts.getTotalPages();

        if(cservice.findAllById(id) != null) {
            List<Cart> cartList = cservice.findAllById(id);

            Long Tp = cartList.stream().mapToLong(cart -> cart.getPrice()).sum();
            Long Tq = cartList.stream().mapToLong(cart -> cart.getItem_Quantity()).sum();

            model.addAttribute("totalPrice",Tp);
            model.addAttribute("totalQuantity",Tq);
        }
        model.addAttribute("cartList",carts);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("userId",id);


        return "userCart";
    }

    @GetMapping("/mem/order/{id}") //-- 유저 주문 전체 출력페이지
    public String order_paging(@PageableDefault(page = 1) Pageable pageable, Model model, @PathVariable Long id){

        Page<Oorder> orders = orservice.paging(id,pageable);

        int blockLimit = 3;
        int startPage = (int)Math.ceil((double)pageable.getPageNumber()/blockLimit - 1) * blockLimit + 1;
        int endPage = ((startPage+blockLimit - 1) < orders.getTotalPages()) ? (startPage + blockLimit - 1) : orders.getTotalPages();

        model.addAttribute("orderList",orders);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("userId",id);

        return "userOrderList";
    }
}
