# 프로젝트 : 혼술 in the wood

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

## Git-Flow

너는 아래와 같이 작업하여 수정된 내용을 서버에 반영해야 한다.

### Pull

항상 원격지의 **master** branch 에서 최신 변경내용을 받아 동기화 해라.

너의 local branch 를 master 로 유지하고 프로젝트 우클릭 > Team > Pull 명령으로 동기화한다.

### Branch

너는 작업 전에 항상 local branch 를 생성해야 한다.

프로젝트 우클릭 > Team > Switch To > New Branch 명령으로 새로운 Branch 를 생성한다.

Branch 명은 너가 지금 작업하려하는 휴양림 ID 로 생성하면 된다.

```
예) branch name : R018
```

## 도메인 용어

- 휴양림 : Forest Resort. 약식으로 Resort 로 표현
- 숙소 : Room
  - 숲속의 집 : Hut
  - 휴양관 등 기타 숙박시설 : Condo
- 예약현황 : Booking