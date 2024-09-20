## 유신사

- 사용자 등급별 할인율, 브랜드별 검색이 지원되는 상품 목록 조회 서비스입니다. 
- 상품 목록 조회 시 사용자의 로그인 여부와 등급에 따라 가격 정보가 다르게 노출되며, 요일에 따른 추가 할인이 적용됩니다.
   - 브랜드 이름으로 검색할 수 있고, 페이지네이션이 지원됩니다.

> 기간 : 2024.09.21 (4시간)

## 목차

1. [프로젝트 환경](#프로젝트-환경)
2. [Quick Start & Stop](#quick-start--stop)
3. [주요 기능](#주요-기능)
4. [ERD](#erd)
5. [API 명세서](#api-명세서)
6. [디렉토리 구조](#디렉토리-구조)
7. [프로젝트 개선 사항 및 예정 작업](#프로젝트-개선-사항-및-예정-작업)

## 프로젝트 환경

| Stack                                                                                                        | Version           |
|:------------------------------------------------------------------------------------------------------------:|:-----------------:|
| ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) | Spring Boot 3.3.4 |
| ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)    | Gradle 8.8       |
| ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)    | JDK 17           |
| ![MariaDB](https://img.shields.io/badge/mariadb-%2300A3E0.svg?style=for-the-badge&logo=mariadb&logoColor=white) | MariaDB 11.5.2 |
| ![Docker](https://img.shields.io/badge/docker-%23296AAB.svg?style=for-the-badge&logo=docker&logoColor=white)    | Docker 27.2.0   |
| ![Docker Compose](https://img.shields.io/badge/docker%20compose-%2318A9D0.svg?style=for-the-badge&logo=docker&logoColor=white) | Docker Compose 2.29.2 |

## Quick Start & Stop

<details>
<summary><strong>Quick Start</strong></summary>
<div markdown="1">
    
### 1. 사전 준비 사항

- Docker 및 Docker Compose가 설치되어 있어야 합니다.
- Docker가 실행중이어야 합니다.

### 2. 데이터베이스 실행

애플리케이션을 시작하기 전에 데이터베이스를 Docker Compose를 사용하여 설정해야 합니다. <br/>
다음 명령어를 사용하여 각 서버의 데이터베이스를 실행합니다.

```shell
docker-compose -f ./docker-compose.auth.yml up -d
```

위 명령어는 백그라운드에서 데이터베이스 컨테이너를 실행합니다. <br/>
실행 중인 상태를 확인하려면 `docker ps` 명령어를 사용하세요.

### 3. 서버 실행 (포트 8080)

서버별로 터미널을 열어 다음 명령어를 실행합니다. 

```shell
./gradlew :bootJar
```

```shell
java -jar ./build/libs/yousinsa-0.0.1-SNAPSHOT.jar
```
</details>


<details>
<summary><strong>Quick Stop</strong></summary>
<div markdown="1">

### 1. 서버 종료

서버를 종료하려면 터미널에서 다음 명령어를 실행합니다.

```shell
sudo lsof -i :8080
sudo kill -9 [PID] # [PID]는 실제 프로세스 ID로 대체
```


### 2. 데이터베이스 종료

데이터베이스를 종료하려면 다음 명령어를 사용합니다.

```shell
docker-compose -f ./docker-compose.auth.yml down
```

</details>

## 주요 기능

### 회원가입 API
- 사용자는 회원가입을 할 수 있습니다.
- 비밀번호는 반드시 암호화하여 저장됩니다.

### 로그인 API
- 사용자는 로그인할 수 있습니다.
- 잘못된 아이디 또는 비밀번호 입력 시 각각 다른 예외 처리가 되어야 합니다.

### 상품 목록 API (로그인 여부 및 사용자 유형에 따른 상품 정보 노출)
- 로그인하지 않은 사용자는 가격 정보가 표시되지 않습니다.
- 로그인한 사용자는 사용자의 유형에 따라 상품 금액이 표시됩니다.
- 사용자 유형에 맞는 금액 정보가 없을 경우 기본 금액이 표시되며, 모든 경우에 할인율이 적용된 최종 금액을 계산하여 표시합니다.
- 월요일에는 1% 추가 할인이 적용됩니다.
- Pagination 기능이 포함되어 있습니다.
- 응답 항목: 기본 금액, 할인된 금액, 전체 할인율, 상품 정보, 브랜드 정보.

### 브랜드별 상품 목록 조회 API
- 브랜드 이름(한글 또는 영어)으로 상품 목록을 검색할 수 있습니다.
- Pagination 기능이 포함되어 있습니다.
- 응답 항목: 기본 금액, 할인된 금액, 전체 할인율, 상품 정보, 브랜드 정보.

## ERD

![image](https://github.com/user-attachments/assets/cba300c6-72e8-4f18-b8a7-de5ccc80a345)

## API 명세서

<br/>

| No | Title      | Method   | URL                     | 
|----|------------|----------|-------------------------|
| 1  | 회원가입    | `POST`   | `/api/users/signup`             | 
| 2  | 로그인 | `POST`  | `/api/users/login`      |   
| 3  | 토큰 재발급    | `GET` | `/api/auth/token`      |    
| 4  | 상품 목록 조회 | `GET`    | `/api/products?page={page}&size={size}`             |    
| 5  | 브랜드별 상품 목록 조회    | `GET`    | `/api/products?page={page}&size={size}&brand={brand}` |    

- [Postman](https://documenter.getpostman.com/view/9878847/2sAXqta1J2)에서 예시 Request, Response을 확인할 수 있습니다.

## 디렉토리 구조 

<details>
<summary><strong>프로젝트 디렉토리 구조 설명</strong></summary>
<div markdown="1">

디렉터리 구조는 크게 domain과 global로 구분합니다.  

- **domain**: 서비스의 핵심 비즈니스 로직 코드가 도메인별로 구현되어 있습니다.  
- **global**: 핵심 비즈니스 로직에 종속적이지 않고 전역에서 사용할 수 있는 리스폰스 형식, 예외 처리 등을 관리합니다.  

### domain  

도메인은 비즈니스 로직에 맞춰 세분화되어 있으며, 각각의 도메인은 공통적으로 controller, dto, entity, repository, service 계층을 가집니다.  
도메인의 종류는 아래와 같습니다.  
- **auth**: 인증 및 인가와 관련된 로직을 담당합니다. JWT 기반의 인증 필터, 토큰 제공자, 유저 정보 관리 서비스 등을 구현합니다.  
- **brand**: 브랜드 관련 데이터 관리 로직을 담당합니다. 브랜드 정보를 관리하고, 브랜드 엔티티 및 관련 리포지토리를 통해 데이터베이스와 상호작용합니다.  
- **product**: 상품과 관련된 로직을 담당합니다. 상품 정보, 상세 정보 등을 제공하며, 컨트롤러 및 서비스 계층에서 비즈니스 로직을 처리합니다.  
- **user**: 사용자 정보와 관련된 로직을 담당합니다. 사용자 등록, 로그인, 등급 관리 등의 비즈니스 로직을 구현합니다.  

각 도메인은 아래와 같은 하위 패키지 구조로 이루어져 있습니다.  
- **controller**: 사용자 요청이 진입하는 지점이며, 요청을 적절한 서비스로 전달하여 처리하는 역할을 합니다.  
- **dto**: 데이터를 전송할 때 사용하는 객체를 관리합니다. 사용자 요청(request) 및 응답(response) 데이터를 처리합니다.  
- **entity**: 데이터베이스 테이블과 매핑되는 객체를 관리합니다.  
- **repository**: 데이터베이스와의 상호작용을 관리하며, JPA를 이용해 CRUD를 처리합니다.  
- **service**: 비즈니스 로직을 담당하며, 필요한 데이터를 가공하고 처리합니다.  

### global  

**global** 디렉토리는 전역적으로 사용되는 유틸리티 클래스, 예외 처리 클래스 등을 포함합니다.  
- **api**: API 응답 형식 및 공통 유틸리티 메서드를 관리합니다.  
- **entity**: 공통적으로 사용되는 엔티티를 정의합니다.  
- **exception**: 전역 예외 처리 로직을 포함하며, 사용자 정의 예외 클래스와 에러 코드를 관리합니다.  

</div>
</details>

<details>
<summary><strong>프로젝트 디렉토리 구조</strong></summary>
<div markdown="1">

```
.
├── HELP.md
├── build.gradle
├── docker-compose.yml
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── yhkim
    │   │           └── yousinsa
    │   │               ├── YousinsaApplication.java
    │   │               ├── domain
    │   │               │   ├── auth
    │   │               │   │   ├── JwtAuthenticationEntryPoint.java
    │   │               │   │   ├── JwtAuthenticationFilter.java
    │   │               │   │   ├── JwtTokenProvider.java
    │   │               │   │   ├── TokenType.java
    │   │               │   │   ├── UserDetailsImpl.java
    │   │               │   │   ├── config
    │   │               │   │   │   └── WebSecurityConfig.java
    │   │               │   │   ├── controller
    │   │               │   │   │   └── AuthController.java
    │   │               │   │   ├── dto
    │   │               │   │   │   ├── JwtTokenInfo.java
    │   │               │   │   │   └── ReissueTokenResponse.java
    │   │               │   │   ├── entity
    │   │               │   │   │   └── RefreshToken.java
    │   │               │   │   ├── repository
    │   │               │   │   │   └── RefreshTokenRepository.java
    │   │               │   │   └── service
    │   │               │   │       ├── AuthService.java
    │   │               │   │       ├── AuthServiceImpl.java
    │   │               │   │       └── UserDetailsServiceImpl.java
    │   │               │   ├── brand
    │   │               │   │   ├── dto
    │   │               │   │   │   └── BrandInfo.java
    │   │               │   │   ├── entity
    │   │               │   │   │   └── Brand.java
    │   │               │   │   └── repository
    │   │               │   │       └── BrandRepository.java
    │   │               │   ├── product
    │   │               │   │   ├── controller
    │   │               │   │   │   └── ProductController.java
    │   │               │   │   ├── dto
    │   │               │   │   │   ├── GetProductDetailResponse.java
    │   │               │   │   │   ├── GetProductResponse.java
    │   │               │   │   │   └── ProductInfo.java
    │   │               │   │   ├── entity
    │   │               │   │   │   └── Product.java
    │   │               │   │   ├── repository
    │   │               │   │   │   └── ProductRepository.java
    │   │               │   │   └── service
    │   │               │   │       ├── ProductService.java
    │   │               │   │       └── ProductServiceImpl.java
    │   │               │   └── user
    │   │               │       ├── controller
    │   │               │       │   └── UserController.java
    │   │               │       ├── dto
    │   │               │       │   ├── Links.java
    │   │               │       │   ├── LoginUserRequest.java
    │   │               │       │   ├── LoginUserResponse.java
    │   │               │       │   ├── SignupUserRequest.java
    │   │               │       │   └── SignupUserResponse.java
    │   │               │       ├── entity
    │   │               │       │   ├── Grade.java
    │   │               │       │   └── User.java
    │   │               │       ├── repository
    │   │               │       │   └── UserRepository.java
    │   │               │       └── service
    │   │               │           ├── UserService.java
    │   │               │           └── UserServiceImpl.java
    │   │               └── global
    │   │                   ├── api
    │   │                   │   └── ApiUtils.java
    │   │                   ├── entity
    │   │                   │   ├── BaseEntity.java
    │   │                   │   └── config
    │   │                   │       └── JpaConfig.java
    │   │                   └── exception
    │   │                       ├── CustomException.java
    │   │                       ├── ErrorCode.java
    │   │                       └── GlobalExceptionHandler.java
    │   └── resources
    │       ├── application.yml
    │       ├── data.sql
    │       ├── static
    │       └── templates
    └── test
        └── java
            └── com
                └── yhkim
                    └── yousinsa
                        └── YousinsaApplicationTests.java
```
</details>

## 프로젝트 개선 사항 및 예정 작업

### 1. Refresh Token Redis 저장
- **설명**: 현재 Refresh Token은 RDBMS에 저장되어 있습니다. 이를 **Redis**에 저장함으로써 시스템의 확장성을 높이고, **세션 관리**를 최적화할 예정입니다. Redis는 데이터의 영속성과 빠른 조회 성능을 제공하여 토큰 관리에 이상적입니다.
- **진행 상태**: 준비 중

### 2. 로그아웃 구현
- **설명**: 사용자 로그아웃 기능을 추가하여 **Refresh Token과 Access Token을 무효화**하고, 보안을 강화할 예정입니다. 특히 로그아웃 시 Redis에 저장된 Refresh Token을 삭제하는 절차를 추가할 계획입니다.
- **진행 상태**: 준비 중

### 3. 검색 키워드 Redis 저장 및 최적화
- **설명**: **사용자가 검색한 키워드를 Redis에 캐시**하여, 자주 검색되는 키워드에 대해 **조회 속도**를 향상시키고 데이터베이스의 부하를 줄일 예정입니다. 검색 기능을 최적화함으로써 **반응성**을 개선할 수 있습니다.
- **진행 상태**: 준비 중


