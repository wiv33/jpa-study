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
    


## 엔티티 매핑

- DDL 생성 기능
    * 제약조건 추가: `@Column(nullalbe = false, length = 10)`
    * 유니크 제약조건 추가: `@Table(uniqueConstraints = {@UniqueConstraint(name="NAME_AGE_UNIQUE",columnNames={"NAME", "AGE"}`
    
### 필드와 컬럼 매핑

- core package: `hibernate.hbm2ddl.auto`
- 컬럼 매핑: `@Column`
    * name : 필드와 매핑할 테이블 컬럼의 이름
    * insertable : 추가 가능 여부
    * updatable : 등록 가능 여부 
    * nullable (DDL) : false 설정 시 `not null`이 된다.
    * unique (DDL) : 한 컬럼의 간단한 유니크 제약 적용 
    * columnDefinition (DDL) : 해당 문자열이 DDL 실행할 때 적용된다.
    * length (DDL) : 길이 제약 조건 (문자만 가능)
    * precision : 소수점을 포함한 전체 자릿수
    * scale : 소수의 자릿수
      
- 날짜 타입 매핑: `@Temporal`
    * `TemporalType.DATE`: 2020-03-16
    * `TemporalType.TIME`: 23:30:30
    * `TemporalType.TIMESTAMP`: 2020-03-16 23:30:30
    
- enum 타입 매핑: `@Enumerated`
    * EnumType.ORDINAL
    * EnumType.STRING
- CLOB, BLOB 매핑: `@Lob`
    * `CLOB` : String, char[], java.sql.CLOB
    * `BLOB` : byte[], java.sql.BLOB
    
- 필드 매핑 무시: `@Transient`


### 기본 키 매핑

- `@Id`
    * `GenerationType.IDENTITY`
        + 기본 키 생성을 데이터베이스에 위임
        + auto_increment는 데이터베이스에 insert sql을 실행한 후 id 값을 알 수 있음
        + insert sql 실행 후 db에서 식별자를 조회
    * `GenerationType.SEQUENCE`
        + `@SequenceGenerator`
            - name
            - sequenceName
            - initialValue
            - allocationSize: default `50`
                * db 설정과 동기화 `필수`
            - catalog, schema
    
- 권장하는 식별자 전략
    * 기본키 제약 조건: not null, unique, `불변`
    * 미래까지 이 조건을 만족하는 자연키는 어렵다. - 대체키
    * Long + 대체키 + 키 생성 전략 사용
    

