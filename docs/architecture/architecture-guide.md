# Harness Programming Guide

> **하네스(Harness) 프로그래밍이란:**
> 코드를 작성하기 전에 **아키텍처 규칙 → TDD 사이클 → 레이어 검증**이라는 '하네스(제어 장치)'를
> 먼저 장착하고, 그 안에서만 코드가 작동하도록 강제하는 개발 방법론.
> 모든 코드는 이 하네스를 통과해야만 프로덕션에 반영된다.

---

## 프로젝트 메타정보

| 항목 | 값                           |
|------|-----------------------------|
| **프로젝트** | realtyos-api                |
| **Base Package** | `com.web.realtyos`             |
| **Java** | 21                          |
| **Spring Boot** | 4.0.6                       |
| **DB** | PostgreSQL                  |
| **ORM** | Spring Data JPA             |
| **테스트** | JUnit 5 + AssertJ + Mockito |
| **빌드** | Gradle                      |

---

## 1. 아키텍처 원칙

### 1.1 의존성 방향 (절대 위반 금지)

```
                    ┌──────────┐
                    │ domain   │  ← 핵심: 아무것도 의존하지 않음
                    │ (순수 Java)│
                    └────▲─────┘
                         │ implements
  ┌──────────────┐       │       ┌──────────────┐
  │ application  │───────┘       │infrastructure│
  │ (orchestration)              │  (JPA, API)  │
  └──────▲───────┘               └──────────────┘
         │ 호출
  ┌──────────────┐
  │  interfaces  │
  │ (Controller) │
  └──────────────┘
```

| # | 규칙 | 위반 시 조치 |
|---|------|-------------|
| 1 | domain → 어디에도 의존하지 않음 | `import`에 Spring/JPA 어노테이션 있으면 즉시 제거 |
| 2 | application → domain만 의존 | infrastructure import 발견 시 Port로 교체 |
| 3 | infrastructure → domain Port 구현 | application import 발견 시 즉시 삭제 |
| 4 | interfaces → application만 호출 | domain 직접 호출 발견 시 AppService로 변경 |

### 1.2 infrastructure vs client 구분

| 구분 | infrastructure | client |
|------|---------------|--------|
| **정의** | 내부 인프라 설정과 구현 | 외부 서비스 SDK/HTTP 호출 |
| **예시** | JPA Entity, JPA Repository, 필터, Spring Config | S3Adaptor, RedisAdaptor, KafkaProducer |
| **특징** | Spring 내부에서 동작 | 네트워크/외부 시스템에 의존 |
| **장애 시** | 앱 자체가 기동 불가 | 외부 서비스 장애로 기능 일부 불가 |

### 1.3 패키지 구조

```
src/main/java/com/web/realtyos/
├── {module}/
│   ├── domain/                    # 핵심 도메인 계층 (순수 Java)
│   │   ├── {Aggregate}.java              # record 기반 불변 도메인 모델
│   │   ├── {Aggregate}Repository.java    # 아웃바운드 포트 (interface)
│   │   ├── {ValueObject}.java            # VO (record)
│   │   ├── {Status}Enum.java             # 상태 enum
│   │   └── service/
│   │       └── {Domain}Policy.java       # 도메인 서비스
│   ├── application/               # 유스케이스 계층
│   │   └── service/
│   │       ├── {Module}ApplicationService.java
│   │       └── {Action}Command.java
│   ├── infrastructure/            # 내부 인프라 (JPA, 필터, Spring 설정)
│   │   ├── jpa/
│   │   │   ├── {Aggregate}RepositoryJpaAdaptor.java
│   │   │   ├── entity/
│   │   │   │   └── {Aggregate}JpaEntity.java
│   │   │   └── repository/
│   │   │       └── {Aggregate}JpaRepository.java
│   │   └── config/
│   ├── client/                    # 외부 서비스 어댑터 (★ infrastructure와 분리)
│   │   └── {service}/
│   │       └── {Service}Adaptor.java
│   └── interfaces/                # 인바운드 어댑터
│       ├── controller/
│       │   └── {Module}Controller.java
│       └── dto/
│           ├── {Action}Request.java
│           └── {Action}Response.java
└── common/
    ├── client/                    # 공통 외부 서비스 (여러 모듈 공유)
    │   └── s3/
    │       ├── S3Config.java
    │       ├── S3Properties.java
    │       └── S3ImageStorageAdaptor.java
    ├── infrastructure/
    │   └── image/                 # S3와 무관한 이미지 유틸리티 (포트, 커맨드, 타입)
    ├── entity/
    │   └── BaseEntity.java
    ├── exception/
    │   ├── ErrorCode.java
    │   └── BusinessException.java
    ├── logging/                   # 로깅 전용 패키지 (Section 5 참고)
    └── response/
        └── ApiResponse.java

src/test/java/com/web/realtyos/
├── {module}/
│   ├── domain/                    # 순수 단위 테스트 (Mock 없음)
│   └── application/
│       └── service/               # Mock 기반 테스트
└── common/
    └── fixture/                   # 테스트 픽스처 (선택)
```

### 1.4 절대 규칙

```
1.  domain에는 순수 Java만 사용한다 (Spring/JPA 어노테이션 금지, Lombok은 허용)
2.  domain 모델은 record 기반 불변 객체로 설계한다 (상태 변경 시 새 인스턴스 반환)
3.  비즈니스 로직은 반드시 domain에 위치한다 (Application Service에 if/else 로직 금지)
4.  Application Service는 오케스트레이션만 수행한다 (조회 → 도메인 메서드 호출 → 저장)
5.  Infrastructure는 domain Port만 구현한다 (역방향 의존 금지)
6.  모든 기능에는 테스트 코드가 반드시 동반된다 (도메인: 순수 단위, 서비스: Mock 기반)
7.  DTO는 interfaces 패키지에만 위치한다 (service가 DTO를 반환하면 안됨)
8.  Service는 순수 Domain Model을 반환한다
9.  JPA Entity와 Domain Model은 반드시 분리한다
10. setter는 금지한다 (불변 객체 원칙)
11. Controller의 응답값은 반드시 ApiResponse<T>로 래핑하여 반환한다
```

---

## 2. 개발 플로우

### 2.1 전체 플로우

```
[요구사항 정의]
      │
      ▼
┌─────────────────────────────────┐
│ STEP 1. 도메인 모델 설계         │
│ (record, VO, Enum, Port)        │
└──────────────┬──────────────────┘
               ▼
┌─────────────────────────────────┐
│ STEP 2. 도메인 테스트 작성       │
│ (Red → Green → Refactor)        │
└──────────────┬──────────────────┘
               ▼
┌─────────────────────────────────┐
│ STEP 3. Application Service     │
│ (오케스트레이션 only, Command)    │
└──────────────┬──────────────────┘
               ▼
┌─────────────────────────────────┐
│ STEP 4. Application 테스트       │
│ (Mock 기반, 흐름 검증)            │
└──────────────┬──────────────────┘
               ▼
┌─────────────────────────────────┐
│ STEP 5. Infrastructure 구현      │
│ (JPA Entity, Adaptor, Mapper)   │
└──────────────┬──────────────────┘
               ▼
┌─────────────────────────────────┐
│ STEP 6. Interfaces 구현          │
│ (Controller, DTO)               │
└──────────────┬──────────────────┘
               ▼
┌─────────────────────────────────┐
│ STEP 7. 안티패턴 검증             │
│ (아키텍처 위반 최종 점검)          │
└─────────────────────────────────┘
```

> **핵심 원칙: 코드 작성 순서는 반드시 Domain → Test → Application → Test → Infrastructure → Interfaces 순이다.**

### 2.2 TDD 사이클 (Red → Green → Refactor)

```
┌─────────┐     ┌─────────┐     ┌──────────┐
│  RED    │────▶│  GREEN  │────▶│ REFACTOR │
│ 실패하는  │     │ 최소 코드  │     │  구조 개선  │
│ 테스트작성 │     │ 로 통과   │     │           │
└─────────┘     └─────────┘     └────┬─────┘
      ▲                               │
      └───────────────────────────────┘
```

| 단계 | 행동 | 금지 사항 |
|------|------|----------|
| **Red** | 실패하는 테스트 **하나**만 작성 | 한 번에 여러 테스트 작성 금지 |
| **Green** | 테스트를 통과하는 **최소한의 코드** 작성 | 과도한 일반화, 미래 대비 코드 금지 |
| **Refactor** | 통과 상태를 유지하며 구조 개선 | 새로운 기능 추가 금지 |

### 2.3 테스트 피라미드

```
                    ┌─────┐
                    │ E2E  │  ← 최소한 (통합 테스트, 선택)
                   ┌┴─────┴┐
                   │Service │  ← Mock 기반 단위 테스트 (필수)
                  ┌┴───────┴┐
                  │  Domain  │  ← 순수 단위 테스트 (최우선, 가장 많이)
                  └─────────┘
```

| 레벨 | 수량 | 속도 | 의존성 |
|------|------|------|--------|
| **Domain 단위 테스트** | 가장 많이 | 매우 빠름 | 없음 |
| **Service 단위 테스트** | 유스케이스당 2+ | 빠름 | Mock |
| **통합 테스트** | 필요 시 | 느림 | Spring Context |

---

## 3. 계층별 구현 가이드

### 3.1 Domain 계층

#### 체크리스트

- [ ] `record` 또는 불변 `class`로 선언되었는가?
- [ ] `@Builder` 외 프레임워크 어노테이션 없는가? (Spring, JPA 금지)
- [ ] setter가 없는가?
- [ ] 상태 변경 메서드가 **새 인스턴스를 반환**하는가?
- [ ] 비즈니스 로직(검증, 계산, 상태 전이)이 모델 내부에 있는가?
- [ ] 비즈니스 예외를 적절히 던지는가? (`BusinessException`)
- [ ] Port가 `interface`로 domain 패키지에 위치하는가?
- [ ] Port 메서드 시그니처에 Domain Model만 사용하는가? (JPA Entity, DTO 금지)
- [ ] Domain Service가 순수 Java 클래스인가? (Spring 어노테이션, Repository 주입 없음)

#### 도메인 모델

```java
// {module}/domain/{Aggregate}.java
@Builder
public record {Aggregate}(
    Long id,
    String name,
    {Status} status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static {Aggregate} create(String name) {
        return {Aggregate}.builder()
            .name(name)
            .status({Status}.ACTIVE)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public {Aggregate} deactivate() {
        if (this.status == {Status}.INACTIVE) {
            throw new BusinessException(ErrorCode.ALREADY_INACTIVE);
        }
        return {Aggregate}.builder()
            .id(this.id)
            .name(this.name)
            .status({Status}.INACTIVE)
            .createdAt(this.createdAt)
            .updatedAt(LocalDateTime.now())
            .build();
    }
}
```

#### Value Object / Enum

```java
// VO — 유효성 검증을 compact constructor에서 처리
public record Email(String value) {
    public Email {
        if (value == null || !value.contains("@")) {
            throw new BusinessException(ErrorCode.INVALID_EMAIL);
        }
    }
}

// Enum
public enum {Status} {
    ACTIVE,
    INACTIVE,
    DELETED
}
```

#### Repository Port

```java
// {module}/domain/{Aggregate}Repository.java
public interface {Aggregate}Repository {
    {Aggregate} save({Aggregate} domain);
    Optional<{Aggregate}> findById(Long id);
    List<{Aggregate}> findAll();
    void deleteById(Long id);
}
```

#### Domain Service

```java
// {module}/domain/service/{Domain}Policy.java
// 순수 Java, 무상태, DB 접근 없음
// @Configuration을 통해 Bean으로 등록 (Section 3.3 참고)
public class {Domain}Policy {
    public boolean canUpdate({Aggregate} model, String newValue) {
        return !model.name().equals(newValue);
    }
}
```

#### 도메인 테스트 패턴

```java
class {Aggregate}Test {

    @Test
    @DisplayName("활성 상태에서 비활성화하면 INACTIVE로 변경된다")
    void deactivate_active_success() {
        // given
        {Aggregate} model = {Aggregate}.builder().id(1L).status({Status}.ACTIVE).build();

        // when
        {Aggregate} result = model.deactivate();

        // then
        assertThat(result.status()).isEqualTo({Status}.INACTIVE);
        assertThat(result).isNotSameAs(model); // 불변성 확인
    }

    @Test
    @DisplayName("이미 비활성 상태에서 비활성화하면 예외가 발생한다")
    void deactivate_inactive_throwsException() {
        // given
        {Aggregate} model = {Aggregate}.builder().status({Status}.INACTIVE).build();

        // when & then
        assertThatThrownBy(() -> model.deactivate())
            .isInstanceOf(BusinessException.class);
    }
}
```

**도메인 테스트 규칙:**
- Mock 금지 (순수 객체 단위 테스트만)
- `@SpringBootTest`, `@DataJpaTest` 사용 금지
- 불변성 검증 (`assertThat(result).isNotSameAs(model)`) 필수
- 성공 + 실패 케이스 모두 작성
- 모든 도메인 테스트는 1초 이내 완료

---

### 3.2 Application 계층

#### 체크리스트

- [ ] `@Service` + `@Transactional(readOnly = true)` 기본 선언되었는가?
- [ ] 상태 변경 메서드에 `@Transactional`이 있는가?
- [ ] 비즈니스 로직(if/else 판단, 계산)이 없는가?
- [ ] 오케스트레이션만 수행하는가? (조회 → 도메인 메서드 호출 → 저장)
- [ ] Domain Port(interface)만 주입받는가? (구현체 직접 주입 금지)
- [ ] 반환 타입이 Domain Model인가? (DTO 반환 금지)
- [ ] Command 객체가 `record`로 선언되었는가?

| 허용 ✅ | 금지 ❌ |
|---|---|
| Port를 통한 조회/저장 | 비즈니스 로직 (if/else 판단) |
| Domain Model 메서드 호출 | JPA Entity 직접 조작 |
| Domain Service 호출 | infrastructure 패키지 import |
| @Transactional 관리 | DTO 반환 |

#### Command 객체

```java
public record Register{Module}Command(String name /* 유스케이스 입력 필드 */) { }

public record Update{Module}Command(Long id, String name /* 수정할 필드 */) { }
```

#### Application Service

```java
// {module}/application/service/{Module}ApplicationService.java
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class {Module}ApplicationService {

    private final {Aggregate}Repository {aggregate}Repository;
    // private final {Domain}Policy {domain}Policy; // 도메인 서비스 (필요 시)

    @Transactional
    public {Aggregate} register(Register{Module}Command command) {
        {Aggregate} model = {Aggregate}.create(command.name());
        return {aggregate}Repository.save(model);
    }

    public {Aggregate} get{Module}(Long id) {
        return {aggregate}Repository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorCode.{MODULE}_NOT_FOUND));
    }

    public List<{Aggregate}> getAll{Module}s() {
        return {aggregate}Repository.findAll();
    }

    @Transactional
    public {Aggregate} update{Module}(Update{Module}Command command) {
        {Aggregate} existing = {aggregate}Repository.findById(command.id())
            .orElseThrow(() -> new BusinessException(ErrorCode.{MODULE}_NOT_FOUND));
        return {aggregate}Repository.save(existing.update(command.name()));
    }

    @Transactional
    public void deactivate(Long id) {
        {Aggregate} existing = {aggregate}Repository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorCode.{MODULE}_NOT_FOUND));
        {aggregate}Repository.save(existing.deactivate());
    }
}
```

#### Application 테스트 패턴

```java
@ExtendWith(MockitoExtension.class)
class {Module}ApplicationServiceTest {

    @InjectMocks
    private {Module}ApplicationService applicationService;

    @Mock
    private {Aggregate}Repository {aggregate}Repository;

    @Test
    @DisplayName("정상적으로 등록할 수 있다")
    void register_success() {
        // given
        Register{Module}Command command = new Register{Module}Command("테스트");
        when({aggregate}Repository.save(any({Aggregate}.class)))
            .thenAnswer(inv -> {Aggregate}.builder()
                .id(1L)
                .name(inv.<{Aggregate}>getArgument(0).name())
                .status({Status}.ACTIVE)
                .build());

        // when
        {Aggregate} result = applicationService.register(command);

        // then
        assertThat(result.id()).isEqualTo(1L);
        verify({aggregate}Repository, times(1)).save(any({Aggregate}.class));
    }

    @Test
    @DisplayName("존재하지 않는 ID로 조회하면 예외가 발생한다")
    void get_notExists_throwsException() {
        when({aggregate}Repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> applicationService.get{Module}(999L))
            .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("활성 상태를 비활성화하면 저장된다")
    void deactivate_active_success() {
        // given
        Long id = 1L;
        {Aggregate} active = {Aggregate}.builder().id(id).status({Status}.ACTIVE).build();
        when({aggregate}Repository.findById(id)).thenReturn(Optional.of(active));
        when({aggregate}Repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // when
        applicationService.deactivate(id);

        // then
        verify({aggregate}Repository, times(1)).save(argThat(
            saved -> saved.status() == {Status}.INACTIVE
        ));
    }
}
```

**Application 테스트 규칙:**
- Port는 `@Mock`으로 주입
- Domain Model은 Mock하지 않음 (실제 객체 사용)
- `verify()`로 Port 호출 순서와 횟수 검증
- 성공 + 실패 케이스 모두 작성

---

### 3.3 Infrastructure 계층 (JPA)

#### 체크리스트

**JPA Entity:**
- [ ] `infrastructure/jpa/entity/` 패키지에 위치하는가?
- [ ] `toDomain()` + `static fromDomain()` 변환 메서드가 있는가?
- [ ] `BaseEntity`를 상속하는가?
- [ ] 비즈니스 로직이 없는가? (순수 영속성 전용)
- [ ] `@Getter` only, setter 없는가?
- [ ] `@Enumerated(EnumType.STRING)` 사용하는가?
- [ ] `@NoArgsConstructor(access = AccessLevel.PROTECTED)`가 있는가?

**Repository Adaptor:**
- [ ] `@Repository` 어노테이션이 있는가?
- [ ] Domain Port(interface)를 `implements` 하는가?
- [ ] `save()` → `fromDomain → JPA save → toDomain` 순서가 올바른가?
- [ ] application 패키지를 import하지 않는가?

#### BaseEntity

```java
// common/entity/BaseEntity.java
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
```

#### JPA Entity

```java
// {module}/infrastructure/jpa/entity/{Aggregate}JpaEntity.java
@Entity
@Table(name = "{table_name}")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class {Aggregate}JpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private {Status} status;

    public {Aggregate} toDomain() {
        return {Aggregate}.builder()
            .id(this.id)
            .name(this.name)
            .status(this.status)
            .createdAt(this.getCreatedAt())
            .updatedAt(this.getUpdatedAt())
            .build();
    }

    public static {Aggregate}JpaEntity fromDomain({Aggregate} domain) {
        return {Aggregate}JpaEntity.builder()
            .id(domain.id())
            .name(domain.name())
            .status(domain.status())
            // createdAt/updatedAt은 BaseEntity가 자동 관리 — 여기서 설정하지 않음
            .build();
    }
}
```

#### Repository Adaptor

```java
// {module}/infrastructure/jpa/{Aggregate}RepositoryJpaAdaptor.java
@Repository
@RequiredArgsConstructor
public class {Aggregate}RepositoryJpaAdaptor implements {Aggregate}Repository {

    private final {Aggregate}JpaRepository jpaRepository;

    @Override
    public {Aggregate} save({Aggregate} domain) {
        {Aggregate}JpaEntity entity = {Aggregate}JpaEntity.fromDomain(domain);
        return jpaRepository.save(entity).toDomain(); // DB 할당 ID 포함
    }

    @Override
    public Optional<{Aggregate}> findById(Long id) {
        return jpaRepository.findById(id).map({Aggregate}JpaEntity::toDomain);
    }

    @Override
    public List<{Aggregate}> findAll() {
        return jpaRepository.findAll().stream()
            .map({Aggregate}JpaEntity::toDomain)
            .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
```

#### JPA Repository

```java
// {module}/infrastructure/jpa/repository/{Aggregate}JpaRepository.java
public interface {Aggregate}JpaRepository extends JpaRepository<{Aggregate}JpaEntity, Long> {
    // 커스텀 쿼리 메서드 (필요 시)
    // Optional<{Aggregate}JpaEntity> findByName(String name);
}
```

#### Embedded Value Object 패턴

```java
// Domain VO
public record Score(int homeRuns, int awayRuns) { }

// JPA Embeddable
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ScoreEmbeddable {
    private int homeRuns;
    private int awayRuns;

    public Score toDomain() {
        return new Score(homeRuns, awayRuns);
    }

    public static ScoreEmbeddable fromDomain(Score score) {
        return new ScoreEmbeddable(score.homeRuns(), score.awayRuns());
    }
}
```

> 연관 관계는 객체 참조 대신 ID 참조를 사용한다: `Long homeTeamId`, `Long awayTeamId`

#### Domain Service Bean 등록

```java
// {module}/infrastructure/config/{Module}DomainConfig.java
@Configuration
public class {Module}DomainConfig {
    @Bean
    public {Domain}Policy {domain}Policy() {
        return new {Domain}Policy();
    }
}
```

---

### 3.4 Client 계층 (외부 서비스)

> **핵심: 외부 서비스(HTTP, SDK, 메시지 브로커 등)를 호출하는 코드는 반드시 `client/` 패키지에 위치한다.**

#### 패키지 위치

```
{module}/
├── infrastructure/    ← JPA, 필터, Spring Config (내부)
└── client/            ← 외부 서비스 어댑터 (외부)
    └── {service}/
        └── {Service}Adaptor.java

common/
├── client/
│   └── s3/            ← 공통 외부 서비스 (여러 모듈에서 공유)
│       ├── S3Config.java
│       ├── S3Properties.java
│       └── S3ImageStorageAdaptor.java
└── infrastructure/
    └── image/         ← S3와 무관한 이미지 유틸리티
```

> `LoginRateLimitFilter`는 Redis를 사용하지만 HTTP 필터 미들웨어이므로 `auth/infrastructure/ratelimit/`에 위치한다.
> client는 순수하게 "외부 서비스에 대한 포트 구현체"만 해당된다.

#### 구현 규칙

```java
// domain Port 구현 필수
@Component
@RequiredArgsConstructor
public class S3ImageStorageAdaptor implements ImageStorageService {

    private final S3Client s3Client;

    @Override
    public ImageFile upload(ImageData imageData, String storagePath, ConvertOption convertOption) {
        try {
            s3Client.putObject(putRequest, RequestBody.fromBytes(bytes));
            // ...
        } catch (Exception e) {
            log.error("S3 upload failed: key={}", key, e);
            throw new BusinessException(ErrorCode.IMAGE_UPLOAD_FAILED); // 외부 예외 변환 필수
        }
    }
}
```

**규칙 요약:**
- 반드시 domain Port(`interface`)를 구현한다
- `@Configuration`과 어댑터는 같은 `client/{service}` 패키지에 둔다
- application/domain을 import하지 않는다 (Port 인터페이스만 구현)
- 외부 서비스 예외는 반드시 `BusinessException`으로 변환한다

#### 새 외부 서비스 추가 시 체크리스트

- [ ] domain 또는 공통 패키지에 Port 인터페이스 정의
- [ ] `{module}/client/{service}/` 패키지 생성
- [ ] `{Service}Adaptor implements {Port}` 작성
- [ ] `{Service}Config (@Configuration + @EnableConfigurationProperties)` 작성
- [ ] `{Service}Properties (@ConfigurationProperties)` 작성
- [ ] 외부 예외 → `BusinessException` 변환 확인
- [ ] `infrastructure/`에 client 코드가 섞이지 않았는지 검증

---

### 3.5 Interfaces 계층

#### 체크리스트

- [ ] `@RestController` + `@RequestMapping("/api/v1/{module}s")` 선언되었는가?
- [ ] Application Service만 주입받는가?
- [ ] Request DTO → Command 변환을 수행하는가?
- [ ] Domain Model → Response DTO 변환을 수행하는가?
- [ ] `ApiResponse`로 감싸서 반환하는가?
- [ ] 비즈니스 로직이 없는가?
- [ ] Request DTO에 Validation 어노테이션(`@NotBlank` 등)이 있는가?
- [ ] Response DTO에 `from(DomainModel)` 정적 팩토리 메서드가 있는가?
- [ ] DTO가 `record`로 선언되었는가?

#### DTO

```java
// Request DTO — interfaces/dto/Register{Module}Request.java
public record Register{Module}Request(
    @NotBlank(message = "이름은 필수입니다") String name
    /* 요청 필드 */
) { }

// Response DTO — interfaces/dto/{Module}Response.java
public record {Module}Response(Long id, String name, String status, LocalDateTime createdAt) {

    public static {Module}Response from({Aggregate} domain) {
        return new {Module}Response(
            domain.id(),
            domain.name(),
            domain.status().name(),
            domain.createdAt()
        );
    }
}
```

#### Controller

```java
// {module}/interfaces/controller/{Module}Controller.java
@RestController
@RequestMapping("/api/v1/{module}s")
@RequiredArgsConstructor
public class {Module}Controller {

    private final {Module}ApplicationService applicationService;

    @PostMapping
    public ApiResponse<{Module}Response> register(@Valid @RequestBody Register{Module}Request request) {
        {Aggregate} result = applicationService.register(new Register{Module}Command(request.name()));
        return ApiResponse.of({Module}Response.from(result));
    }

    @GetMapping("/{id}")
    public ApiResponse<{Module}Response> get{Module}(@PathVariable Long id) {
        return ApiResponse.of({Module}Response.from(applicationService.get{Module}(id)));
    }

    @GetMapping
    public ApiResponse<List<{Module}Response>> getAll() {
        return ApiResponse.of(applicationService.getAll{Module}s().stream()
            .map({Module}Response::from)
            .toList());
    }

    @PutMapping("/{id}")
    public ApiResponse<{Module}Response> update(
            @PathVariable Long id,
            @Valid @RequestBody Update{Module}Request request) {
        {Aggregate} result = applicationService.update{Module}(new Update{Module}Command(id, request.name()));
        return ApiResponse.of({Module}Response.from(result));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deactivate(@PathVariable Long id) {
        applicationService.deactivate(id);
        return ApiResponse.of(null);
    }
}
```

---

## 4. 테스트 네이밍 규칙

### 메서드 명명 패턴

```
{메서드명}_{시나리오}_{기대결과}
```

| 예시 | 설명 |
|------|------|
| `create_validInput_success` | 유효한 입력으로 생성 성공 |
| `cancel_alreadyCancelled_throwsException` | 이미 취소된 상태에서 취소 시 예외 |
| `applyDiscount_vipGrade_10PercentDiscount` | VIP 등급에 10% 할인 적용 |
| `findById_notExists_throwsException` | 존재하지 않는 ID로 조회 시 예외 |

### @DisplayName 규칙

- 한글로 작성 (비개발자도 이해 가능)
- **"~하면 ~한다"** 형식
- 비즈니스 용어 사용

```java
@DisplayName("VIP 회원이 주문하면 10% 할인이 적용된다")
@DisplayName("이미 취소된 주문을 다시 취소하면 예외가 발생한다")
@DisplayName("홈팀 득점이 원정팀보다 높으면 홈팀이 승리한다")
```

### Fixture 패턴 (선택사항)

반복되는 테스트 데이터 생성을 위해 `test/common/fixture/`에 Fixture 클래스를 활용한다.

```java
// test/common/fixture/{Module}Fixture.java
public class {Module}Fixture {

    public static {Aggregate} createActive() {
        return {Aggregate}.builder()
            .id(1L)
            .name("테스트")
            .status({Status}.ACTIVE)
            .createdAt(LocalDateTime.now())
            .build();
    }

    public static {Aggregate} createInactive() {
        return {Aggregate}.builder()
            .id(2L)
            .name("비활성 테스트")
            .status({Status}.INACTIVE)
            .build();
    }
}
```

---

## 5. 로깅 명세

> 공통 로깅 시스템의 단일 소스 오브 트루스(SSOT). 변경이 필요할 경우 코드보다 이 문서를 먼저 갱신한다.

### 설계 원칙

| # | 원칙 |
|---|------|
| 1 | **TraceId 생성은 Filter 단일 책임** — 요청 진입 가장 바깥 경계에서 1회만 발급 |
| 2 | **API 요청/응답 로그는 Interceptor 단일 책임** — HandlerMapping 이후에 기록 |
| 3 | **에러 응답 본문 변환은 `GlobalExceptionHandler` 책임** — Interceptor는 `ex` 메타만 로깅 |
| 4 | **MDC 정리는 발급한 곳에서 책임진다** — Filter `finally` 블록에서 반드시 `remove` |
| 5 | **요청/응답 본문은 기본적으로 로깅하지 않는다** — 성능·개인정보 보호 |
| 6 | **민감 헤더는 마스킹한다** — `Authorization`, `Cookie`, `Set-Cookie` |

### 패키지 구조

```
src/main/java/com/web/realtyos/common/
├── logging/
│   ├── TraceIdFilter.java          # TraceId 생성 + MDC put/remove
│   ├── TraceIdGenerator.java       # UUID 16-char 생성 전략
│   ├── ApiLoggingInterceptor.java  # 요청/응답 로그 + 소요시간
│   └── LoggingConstants.java       # MDC 키, 헤더명, 마스킹 대상 상수
└── config/
    └── WebConfig.java              # Filter 등록 + Interceptor 등록

src/main/resources/
└── logback-spring.xml
```

### 책임 분담

| 컴포넌트 | 책임 | 비책임 |
|---------|------|-------|
| **TraceIdFilter** | TraceId 생성, MDC put/remove, 응답 헤더 `X-Trace-Id` 세팅 | 요청/응답 로그, 에러 응답 변환 |
| **ApiLoggingInterceptor** | 요청/응답 로그, 소요시간, 핸들러 메타, 예외 메타 로깅 | TraceId 생성, MDC 관리, 에러 응답 본문 |
| **GlobalExceptionHandler** | 예외 → 표준 응답 변환, warn 로그, **예외를 request attribute에 stash** | 트랜잭션 시간 측정, MDC 관리 |

> Spring은 `@ExceptionHandler`로 해소된 예외를 `afterCompletion()`의 `ex` 파라미터에 전달하지 않는다.
> 따라서 `GlobalExceptionHandler`의 모든 핸들러는 `request.setAttribute(LoggingConstants.HANDLED_EXCEPTION_ATTRIBUTE, e)`로 stash하고,
> `ApiLoggingInterceptor`는 `ex` 파라미터가 null이면 이 attribute를 fallback으로 읽는다.

### 실행 순서

```
HTTP Request
    │
    ▼
TraceIdFilter.doFilterInternal()           ← MDC.put("traceId", ...)
    │   response.setHeader("X-Trace-Id", ...)
    │
    ▼
[Spring Security Filter Chain]
    │
    ▼
DispatcherServlet → HandlerMapping
    │
    ▼
ApiLoggingInterceptor.preHandle()           ← log "--> METHOD URI handler=..."
    │
    ▼
Controller → Service → ...
    │
    ▼
[GlobalExceptionHandler if exception]       ← log "BusinessException: code=..."
    │   request.setAttribute(HANDLED_EXCEPTION_ATTRIBUTE, e)
    │
    ▼
ApiLoggingInterceptor.afterCompletion()     ← log "<-- METHOD URI STATUS (Nms)"
    │
    ▼
TraceIdFilter finally                       ← MDC.remove("traceId")
    │
    ▼
HTTP Response (header X-Trace-Id 포함)
```

### TraceId 사양

| 항목 | 값 |
|------|-----|
| **MDC 키** | `traceId` |
| **응답 헤더** | `X-Trace-Id` |
| **생성 방식** | `UUID.randomUUID()` → `-` 제거 → 앞 16자리 |
| **생성 위치** | `TraceIdFilter.doFilterInternal()` 진입 직후 |
| **제거 위치** | `TraceIdFilter` `finally` 블록 |
| **요청 헤더 수신** | 미지원 (항상 신규 생성, 분산 추적 도입 시 갱신) |

### Filter / Interceptor 사양

| 항목 | TraceIdFilter | ApiLoggingInterceptor |
|------|--------------|----------------------|
| **타입** | `OncePerRequestFilter` | `HandlerInterceptor` |
| **등록** | `WebConfig` → `FilterRegistrationBean` | `WebConfig` → `addInterceptors()` |
| **Order** | `Ordered.HIGHEST_PRECEDENCE` | — |
| **적용 경로** | `/api/*` | `addPathPatterns("/api/**")` |
| **제외 경로** | `/actuator`, `/error`, `/favicon.ico` | `/actuator/**`, `/error`, `/favicon.ico` |
| **로그 출력** | **없음** | 요청/응답 로그 |
| **민감 헤더** | — | DEBUG 모드에서만 `***` 마스킹 후 출력 |

### 로그 포맷

**Logback 패턴:**
```
%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] [%X{traceId:-}] %-5level %logger{36} - %msg%n
```

**메시지 포맷:**

| 시점 | 포맷 | 레벨 |
|------|------|------|
| **요청 진입** | `--> {METHOD} {URI} handler={Controller#method}` | INFO |
| **정상 응답** | `<-- {METHOD} {URI} {STATUS} ({duration}ms)` | INFO |
| **예외 응답** | `<-- {METHOD} {URI} {STATUS} ({duration}ms) ex={ExceptionClass}: {message}` | WARN(4xx) / ERROR(5xx) |

**GlobalExceptionHandler 메시지:**

| 예외 | 포맷 | 레벨 |
|------|------|------|
| `BusinessException` | `BusinessException: code={errorCode}, message={message}` | WARN |
| `MethodArgumentNotValidException` | `ValidationException: fields={fieldErrors}` | WARN |
| 그 외 `Exception` | `Unexpected exception` + stacktrace | ERROR |

### 로그 출력 예시

```
# 정상 흐름
2026-05-04 14:23:11.482 [http-nio-18083-exec-3] [a1b2c3d4e5f6a7b8] INFO  c.w.k.c.l.ApiLoggingInterceptor - --> GET /realtyos/api/v1/teams handler=TeamController#getTeams
2026-05-04 14:23:11.534 [http-nio-18083-exec-3] [a1b2c3d4e5f6a7b8] INFO  c.w.k.c.l.ApiLoggingInterceptor - <-- GET /realtyos/api/v1/teams 200 (52ms)

# 비즈니스 예외 (404)
2026-05-04 14:25:02.110 [http-nio-18083-exec-5] [9f8e7d6c5b4a3210] INFO  c.w.k.c.l.ApiLoggingInterceptor - --> GET /realtyos/api/v1/teams/999 handler=TeamController#getTeam
2026-05-04 14:25:02.142 [http-nio-18083-exec-5] [9f8e7d6c5b4a3210] WARN  c.w.k.c.e.GlobalExceptionHandler - BusinessException: code=TEAM_NOT_FOUND, message=팀을 찾을 수 없습니다.
2026-05-04 14:25:02.143 [http-nio-18083-exec-5] [9f8e7d6c5b4a3210] WARN  c.w.k.c.l.ApiLoggingInterceptor - <-- GET /realtyos/api/v1/teams/999 404 (33ms) ex=BusinessException: 팀을 찾을 수 없습니다.

# 예상치 못한 예외 (500)
2026-05-04 14:30:45.901 [http-nio-18083-exec-2] [11223344aabbccdd] INFO  c.w.k.c.l.ApiLoggingInterceptor - --> POST /realtyos/api/v1/games handler=GameController#createGame
2026-05-04 14:30:45.998 [http-nio-18083-exec-2] [11223344aabbccdd] ERROR c.w.k.c.e.GlobalExceptionHandler - Unexpected exception
java.lang.NullPointerException: ...
2026-05-04 14:30:45.999 [http-nio-18083-exec-2] [11223344aabbccdd] ERROR c.w.k.c.l.ApiLoggingInterceptor - <-- POST /realtyos/api/v1/games 500 (98ms) ex=NullPointerException: null
```

### 로깅 절대 규칙

```
1. TraceId 생성은 TraceIdFilter에서만 한다. (다른 곳에서 MDC.put("traceId", ...) 금지)
2. MDC.put을 한 컴포넌트가 MDC.remove를 책임진다. (스레드 풀 누수 방지)
3. Filter는 로그 메시지를 출력하지 않는다.
4. Interceptor는 stacktrace를 출력하지 않는다.
5. 요청/응답 body 로깅은 본 명세 미포함. 필요 시 이 문서 갱신 후 구현한다.
6. 민감 헤더(Authorization, Cookie, Set-Cookie)는 절대 평문 로깅하지 않는다.
7. 로그 패턴은 logback-spring.xml에 정의하며 코드에서 직접 prefix하지 않는다.
8. /actuator, /error, /favicon.ico는 항상 제외한다.
```

### 로깅 구현 체크리스트

- [ ] `TraceIdFilter`에서 `MDC.put`과 `MDC.remove`가 `finally`로 짝을 이루는가?
- [ ] `TraceIdFilter` 안에 `log.info(...)` 호출이 없는가?
- [ ] `ApiLoggingInterceptor`에서 `MDC.put`/`MDC.remove`를 직접 호출하지 않는가?
- [ ] `WebConfig`에서 Filter Order가 `HIGHEST_PRECEDENCE`인가?
- [ ] 응답 헤더 `X-Trace-Id`가 세팅되는가?
- [ ] `logback-spring.xml`에 `%X{traceId:-}`가 포함되었는가?
- [ ] 4xx는 WARN, 5xx는 ERROR로 구분되는가?

---

## 6. 신규 모듈 생성 템플릿

### 변수 치환 표

| 변수 | 설명 | 예시 |
|------|------|------|
| `{module}` | 모듈명 (소문자) | `user` |
| `{Module}` | 모듈명 (PascalCase) | `User` |
| `{Aggregate}` | 애그리거트명 | `User` |
| `{table_name}` | DB 테이블명 (snake_case) | `users` |
| `{Status}` | 상태 Enum명 | `UserStatus` |

### 파일 생성 체크리스트

| # | 파일 | 경로 |
|---|------|------|
| 1 | 상태 Enum | `{module}/domain/{Status}.java` |
| 2 | Value Object (선택) | `{module}/domain/{VO}.java` |
| 3 | 도메인 모델 | `{module}/domain/{Aggregate}.java` |
| 4 | Repository Port | `{module}/domain/{Aggregate}Repository.java` |
| 5 | 도메인 서비스 (선택) | `{module}/domain/service/{Domain}Policy.java` |
| 6 | **도메인 테스트** | `test/{module}/domain/{Aggregate}Test.java` |
| 7 | Command 객체 | `{module}/application/service/Register{Module}Command.java` |
| 8 | Application Service | `{module}/application/service/{Module}ApplicationService.java` |
| 9 | **서비스 테스트** | `test/{module}/application/service/{Module}ApplicationServiceTest.java` |
| 10 | JPA Entity | `{module}/infrastructure/jpa/entity/{Aggregate}JpaEntity.java` |
| 11 | JPA Repository | `{module}/infrastructure/jpa/repository/{Aggregate}JpaRepository.java` |
| 12 | Adaptor | `{module}/infrastructure/jpa/{Aggregate}RepositoryJpaAdaptor.java` |
| 13 | Domain Config (선택) | `{module}/infrastructure/config/{Module}DomainConfig.java` |
| 14 | Request DTO | `{module}/interfaces/dto/Register{Module}Request.java` |
| 15 | Response DTO | `{module}/interfaces/dto/{Module}Response.java` |
| 16 | Controller | `{module}/interfaces/controller/{Module}Controller.java` |

> 각 파일의 코드 패턴은 **Section 3** 참고.

---

## 7. 안티패턴 검증

### 검증 우선순위

```
🔴 Critical (P0) — 즉시 수정 필수 (머지 차단)
🟡 Warning  (P1) — 리뷰 후 수정 필요
🔵 Info     (P2) — 개선 권장
```

### 🔴 P0-1. Domain에 프레임워크 어노테이션

```bash
grep -rn "import jakarta.persistence\|import org.springframework\|@Entity\|@Table\|@Service\|@Component\|@Repository\|@Transactional" \
  src/main/java/com/web/realtyos/*/domain/
```

**수정:** domain 패키지는 `@Builder`(Lombok)만 허용. JPA 어노테이션은 `infrastructure/jpa/entity/`에만.

### 🔴 P0-2. Application Service에 비즈니스 로직 유출

```bash
grep -rn "if (\|switch\|} else" src/main/java/com/web/realtyos/*/application/
```

> 단순 null 체크(`orElseThrow`)는 허용. 비즈니스 판단 로직만 위반.

```java
// ❌
@Transactional
public Team deactivate(Long id) {
    Team team = teamRepository.findById(id).orElseThrow(...);
    if (team.status() == TeamStatus.INACTIVE) { // ← domain에 있어야 함
        throw new BusinessException(ErrorCode.ALREADY_INACTIVE);
    }
    return teamRepository.save(Team.builder().id(team.id()).status(TeamStatus.INACTIVE).build());
}

// ✅
@Transactional
public Team deactivate(Long id) {
    Team team = teamRepository.findById(id).orElseThrow(...);
    return teamRepository.save(team.deactivate()); // 도메인 메서드 호출
}
```

### 🔴 P0-3. Application → Infrastructure 직접 의존

```bash
grep -rn "import com.web.realtyos.*.infrastructure\|JpaRepository\|JpaEntity" \
  src/main/java/com/web/realtyos/*/application/
```

**수정:** `UserJpaRepository` → `UserRepository` (Port interface)

### 🔴 P0-4. Infrastructure → Application 역방향 의존

```bash
grep -rn "import com.web.realtyos.*.application" src/main/java/com/web/realtyos/*/infrastructure/
```

### 🔴 P0-5. Service가 DTO를 반환

```bash
grep -rn "import com.web.realtyos.*.interfaces.dto\|Response \|Request " \
  src/main/java/com/web/realtyos/*/application/
```

**수정:** Application Service는 Domain Model 반환, Controller가 `Response.from(domain)`으로 변환.

### 🔴 P0-6. save() 후 원본 객체 반환 (ID 누락)

```bash
grep -A5 "public.*save" src/main/java/com/web/realtyos/*/infrastructure/jpa/*Adaptor.java
```

```java
// ❌
jpaRepository.save(entity);
return domain; // ID가 null인 채로 반환!

// ✅
return jpaRepository.save(entity).toDomain(); // DB 할당 ID 포함
```

### 🟡 P1-1. Domain Service에 Repository 주입

```bash
grep -rn "Repository" src/main/java/com/web/realtyos/*/domain/service/
```

**수정:** Domain Service는 파라미터로 데이터를 받고, Application Service가 Repository에서 미리 조회.

### 🟡 P1-2. Domain에 Setter

```bash
grep -rn "@Setter\|@Data\|public void set" src/main/java/com/web/realtyos/*/domain/
```

### 🟡 P1-3. 테스트 누락

```bash
find src/main/java -path "*/domain/*.java" | sort
find src/test/java -name "*Test.java" | sort
```

### 전체 검증 스크립트

`scripts/validate-architecture.sh`로 저장하여 사용:

```bash
#!/bin/bash
echo "========================================="
echo "  DDD + Hexagonal 아키텍처 검증"
echo "========================================="

ERRORS=0
SRC="src/main/java/com/web/realtyos"

echo "[P0-1] Domain 프레임워크 어노테이션..."
if grep -rn "import jakarta.persistence\|import org.springframework\|@Entity\|@Service\|@Component\|@Repository\|@Transactional" $SRC/*/domain/ 2>/dev/null; then
    echo "  ❌ FAIL"; ERRORS=$((ERRORS+1))
else echo "  ✅ PASS"; fi

echo "[P0-3] Application → Infrastructure 직접 의존..."
if grep -rn "import com.web.realtyos.*.infrastructure\|JpaRepository\|JpaEntity" $SRC/*/application/ 2>/dev/null; then
    echo "  ❌ FAIL"; ERRORS=$((ERRORS+1))
else echo "  ✅ PASS"; fi

echo "[P0-4] Infrastructure → Application 역방향 의존..."
if grep -rn "import com.web.realtyos.*.application" $SRC/*/infrastructure/ 2>/dev/null; then
    echo "  ❌ FAIL"; ERRORS=$((ERRORS+1))
else echo "  ✅ PASS"; fi

echo "[P0-5] Service DTO 반환..."
if grep -rn "import com.web.realtyos.*.interfaces.dto" $SRC/*/application/ 2>/dev/null; then
    echo "  ❌ FAIL"; ERRORS=$((ERRORS+1))
else echo "  ✅ PASS"; fi

echo "[P1-2] Domain Setter..."
if grep -rn "@Setter\|@Data\|public void set" $SRC/*/domain/ 2>/dev/null; then
    echo "  ⚠️  WARNING"; ERRORS=$((ERRORS+1))
else echo "  ✅ PASS"; fi

echo "========================================="
[ $ERRORS -eq 0 ] && echo "  ✅ 전체 검증 통과!" || echo "  ❌ $ERRORS개 위반 발견!"
echo "========================================="
exit $ERRORS
```

---

## 8. 코드 리뷰 체크리스트

| # | 검증 항목 | 우선순위 |
|---|---------|---------|
| 1 | domain에 Spring/JPA import 없음 | 🔴 P0 |
| 2 | Application Service에 if/else 비즈니스 로직 없음 | 🔴 P0 |
| 3 | Application에서 infrastructure import 없음 | 🔴 P0 |
| 4 | Infrastructure에서 application import 없음 | 🔴 P0 |
| 5 | Service 반환 타입이 Domain Model임 | 🔴 P0 |
| 6 | Adaptor save() 후 toDomain() 반환함 | 🔴 P0 |
| 7 | DTO가 interfaces 패키지에만 존재 | 🔴 P0 |
| 8 | Domain Service에 Repository 없음 | 🟡 P1 |
| 9 | Domain에 setter 없음 | 🟡 P1 |
| 10 | 모든 Domain 모델/서비스에 단위 테스트 있음 | 🟡 P1 |
| 11 | Application Service에 Mock 기반 테스트 있음 | 🟡 P1 |
| 12 | client 패키지에 외부 예외 → BusinessException 변환 | 🟡 P1 |
| 13 | @DisplayName 한글 작성 | 🔵 P2 |
| 14 | given-when-then 구조 준수 | 🔵 P2 |