# Project Camper

### 프로젝트 개요
캠핑장, 캠핑용품을 공유하고 추천할 수 있는 캠핑 관련 커뮤니티 애플리케이션
(추후엔 예약 기능도 추가)

### 개발 환경
- Jenkins, Docker
- AWS EC2, RDS, CodeBuilder, CodeDeploy
- Amazon Corretto 11 JDK
- Spring Data JPA
- Querydsl(목록 조회 기능에만 사용)
- Spring Security + OAuth2.0 + JWT(인증)
- Spring REST Docs(API 문서)
- 2차 캐시를 사용(READ_WRITE)하고 낙관적 Lock(@Version)을 사용합니다.

### CI/CD
- Jenkins, Docker, CodeBuilder, CodeDeploy를 활용한 CI/CD 구축(자동화 배포)
- 빌드 시 clean build test

### API 문서
- API 문서는 Spring REST Docs를 사용합니다.
- clean build test 후 http://localhost:8080/docs/main.html 접속하시면 API 문서를 확인하실 수 있습니다.

### 테스트 환경
- BDD를 기반으로 테스트 합니다.
- Test Case는 성공, 프로세스 별, 유효성 검증 실패에 관한 테스트를 진행합니다.
- Domain, QueryDSL Repository, Service, Controller Layer 테스트를 진행합니다.


