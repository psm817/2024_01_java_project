package org.example;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    // 모든 메서드에서 접근 가능하도록 articles를 전역변수로 만들어준다.
    private static List<Article> articles;
    static {
        articles = new ArrayList<>();
    }
    public static void main(String[] args) {
        System.out.println("== 프로그램 시작 ==");

        // 테스트를 위한 데이터 게시물 3개 생성
        makeTestData();

        Scanner sc = new Scanner(System.in);

        // 게시글 번호 변수 만들기
//        int lastArticleId = 0;

        while(true) {
            System.out.printf("명령어) ");
            String cmd = sc.nextLine();

            // 양옆에 있는 공백을 없애준다.
            // article list 뒤에 공백을 넣어도 게시물이 없습니다. 라고 출력된다.
            cmd = cmd.trim();

            // 명령어를 아무것도 적지 않아도 계속 반복된다.
            if(cmd.length() == 0) {
                continue;
            }

            if(cmd.equals("system exit")) {
                break;
            }

            else if(cmd.equals("article list")) {
                if(articles.size() == 0) {
                    System.out.println("게시물이 없습니다.");
                    continue;
                }

                System.out.println("번호 | 조회수 | 제목");
                for(int i = articles.size() - 1; i >= 0; i--) {
                    Article article = articles.get(i);
                    // %4d는 4칸정도의 공간을 가진다는 뜻으로 위의 출력문 번호 | 와 칸수를 맞추기 위해서 작성
                    System.out.printf("%4d | %4d | %s\n", article.id, article.hit, article.title);
                }
            }

            else if(cmd.equals("article write")) {
                int id = articles.size() + 1;
                System.out.printf("제목 : ");
                String title = sc.nextLine();
                System.out.printf("내용 : ");
                String body = sc.nextLine();

                String regDate = Util.getNowDateStr();

                Article article = new Article(id, regDate, title, body);

                articles.add(article);

                System.out.printf("%d번 글이 작성되었습니다.\n", id);
            }

            // ------- 상세보기 -------
            // startsWith는 해당 문자열로 시작하는지 비교하는 메서드 (여기서는 article detail로 시작하냐?)
            else if(cmd.startsWith("article detail ")) {
                // 여기서 split은 공백을 기준으로 쪼갠다는 의미
                String[] cmdBits = cmd.split(" ");
//                cmdBits[0];     // article
//                cmdBits[1];     // detail
                // Integer.parseInt는 "숫자" 를 정수로 바꿔준다.
                int id = Integer.parseInt(cmdBits[2]);     // 숫자

                // 보고싶은 게시물의 정보를 담아두는 foundArticle 변수 생성
                Article foundArticle = null;

                for(int i = 0; i < articles.size(); i++) {
                    Article article = articles.get(i);

                    // article의 id와 입력한 id가 같다면 해당 게시물 정보를 foundArticle에 넣어준다.
                    if(article.id == id) {
                        foundArticle = article;
                        break;
                    }
                }

                if(foundArticle == null) {
                    System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
                    continue;
                }

                // 조회수 늘리기
                foundArticle.increaseHit();

                System.out.printf("번호 : %d\n", foundArticle.id);
                System.out.printf("최초등록날짜 : %s\n", foundArticle.regDate);
                System.out.printf("제목 : %s\n", foundArticle.title);
                System.out.printf("내용 : %s\n", foundArticle.body);
                System.out.printf("조회수 : %d\n", foundArticle.hit);
            }

            // ------- 수정 -------
            // startsWith는 해당 문자열로 시작하는지 비교하는 메서드 (여기서는 article modify로 시작하냐?)
            else if(cmd.startsWith("article modify ")) {
                // 여기서 split은 공백을 기준으로 쪼갠다는 의미
                String[] cmdBits = cmd.split(" ");
//                cmdBits[0];     // article
//                cmdBits[1];     // detail
                // Integer.parseInt는 "숫자" 를 정수로 바꿔준다.
                int id = Integer.parseInt(cmdBits[2]);     // 숫자

                // 보고싶은 게시물의 정보를 담아두는 foundArticle 변수 생성
                Article foundArticle = null;

                for(int i = 0; i < articles.size(); i++) {
                    Article article = articles.get(i);

                    // article의 id와 입력한 id가 같다면 해당 게시물 정보를 foundArticle에 넣어준다.
                    if(article.id == id) {
                        foundArticle = article;
                        break;
                    }
                }

                if(foundArticle == null) {
                    System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
                    continue;
                }

                System.out.printf("제목 : ");
                String title = sc.nextLine();
                System.out.printf("내용 : ");
                String body = sc.nextLine();

                foundArticle.title = title;
                foundArticle.body = body;

                System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
            }

            // ------- 삭제 -------
            // startsWith는 해당 문자열로 시작하는지 비교하는 메서드 (여기서는 article delete로 시작하냐?)
            else if(cmd.startsWith("article delete ")) {
                // 여기서 split은 공백을 기준으로 쪼갠다는 의미
                String[] cmdBits = cmd.split(" ");
//                cmdBits[0];     // article
//                cmdBits[1];     // detail
                // Integer.parseInt는 "숫자" 를 정수로 바꿔준다.
                int id = Integer.parseInt(cmdBits[2]);     // 숫자

                // 삭제 되었다가 다시 write를 썼을 때 index는 빈 공간부터 넣어지고 id는 계속해서 증가하기 때문에
                // index를 찾는 변수를 만들어줘야한다.
                // index가 존재하지 않을 때를 대비하여 기본 변수 값을 -1로 세팅한다.
                int foundIndex = -1;

                for(int i = 0; i < articles.size(); i++) {
                    Article article = articles.get(i);

                    // article의 id와 입력한 id가 같다면 해당 숫자를 foundIndex에 넣어준다.
                    if(article.id == id) {
                        foundIndex = i;
                        break;
                    }
                }

                if(foundIndex == -1) {
                    System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
                    continue;
                }

                articles.remove(foundIndex);
                System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
            }

            else {
                System.out.printf("%s(은)는 존재하지 않는 명령어입니다.\n", cmd);
            }
        }

        sc.close();

        System.out.println("== 프로그램 끝 ==");
    }
    private static void makeTestData() {
        System.out.println("테스트를 위한 데이터를 생성합니다.");

        articles.add(new Article(1, Util.getNowDateStr(), "제목1", "내용1", 10));
        articles.add(new Article(2, Util.getNowDateStr(), "제목2", "내용2", 32));
        articles.add(new Article(3, Util.getNowDateStr(), "제목3", "내용3", 103));
    }
}

class Article {
    int id;
    String regDate;
    String title;
    String body;
    int hit;

    // 테스트 데이터는 조회수를 강제로 포함하고싶으면 Article 생성자를 오버로딩한다.
    public Article (int id, String regDate, String title, String body, int hit) {
        this.id = id;
        this.regDate = regDate;
        this.title = title;
        this.body = body;
        this.hit = hit;
    }
    // 복잡하게 쓰는거보다 this 메서드를 이용해서 간단하게 넘겨준다.
    public Article (int id, String regDate, String title, String body) {
        this(id, regDate, title, body, 0);
    }
    // 메서드에 privite를 붙히면 외부 클래스에서 사용할 수 없다.
    // 메서드에 public를 붙히면 외내부 전부 사용할 수 있다.
    public void increaseHit() {
        hit++;
    }
}