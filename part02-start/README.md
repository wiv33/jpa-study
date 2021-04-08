# Jpa

## 영속성 컨텍스트
jpa가 엔티티를 관리하는 상태

- `@Id` 값으로 key 설정
- 1차 캐시
- 스냅 샷
    

## 플러시

- 데이터베이스와 `동기화` 하는 작업
- `JPQL`을 사용할 경우 `flush가 실행` 된다.
- `em.setFlushMode(FlushModeType)`
    * FlushModeType.AUTO
    * FlushModeType.COMMIT


## 준영속

- 영속 상태의 엔티티가 영속성 컨텍스트에서 `분리(detached)`
- 메서드
    - `em.detach(entity)`
    - `em.clear()`
        * 영속성 컨텍스트 초기화
    - `em.close()`
        * 영속성 컨텍스트 종료
    