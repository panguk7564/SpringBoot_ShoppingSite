### Spring Boot - Shopping_Site Project ---(the OldMan and Ocean) [12/1 ~ 12/31]
---
스프링 부트로 만든 인터넷 쇼핑몰 입니다.  

__※ 1. 개발환경__

→ IDE : InteliJ IDEA Community

→ Spring Boot: 2.7.6

→ JDK 11

→ MySQL: 8.0.35

→ Spring Data JPA: 3.1.2

→ Lombok: 1.18.28

→ Thymleaf: 3.1.2

→ Spring Security: 2.7.14

→ Apache HttpClient: 4.5.13

→ Java-Jwt: 4.3.0

→ Font-Awesome: 5.15.4


<br>__※ 2. 주요기능__

→ 1. 회원

  - 회원가입

  - 인증인가(로그인) --> 토큰 + 세션 로그인

  - 회원정보 수정/삭제

<br>→ 2. 상품

  - 상품등록(로그인된 사용자만 가능)

  - 상품 목록 페이징

  - 상품 옵션 등록/수정/삭제

  - 상품 옵션 선택 및 장바구니에 추가

→ 3. 구매

  - 장바구니 상품 정보 결제 API에 전송

  - 결제 완료후 주문 객체 생성


→ 4. 이미지

  - 회원 정보에 이미지추가/삭제

  - 상품 정보에 이미지 첨부/삭제



__※ 3. 수정 처리__

 - 로그인 시 DB에 토큰정보 저장
 - 로그아웃시 토큰 정보 삭제
 - 상품 구매후 해당 상품의 재고수 감소처리
 - 상품 결제중 취소시 주문 객체 삭제
 - 이미지가 추가된 객체를 수정할때 이미지 첨부 부재시 기존 이미지 삭제
 - 이미지가 존재하는 객체 수정시 기존의 이미지 삭제후 새로운 이미지 저장


 __※ 4. API 명세__

  <img src="./API-doc/1.jpg" alt="image_01"><br><br>
  <img src="./API-doc/2.jpg" alt="image_01"><br><br>
  <img src="./API-doc/3.jpg" alt="image_01"><br><br>

---
  #### ※ 5.화면구성
<br><br>

  <img src="./img/1.jpg" alt="image_01"><br>

__1. [메인화면](http://localhost:8080/)__

: 맨 처음 보게되는 화면입니다.<br><br>


  <img src="./img/1-1.jpg" alt="image_02"><br>

__2. 회원가입 화면__

: 회원가입시 나타나는 화면입니다. 위의 3가지항목(이름, 메일, 비번)은 필수 항목이고, 프로필 사진 첨부는 생략해도 됩니다.<br><br>

  <img src="./img/1-2.jpg" alt="image_03"><br>

  __3. 로그인 화면__

  : 로그인이 완료되면 팝업에서 로그인이 완료됨을 알려주고 메인화면으로 이동합니다.<br><br>

  <img src="./img/1-3.jpg" alt="image_04"><br>

__4. 로그인 후 메인화면__

  : 로그인이후에는 세션에 로그인한 사용자의 정보가 저장되고 그 내용이 뷰에 표시됩니다. 로그인 이후에는 로그인 버튼 대신 로그아웃 버튼과 사용자 정보 버튼이 나타납니다.<br><br>


<img src="./img/2.jpg" alt="image_05"><br>

__5. 사용자 세부 정보__

: 로그인 이후 사용자의 정보를 출력할 수 있습니다. 이 뷰에서 사용가능한 기능은 __5-1. 회원정보 수정, 5-2. 사용자 등록상품 조회, 사용자 장바구니조회, 사용자 주문조회, 사용자 정보(회원정보)삭제__ 기능이 있습니다.<br><br>

<img src="./img/2-1.jpg" alt="image_06"><br>

__5-1. 회원정보 수정__

: 로그인된 사용자의 정보 및 첨부사진을 변경할 수 있습니다. <br><br>

<img src="./img/2-2.jpg" alt="image_07"><br>

__5-2. 사용자 등록상품 조회__

: 사용자가 등록한 상품을 페이징 합니다.

<img src="./img/2-2-1.jpg" alt="image_07"><br>

: 사용자 등록 상품의 수정/삭제 그 해당상품의 옵션의 CRUD가 가능합니다.<br><br>

<img src="./img/2-3.jpg" alt="image_08"><br>

<img src="./img/3.jpg" alt="image_09"><br>

__6. 전체 상품목록__

: 전체 상품(Product)목록을 출력합니다. 인증이 되지 않은 사용자에겐 상품등록 기능이 제공되지 않습니다.<br><br>

<img src="./img/3-2.jpg" alt="image_10"><br>
<img src="./img/3-3.jpg" alt="image_11"><br>

__7. 상품 상세__

: 해당 상품의 상세 정보(이름, 가격, 재고수, 옵션정보..)를 출력합니다. 장바구니에 담기를 누르면 사용자의 장바구니에 해당 상품이 장바구니에 추가됩니다. 이미 장바구니에 있거나 재고수가 없는 상품은 담기지 않습니다.<br><br>


<img src="./img/4.jpg" alt="image_12"><br>

__8. 장바구니__

: 사용자의 장바구니에 담긴 전체 상품의 정보를 표시합니다. __9. 구매하기__를 누르면 뷰에 표시된 상품 전체를 구매합니다.<br><br>

<img src="./img/4-1.jpg" alt="image_13"><br>

__9. 결제 페이지__

: 사용자 장바구니에 있는 상품의 정보를 넘겨 받은 결제서비스로 이동합니다. 물론 실제로 결제되지 않습니다. [결제창에 명시되어 있음]<br><br>

<img src="./img/4-2.png" alt="image_14"><br>
<img src="./img/4-4.jpg" alt="image_15"><br>

: 만약 구매하고자 하는 상품이 2개 이상이라면 구매 품목의 첫번째 값의 이름과 전체 상품의 수 -1 의 명칭으로 표기됩니다.


<img src="./img/4-3.jpg" alt="image_16"><br>

: 결제 및 인증이 완료되면 결제 성공페이지가 출력되고 메인화면으로 이동합니다.<br><br>


<img src="./img/5.jpg" alt="image_17"><br>

__10. 주문내역__

: 결제가 완료된 상품은 주문내역 페이지에 출력됩니다. 주문도중 취소되거나 인증에 실패한 상품은 표시되지 않습니다.<br><br>

<img src="./img/5-1.jpg" alt="image_18"><br>

---
