# 간편결제 프로젝트
- 총 6개의 서비스
  - 머니, 계정, 충전,송금, 결제, 정산
- 사용하다보면 한번쯤은 궁금했던 막연한 간편 결제에 대한 궁금증 해소

## Architecture
![image](https://github.com/user-attachments/assets/14efd480-fe9d-4779-80d7-44ab2b184b7e)


## 실습 환경
![image](https://github.com/user-attachments/assets/6a0651ea-3036-46c5-99a7-d589e7a5470b)

- 거의 모든 실습을 Local Machine에서 진행 가능하도록 구성
- Docker-compose, Docker Desktop 활용
- Local 환경이지만, 클라우드 MSA 환경처럼 테스트

## Hexagonal Architecture
- 각 계층(Layered Architecture)에서 하던 일들을 "내부와 외부" 라는 개념으로 나누어 각각에 맞는 별도의 인터페이스를 정의
- Adapter & Port

- "내부" 로직은 오직 "외부"를 통해서만 접근 가능
  - 모든 외부 시스템과 직접적인 상호작용은 "어댑터" 역할
  - 각 서비스에서 비즈니스 로직에 맞게 정의된 인터페이스는 "포트"
  - 즉, 외부 서비스와의 상호 작용(어댑터)는 비즈니스 로직과의 작업을 정의한 인터페이스(포트)랑만 통신
 
- 인바운드 어댑터 -> 인바운드 포트 -> 비즈니스 로직
- 비즈니스 로직 -> 아웃바운드 포트 -> 아웃바운드 어댑터

- **어댑터(Adapter)** : 서비스의 입장에서 이 서비스가 사용하는 외부 시스템과의 직접적인 구현 및 상호작용을 처리

- **포트(Port)** : 비즈니스 로직 입장에서 어댑터와 통신하기 위한 동작을 정의한 인터페이스

## 도식화된 Hexagonal Architecture
![image](https://github.com/user-attachments/assets/e5487f43-aed1-43d4-b961-6d3a3f334409)

- **어댑터**를 통해 외부 서비스의 의존성을 분리하여 언제든 쉽게 교체하여 유연한 확정성 있는 대처를 하고,
- **포트**를 통해 내부 비즈니스 로직과 인터페이스를 분리하여 내부 로직의 구현은 인터페이스와 무관하게 개발 가능하도록!

-> 목적 : 유연성 & 확장성


##  Hexagonal Project 패키지 구조 설계
fastcampus-pay
  ㄴ membership-service
    ㄴ src.main
      ㄴ java
        ㄴ com.fastcampuspay.membership      // 각 서비스 별 최상위 패키지
          ㄴ adapter                        // adapter 는 외부 시스템과의 상호작용
            ㄴ in.web
            ㄴ out.persistence
          ㄴ application  
            ㄴ port                         // port 는 비즈니스 로직(app)과 직접 통신하는 인터페이스
              ㄴ in
              ㄴ out
            ㄴ service                      // service 는 port 인터페이스의 실제 비즈니스 로직의 구현체
          ㄴ domain                        // domian 은 일반적인 모델 정보를 저장하는 패키지
        ㄴ build.gradle
      ㄴ test


## Membership 서비스 정의, DB 설계
- fastcampus-pay 라는 비즈니스에서 제공하는 멤버십 서비스로서, 패캠 페이에 가입하는 개인/법인 고객의 정보를 **소유**하고 관련 **정보의 변경에 대한 의무**를 가진 서비스

- Membership (개인/법인)

| membershipId | string  |개별 멤버(개인/법인)의 Unique한 Id|
| name         | string  |개별 멤버(개인/법인) 이름|
| email        | string  |개별 멤버(개인/법인) 이메일 주소|
| address      | string  |개별 멤버(개인/법인) 주소|
| isValid      | boolean |개별 멤버(개인/법인)의 현재 유효한 상태 여부|
| isCorp       | boolean |개별 멤버가 법인 고객인지에 대한 여부|




