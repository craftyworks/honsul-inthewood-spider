# 프로젝트 : 혼술 in the wood 

[![build](https://gitlab.com/craftyworks/honsul-inthewood-spider/badges/master/build.svg)](https://craftyworks.gitlab.io/honsul-inthewood-spider/)

[![coverage report](https://gitlab.com/craftyworks/honsul-inthewood-spider/badges/master/coverage.svg)](https://craftyworks.gitlab.io/honsul-inthewood-spider/jacoco/)

![test result](https://craftyworks.gitlab.io/honsul-inthewood-spider/test-result.svg)
## 환경설정

### 프로그램 설치
- [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [STS](https://spring.io/tools)
- [lombok.jar](https://projectlombok.org/download)
- [D2 코딩 글꼴](https://github.com/naver/d2codingfont) (Optional)
- [HeidiSQL](https://www.heidisql.com/) (Optional)

### lombok 설치

다운로드 받은 **lombok.jar** 파일을 STS 설치 경로의 STS.exe 가 존재하는 폴더에 복사한다.

STS.exe 가 존재하는 폴더의 STS.ini 파일을 열어 맨 아래에 아래 문자열을 삽입한다. 삽입 시 설치 경로는 적절하게 수정한다.

```
-javaagent:E:\ProjectHome\tools\sts-bundle\sts-3.9.4.RELEASE\lombok.jar
```

STS.exe 실행 후 Help > About Spring Tool Suite 으로 실행되는 About 창에 아래와 같은 문구가 포함되어 있는지 확인한다.

( *Lombok v1.16.20 "Dancing Elephant" is installed.* )

![lombok](https://projectlombok.org/img/eclipse-about.png)

### STS 설정

STS.exe 실행 후 Window > Preferences 메뉴로 이동하여 아래와 같이 설정한다.

- General > Appearance > Colors and Fonts
  - Base > Text Font : Edit 클릭하여 D2 Coding Font 로 변경
- General > Workspace
  - Text File Encoding > Other : UTF-8 입력 후 Apply
- General > Compare/Patch
  - Ignore white space : 체크
- Validation
  - Suspend all validators : 체크
- General > Editors > Text Editors
  - Display tab width : 2
  - Insert spaces for tabs : 체크
- General > Editors > Text Editors > Spelling
  - Enable spell checking : 체크 해제
- Java > Code Style > Organize Imports
  - Number of static imports needed for .* : 99 에서 0 으로 변경
- Java > Editor > Save Actions
  - Perform the selected action on save : 체크
  - Organize imports : 체크
  
### Project Import

STR 실행 후 gitlab 으로부터 프로젝트를 내려 받는다.

- File > Import
  1. Git > Projects from Git : 선택 -> Next
  2. Clone URI : 선택 -> Next
  3. URI : https://gitlab.com/craftyworks/honsul-inthewood-spider.git
  4. User : <너의 gitlab 계정 이메일>
  5. Password : <너의 gitlab 계정 패스워드>
  6. Store in Secure Store : 체크 -> Next

```
이 후로는 선택지가 거의 단방향이다. 
Next 연타 후 Finish 까지 진행해도 무방하다.
```
   
- Local Destination 창이 나오면 Directory 를 너의 STS workspace 경로로 변경한다.
  - 예) E:\temp\ProjectHome\workspace\honsul-inthewood-spider
  
- Import existing Eclipse Projects 선택
  - Finish

이제 Package Explorer 에 *honsul-inthewood-spider* 프로젝트가 보일 것이고, 빌드 작업이 진행된다. 

빌드 작업이 성공하면 붉은색의 경고등이 사라진다.

## GitHub Flow

너는 아래와 같은 GitHub Flow 를 준수하여 너가 작업한 내용을 서버에 반영해야 한다.

- GitHub Flow 에 대한 친절한 설명은 다음 [문서](https://guides.github.com/introduction/flow/) 를 참고해라.
- 만약 너가 Git 이 생소하다면 먼저 이 [문서](https://backlog.com/git-tutorial/kr/) 나 요 [문서](https://git-scm.com/book/ko/v2) 부터 읽어보길 바란다.

### 1. Pull

항상 원격지의 **master** branch 에서 최신 변경내용을 받아 동기화 해라.

너의 local branch 를 master 로 유지하고 프로젝트 우클릭 > Team > Pull 명령으로 동기화한다.

### 2. Branch

너는 작업 전에 항상 local branch 를 생성해야 한다.

프로젝트 우클릭 > Team > Switch To > New Branch 명령으로 새로운 Branch 를 생성한다.

Branch 명은 너가 지금 작업하려하는 휴양림 ID 로 생성하면 된다.

```
예) branch name : R018
```

### 3. Commit

니가 생성한 local branch 에서의 작업 중인 내용은 수시로 너의 local Repository 에 commit 해라.

너의 Commit 내역은 원격지 서버에는 반영되지 않을 것이다. 그러므로 마음껏 commit 하도록 해라.

### 4. Push 

너의 local branch 작업 내용을 원격지 서버에 등록해라. 우리의 **master** branch 를 오염시킬 염려는 없으므로 부담 갖지 말고 push 해도 된다.

프로젝트 우클릭 > Team > Push Branch'RXXX'... 명령으로 너의 local branch 를 서버에 반영시켜라.

성공한다면 우리의 gitlab 프로젝트 페이지에서 니가 올린 branch 를 확인 할 수 있을 것이다.

### 5. Merge Request

이제 너의 branch 의 작업 내용을 우리의 **master** 에 반영시킬 시간이 되었다.

이 작업은 gitlab 프로젝트 페이지에서 수행해야 한다.

gitlab 프로젝트 > Repository > 브랜치 메뉴로 이동한다.

니가 올린 branch 우측에 **Merge Request** 버튼을 클릭한다.

너의 작업 내용을 성심성의껏 작성한 후 하단의 **Submit Request** 버튼을 클릭한다.

이제 프로젝트 관리자가 너의 요청을 확인하고 문제가 없다면 **master** branch 에 반영시켜 줄 것이다.

> 관리자는 니가 올린 Merge Request 를 승인해 줌과 동시에 니가 올린 branch 를 Remote 저장소에서 삭제 할 것이다. 너의 local branch 는 니가 알아서 삭제하거나 간직하면 된다.

### 6. 다시 Pull

Merge Request 가 성공되었다면 니가 이번에 작업한 휴양림 branch 작업은 완료된 샘이다. 

다시 master branch 로 이동하여 Remote 로부터 동기화를 받고 새로운 휴양림을 선택하여 다시 local branch 생성 후 작업을 시작하면 된다.

## 도메인 용어

- 휴양림 : Forest Resort. 약식으로 Resort 로 표현
- 숙소 : Room
  - 숲속의 집 : Hut
  - 휴양관 등 기타 숙박시설 : Condo
- 예약현황 : Booking