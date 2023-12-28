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

  __※ 5. 화면구성__
