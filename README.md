# Project Camper

### 프로젝트 개요
캠핑장, 캠핑용품을 공유하고 추천할 수 있는 캠핑 관련 커뮤니티 애플리케이션
(추후엔 예약 기능도 추가할 예정)

### 개발 환경
- Amazon Corretto 11 JDK
- Spring Data JPA
- Querydsl(목록 조회 기능에만 사용)
- Spring Security + JWT(인증)
- Spring REST Docs(API 문서)

### CI/CD
- Docker, Jenkins를 활용한 CI/CD 구축(자동화 배포)

### API 문서
- API 문서는 Spring REST Docs를 사용합니다.

### 테스트 환경
- BDD를 기반으로 테스트 합니다.
- Test Case는 성공, 프로세스 별, 유효성 검증 실패에 관한 테스트를 진행합니다.
- Domain, Repository, Service, Controller Layer 테스트를 진행합니다.
- Repository 테스트에서는 save 함수만 테스트 합니다.


