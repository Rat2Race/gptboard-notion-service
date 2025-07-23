<div align="center">

<img src="public/image/tiredMouse.png" width="200"/>

### Notion Review App Backend ✅
<img src="https://img.shields.io/badge/Spring%20Boot-3.5.x-green" alt="Spring Boot" />
<img src="https://img.shields.io/badge/OpenAI-GPT-blueviolet" alt="OpenAI" />
<img src="https://img.shields.io/badge/Project%20Duration-2025.06.17~-%23a8e063?style=flat" alt="Project Duration" />

</div>

<br>

## 🚩 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Roadmap](#roadmap)
- [Database Schema](#database-schema)
- [API Endpoints](#api-endpoints)
- [TroubleShooting](#troubleshooting)

<br>

## ✨ Features <a name="features"></a>

- 내 노션 페이지/DB 목록 조회 및 선택
- 페이지 콘텐츠 읽기 및 파싱(Markdown/텍스트)

<br>

## 🛠 Tech Stack <a name="tech-stack"></a>

<p align="left">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.5.x-6DB33F?logo=springboot&logoColor=white" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/MySQL-005C84?logo=mysql&logoColor=white" alt="MySQL" />
  <img src="https://img.shields.io/badge/OpenAI-GPT-412991?logo=openai&logoColor=white" alt="OpenAI" />
  <img src="https://img.shields.io/badge/Notion-000000?logo=notion&logoColor=white" alt="Notion" />
  <img src="https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white" alt="Docker" />
  <img src="https://img.shields.io/badge/GitHub%20Actions-2088FF?logo=githubactions&logoColor=white" alt="GitHub Actions" />
</p>

<br>

## 🚀 Getting Started <a name="getting-started"></a>

```bash
# 레포지토리 클론
$ git clone https://github.com/Rat2Race/gptboard-notion-service.git

# 백엔드 폴더 이동 및 빌드
$ cd backend
$ ./gradlew build

# 프론트엔드 폴더 이동 및 설치
$ cd ../frontend
$ npm install
```

- Notion API, OpenAI API 키 등은 `application.yml`에 입력 필요

```bash
# 백엔드(Spring Boot)
$ cd backend
$ ./gradlew bootRun

# 프론트엔드(React)
$ cd ../frontend
$ npm start
```

<br>

## 🗺 Roadmap <a name="roadmap"></a>

- [ ] Notion OAuth 연동
- [ ] 노션 페이지/DB 파싱

<br>

## 🗄️ Database Schema <a name="database-schema"></a>

ERD 다이어그램

<br>

## 🔗 API Endpoints <a name="api-endpoints"></a>

| Method | Endpoint                    | Description            |
| ------ |-----------------------------|------------------------|
| GET    | /api/login                  | Notion OAuth 로그인 리다이렉트 |
| POST   | /api/auth/issue             | Notion 액세스 토큰 발급       |
| POST   | /api/auth/revoke            | Notion 액세스 토큰 취소       |
| POST   | /api/notion/pages           | Notion 전체 페이지 조회       |
| POST   | /api/notion/page/{pageId}          | Notion 특정 페이지 조회       |
| POST   | /api/notion/blocks/{blockId}/children | Notion 페이지 전체 블록 조회    |

<br>

## 🐛 TroubleShooting <a name="troubleshooting"></a>

- 문제/해결/배운점

<br>
