파이어폭스 & 자바 셀레니움을 이용한 네이버 뉴스 크롤링
===

# 🚤 개발 환경
## Java : `Amazon Correto 17`
- `Ubuntu (Remote)` 환경과 `Mac (Local)` 환경을 맞추기 위해 선택

# 🛥 사용한 라이브러리
## 1) `Jackson-databind`
### 사용한 이유
1) `jackson-core` : 저수준 api 제공
2) `jackson-databind` : 고수준 api 제공
→ 객체 지향적으로 json 을 read/write 해보고 싶어 jackson-databind 선택!
## 2) `Java Selenium`
### 사용한 이유
- 네이버 뉴스 란에는 `더보기` 버튼 등이나, `스크롤` 등의 액션을 통해 정보를 보여지는 부분들이 많았다.
- 이를 통해, `동적`으로 web을 크롤링하고자 하였다.
- `Java Selenium`은 동적 Web 크롤링을 할 수 있기 때문에 선택!

# 🚢 파이어폭스 선택 이유
- 로컬 환경은 (Mac M1 - ARM64) 환경이었기 때문에 `Ubuntu (remote)` 서버도 `aarch64` 환경에 맞춰 설치해야했다.
  - `emulate`를 통해 다른 os도 설치할 수 있었지만, 속도가 너무 느려, 포기했다..
- 따라서 `Ubuntu (remote)` 에서 사용할 수 있는 브라우저를 찾다가, `Firefox`와 `geckodriver`를 통해 크롤링 할 수 있다는 것을 알게 되어 사용

# 🌌 구현 방식

## 1) `Crawler.java`
- 네이버 뉴스에서 각각의 `ul` 태그 - `li` 태그 - `div` 태그 순으로 `WebElement`를 파싱해 나가면서 원하는 요소를 저장
- `--headless` 옵션을 통해 `Ubuntu Server` terminal에서도 `GUI` 없이 동작하도록 구현
## 2) `News.java`
- 저장한 정보를 객체로 관리하기 위해 record 타입의 객체 생성
## 3) `JsonWriter.java`
- `News` 객체들을 모두 모아, `news.json` 파일로 저장
- 기존에 저장되어있는 `News` 객체들의 중복을 제거하기 위해, `Map<>` 형태로 `key-value` json 읽어옴
- 그런 후, 해당 `Map<>`에 `put` 연산을 하게 되면, `key`값인 `News URL`에 대해 중복 제거가 되게 구현
