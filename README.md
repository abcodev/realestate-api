# RealtyOS
## Real-estate AI Decision Support Platform
RAG · Agent · Long-term Memory · Model Routing 기반 AI Runtime Platform

---

# 1. 프로젝트 개요

## 한 줄 소개
부동산 실거래가 및 지역 데이터를 기반으로  
RAG · Agent · Long-term Memory · Model Routing 구조를 활용한  
AI-native Decision Support Platform 프로젝트.

단순 AI 챗봇이 아닌, 다양한 AI 기능을 공통 Runtime 구조로 추상화하여  
운영 가능한 AI Platform을 설계하는 것을 목표로 한다.

---

# 2. 프로젝트 목표

## 기술 목표
- Java/Spring 기반 AI Runtime 구축
- RAG 기반 Personalized Retrieval Pipeline 구축
- Multi-Agent Runtime 설계
- Long-term Memory 시스템 구현
- Multi-model Routing 구조 설계
- SaaS + Self-hosted LLM Hybrid Serving 구축
- Prompt Runtime 및 Prompt Versioning 구축
- AI Evaluation Platform 구축
- Kafka 기반 Event-driven AI Runtime 구축
- SSE 기반 Streaming 응답 구현
- AI Observability 시스템 구축

## 서비스 목표
사용자의:
- 관심 지역
- 투자 성향
- 실거주 여부
- 예산 범위
- 반복 조회 패턴

등을 장기적으로 기억하고  
개인화된 부동산 분석 및 의사결정 경험을 제공하는 AI-native Platform 구현

---

# 3. 핵심 문제 정의

기존 부동산 서비스의 문제:

- 단순 데이터 조회 중심
- 사용자 맥락 반영 부족
- 지역 분석 정보 분산
- 투자 성향 기반 추천 부족
- 복잡한 데이터 해석 어려움
- AI 응답 품질 운영 체계 부족

특히 다음 데이터는 복합적으로 결합되어야 함:
- 실거래가
- 입주 물량
- 정책 변화
- 뉴스 데이터
- 사용자 관심 데이터

→ 단일 LLM 구조로는 한계 존재

---

# 4. 해결 접근 방식

## AI Runtime Platform 구조 설계

AI 기능을 다음 단위로 분리:

- Retrieval
- Memory
- Agent
- Prompt Runtime
- Evaluation
- Model Routing

→ 운영 가능한 AI Platform 구조로 설계

---

# 5. 핵심 기능

---

## 5-1. RAG 기반 Personalized Retrieval

### Retrieval 데이터
- 국토부 실거래가 데이터
- 지역 거래량 데이터
- 입주 예정 물량 데이터
- 정책 뉴스 데이터
- 교통 데이터
- 학군 데이터
- 사용자 관심 데이터
- 과거 조회 이력

### Retrieval Flow

User Input
→ Query Rewrite
→ Hybrid Retrieval
├ Dense Retrieval
└ BM25 Retrieval
→ Metadata Filtering
→ Reranking
→ Context Compression
→ Context Assembly
→ LLM Generation


### Hybrid Retrieval 전략

**Dense Retrieval**
- Embedding 기반 semantic search

**BM25 Retrieval**
- 지역명 / 아파트명 / 정책 키워드 기반 정확 검색

### Metadata Filtering
- region
- price_range
- transaction_type
- construction_year
- apartment_type
- supply_volume

### Reranking
- relevance 기반 재정렬

### Personalized Retrieval
User Long-term Memory 기반 반영:
- 관심 지역
- 가격대
- 투자 성향
- 실거주 여부

---

## 5-2. Multi-Agent Runtime

### Agent 구성

**Retrieval Agent**
- 실거래가 데이터
- 뉴스/정책
- 지역 통계

**Analysis Agent**
- 가격 흐름 분석
- 거래량 분석
- 지역 트렌드 분석

**Risk Evaluation Agent**
- 공급 리스크
- 가격 변동 리스크
- 거래 감소 리스크

**Recommendation Agent**
- 사용자 성향 기반 추천
- 지역 기반 추천
- 예산 기반 추천

**Memory Agent**
- 사용자 행동 패턴 저장
- 투자 성향 저장

**Evaluation Agent**
- retrieval relevance 평가
- hallucination 모니터링
- prompt 비교 평가

---

## 5-3. Agent Workflow Orchestration


User Input
→ Intent Analysis
→ Retrieval Agent
→ Memory Agent
→ Context Assembly
→ Analysis Agent
→ Risk Evaluation Agent
→ Model Router
→ LLM Generation
→ Evaluation Agent
→ Streaming Response


---

## 5-4. Long-term Memory

### 저장 데이터
- 관심 지역
- 반복 조회 지역
- 가격대 선호
- 투자/실거주 성향
- 신축 선호 여부
- 조회 패턴

### 활용
- Personalized Retrieval 강화
- 사용자 Context 유지
- 반복 탐색 감소
- 개인화 추천

---

## 5-5. Multi-LLM Model Router

### 목적
작업별 최적 모델 선택

### Routing 예시

| 작업 | 모델 |
|------|------|
| 단순 요약 | Local sLM |
| 빠른 브리핑 | Small LLM |
| 투자 분석 | GPT-4급 |
| 뉴스 분석 | GPT-4o-mini |

### Routing 기준
- latency
- cost
- quality
- token usage
- fallback

### Hybrid Serving
- OpenAI (고품질)
- Ollama (로컬)
- Dynamic Routing

---

## 5-6. Prompt Runtime

### Prompt Versioning
- Prompt 템플릿 버전 관리
- A/B 테스트
- 실험 기반 개선

### Context Composition
- Retrieval Context
- User Memory
- Market Summary
- Region Stats
- Agent Instructions

---

## 5-7. AI Evaluation Platform

### 평가 항목
- retrieval relevance
- response quality
- reasoning quality
- hallucination ratio
- recommendation relevance

### 기능
- Prompt 평가
- Response scoring
- A/B 비교
- Retrieval 품질 측정

---

## 5-8. AI Observability

### 수집 데이터
- token usage
- latency
- retrieval score
- retry count
- fallback rate
- streaming duration

### 목적
- 비용 분석
- 성능 비교
- 장애 분석
- Prompt 최적화

---

# 6. 시스템 아키텍처


Flutter App
↓
Spring API Server
↓
Kafka Event Publish
↓
AI Runtime
├ Retrieval Agent
├ Analysis Agent
├ Risk Evaluation Agent
├ Recommendation Agent
├ Memory Agent
└ Evaluation Agent
↓
Prompt Runtime
↓
Model Router
↓
LLM Providers
├ OpenAI
├ Ollama
└ Local sLM
↓
RAG Pipeline
↓
SSE Streaming Response


---

# 7. Event-driven AI Runtime

### Events
- conversation-started
- retrieval-completed
- analysis-generated
- memory-updated
- evaluation-generated
- streaming-completed
- fallback-triggered

### Flow

User Request
→ Kafka Event Publish
→ Agent Processing
→ Prompt Runtime
→ Model Routing
→ LLM Response
→ Streaming Response


---

# 8. Streaming Architecture (SSE)

### 목적
- 실시간 응답 UX
- 긴 분석 응답 개선
- AI 브리핑 경험

### 고려사항
- reconnect 처리
- emitter lifecycle
- multi-instance 대응
- timeout 처리

---

# 9. 기술 스택

| 영역 | 기술 |
|------|------|
| Backend | Java 21, Spring Boot |
| AI Runtime | LangChain4j |
| Mobile | Flutter |
| Admin | React + Vite |
| DB | PostgreSQL |
| Vector DB | pgvector |
| Cache | Redis |
| Queue | Kafka |
| Streaming | SSE |
| LLM | OpenAI, Ollama |
| Infra | Docker Compose |

---

# 10. 핵심 설계 포인트

1. AI Runtime 구조
2. Personalized RAG
3. Multi-model Routing
4. Long-term Memory
5. 운영 가능한 AI 시스템
    - Streaming
    - Retry
    - Monitoring
    - Evaluation
    - Prompt Versioning
    - Token tracking

---

# 11. 기대 효과

- Java/Spring 기반 AI Platform 경험
- RAG / Retrieval 설계 경험
- Multi-Agent Runtime 경험
- Long-term Memory 설계 경험
- Multi-model 운영 경험
- AI Evaluation Platform 구축 경험
- Event-driven AI Runtime 경험
- 운영 가능한 AI 시스템 설계 경험

---

# 12. 향후 확장 방향

- MCP 기반 Tool 연동
- 실시간 정책 분석
- 지도 기반 분석
- 음성 부동산 상담
- Multi-domain Agent 확장
- AI 자동 리포트 생성

---

# 13. 프로젝트 핵심 문장

> “부동산 실거래가 및 지역 데이터를 기반으로,  
RAG · Agent · Long-term Memory · Model Routing 구조를 활용한  
AI Runtime Platform을 설계 및 운영”