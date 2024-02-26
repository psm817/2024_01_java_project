package org.example;

import org.example.controller.ArticleController;
import org.example.controller.MemberController;
import org.example.dto.Article;
import org.example.dto.Member;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class App {
    // 모든 메서드에서 접근 가능하도록 articles를 전역변수로 만들어준다.
    private List<Article> articles;
    private List<Member> members;

    public App() {
        articles = new ArrayList<>();
        members = new ArrayList<>();
    }
    public void start() {
        System.out.println("== 프로그램 시작 ==");

        // 테스트를 위한 데이터 게시물 3개 생성
        makeTestData();

        Scanner sc = new Scanner(System.in);

        MemberController memberController = new MemberController(sc, members);
        ArticleController articleController = new ArticleController();

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

            else if(cmd.equals("member join")) {
                memberController.doJoin();
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

            else if(cmd.startsWith("article list")) {
                if(articles.size() == 0) {
                    System.out.println("게시물이 없습니다.");
                    continue;
                }

                String searchKeyword = cmd.substring("article list".length()).trim();

                // articles와 연결된 forListArticles
                List<Article> forListArticles = articles;

                // 검색어를 담은 리스트를 새로 생성
                if(searchKeyword.length() > 0) {
                    forListArticles = new ArrayList<>();

                    // 반복문을 통해 제목에 검색어가 있다면 리스트에 저장
                    for(Article article : articles) {
                        if(article.title.contains(searchKeyword)) {
                            forListArticles.add(article);
                        }
                    }
                }

                if (forListArticles.size() == 0) {
                    System.out.println("검색결과가 존재하지 않습니다.");
                    continue;
                }

                System.out.println("번호 | 조회수 | 제목");
                for(int i = forListArticles.size() - 1; i >= 0; i--) {
                    Article article = forListArticles.get(i);
                    // %4d는 4칸정도의 공간을 가진다는 뜻으로 위의 출력문 번호 | 와 칸수를 맞추기 위해서 작성
                    System.out.printf("%4d | %4d | %s\n", article.id, article.hit, article.title);
                }
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

                // getArticleById 메서드를 통해 id를 준다.
                Article foundArticle = getArticleById(id);

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

                // getArticleById 메서드를 통해 id를 준다.
                Article foundArticle = getArticleById(id);

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
                int foundIndex = getArticleIndexById(id);

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

    private int getArticleIndexById(int id) {
        int i = 0;
        for(Article article : articles) {
            if(article.id == id) {
                return i;
            }
            i++;
        }
        return -1;
    }

    // 공통된 기능을 하나의 메서드로 집합
    private Article getArticleById(int id) {
        int index = getArticleIndexById(id);

        if(index != -1) {
            return articles.get(index);
        }

        return null;
    }

    private void makeTestData() {
        System.out.println("테스트를 위한 데이터를 생성합니다.");

        articles.add(new Article(1, Util.getNowDateStr(), "제목1", "내용1", 10));
        articles.add(new Article(2, Util.getNowDateStr(), "제목2", "내용2", 32));
        articles.add(new Article(3, Util.getNowDateStr(), "제목3", "내용3", 103));
    }
}

