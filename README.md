# RealtyOS API

RAG와 LLM을 활용한 부동산 실거래 데이터 검색·분석 API.

RealtyOS는 국토교통부 실거래 데이터를 수집하고, 이를 검색 가능한 RAG 문서와 임베딩으로 변환한 뒤 OpenAI 또는 Ollama 기반 LLM으로 답변을 생성하는 AI 부동산 분석 플랫폼입니다.

---

## 핵심 기능

- 아파트 실거래 데이터 수집 및 저장
- 실거래 데이터 기반 RAG 문서 생성
- OpenAI / Ollama 임베딩 생성 및 벡터 검색
- 자연어 질문에서 지역, 가격, 면적, 기간 조건 추론
- RAG 근거 문서 기반 LLM 답변 생성
- SSE 기반 스트리밍 답변
- 사용자 AI 메모리 기반 개인화 검색
- Kakao / Google OAuth2 로그인
- JWT 기반 인증
- 실거래 수집 후 RAG 문서와 임베딩 자동 동기화

---

## 기술 스택

| 영역 | 기술 |
| --- | --- |
| Backend | Java 21, Spring Boot |
| Auth | Spring Security, OAuth2, JWT |
| Database | PostgreSQL, pgvector |
| Migration | Flyway |
| AI | OpenAI, Ollama |
| Streaming | Server-Sent Events |
| Frontend | React + Vite |

---

## RAG 흐름

```text
실거래 원본 데이터
-> RAG 문서 생성
-> 임베딩 생성
-> 사용자 질문
-> Query Rewrite
-> 벡터 검색
-> RAG Prompt 구성
-> LLM 답변 생성
-> 일반 응답 또는 SSE 스트리밍
```

검색 결과가 없을 경우 keyword fallback을 사용합니다. fallback 결과는 실제 벡터 유사도가 아니므로 관련도가 고정값으로 표시될 수 있습니다.

---

## 주요 API

### RAG

| Method | Path | 설명 |
| --- | --- | --- |
| POST | `/api/v1/rag/search` | RAG 문서 검색 |
| POST | `/api/v1/rag/ask` | RAG 기반 답변 생성 |
| POST | `/api/v1/rag/ask/stream` | RAG 기반 SSE 스트리밍 답변 |
| POST | `/api/v1/rag/documents/deals` | 실거래 데이터를 RAG 문서로 변환 |
| POST | `/api/v1/rag/documents/embeddings` | 누락 임베딩 생성 |
| POST | `/api/v1/rag/documents/sync` | 문서 생성과 임베딩 생성 동기화 |
| GET | `/api/v1/rag/documents/stats` | RAG 인덱스 상태 조회 |

### 사용자 AI 메모리

| Method | Path | 설명 |
| --- | --- | --- |
| GET | `/api/v1/rag/memory/me` | 내 AI 메모리 조회 |
| GET | `/api/v1/rag/memory/me/events` | 최근 RAG 사용 이벤트 조회 |
| PUT | `/api/v1/rag/memory/me` | 내 AI 메모리 수정 |
| DELETE | `/api/v1/rag/memory/me` | 내 AI 메모리 초기화 |

### 인증

| Method | Path | 설명 |
| --- | --- | --- |
| GET | `/api/v1/auth/login/{provider}` | OAuth2 로그인 시작 |
| GET | `/api/v1/auth/token/exchange` | OAuth 임시 code를 JWT로 교환 |
| POST | `/api/v1/auth/reissue` | access token 재발급 |
| POST | `/api/v1/auth/logout` | 로그아웃 |
| DELETE | `/api/v1/users` | 회원 탈퇴 |

---

## AI Provider

RealtyOS는 임베딩과 답변 생성을 provider/model 단위로 분리합니다.

- 임베딩 provider: OpenAI, Ollama
- 답변 provider: OpenAI, Ollama
- 같은 문서에 대해 여러 provider/model 임베딩을 동시에 저장 가능
- 검색 요청의 provider/model은 저장된 임베딩 조합과 일치해야 함

예를 들어 Ollama 임베딩으로 검색하려면 `OLLAMA / nomic-embed-text` 조합으로 임베딩이 생성되어 있어야 합니다.

---

## SSE 스트리밍

`/api/v1/rag/ask/stream`은 답변 생성 과정을 SSE 이벤트로 전송합니다.

```text
memory_loaded
retrieval_started
retrieval_completed
model_selected
token
completed
```

프론트엔드는 `token` 이벤트를 받을 때마다 답변 텍스트를 누적 표시합니다.

---

## 사용자 AI 메모리

로그인 사용자의 RAG 사용 패턴을 저장하고 다음 검색에 반영합니다.

저장되는 주요 정보:

- 선호 지역
- 최근 조회 지역
- 가격대
- 최근 질문
- 검색 이벤트 이력

이를 통해 반복 질문에서 사용자의 맥락을 유지하고, 검색 조건을 개인화합니다.

---

## 자동 동기화

스케줄러가 실거래 데이터를 수집하고 RAG 문서/임베딩 동기화를 수행합니다.

- 실거래 데이터 정기 수집
- RAG 문서 생성/갱신
- 누락 임베딩 생성
- 실거래 문서 변경 시 기존 임베딩 무효화

---

## 실행 개요

로컬 실행에는 다음이 필요합니다.

- Java 21
- PostgreSQL + pgvector
- Ollama 또는 OpenAI API key

기본 실행 흐름:

```text
1. PostgreSQL/pgvector 실행
2. API 서버 실행
3. 실거래 데이터 수집
4. RAG 문서 생성
5. 임베딩 생성
6. RAG 검색/답변 API 호출
```

세부 환경값은 `application.yml`, `application-loc.yml`을 기준으로 관리합니다.

---

## 주요 모듈

```text
auth        OAuth2, JWT, 로그인/토큰
common      공통 설정, AI client, model router
rag         RAG 검색, 문서/임베딩, 답변, 메모리
realestate  실거래/분양 데이터 수집
user        사용자 설정
```

---

## 현재 구현 기준

- RAG 문서와 임베딩은 PostgreSQL/pgvector에 저장
- OpenAI와 Ollama 임베딩 동시 운영 가능
- 지역 검색은 실제 거래 소재지 기준으로 필터링
- 자연어 면적 표현을 제곱미터 조건으로 변환
- SSE 스트리밍 답변 제공
- OAuth2 로그인 후 JWT 발급
- 사용자 AI 메모리 저장 및 검색 조건 반영

