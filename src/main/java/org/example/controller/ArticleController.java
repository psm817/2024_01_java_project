package org.example.controller;

import org.example.container.Container;
import org.example.dto.Article;
import org.example.dto.Member;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller{
    private Scanner sc;
    private List<Article> articles;
    private String cmd;
    private String actionMethodName;

    public ArticleController(Scanner sc) {
        this.sc = sc;

        // Container를 통해 접근한다.
        articles = Container.articleDao.articles;
    }

    public void makeTestData() {
        System.out.println("테스트를 위한 게시물 데이터를 생성합니다.");

        articles.add(new Article(1, Util.getNowDateStr(), 1, "제목1", "내용1", 10));
        articles.add(new Article(2, Util.getNowDateStr(), 2, "제목2", "내용2", 32));
        articles.add(new Article(3, Util.getNowDateStr(), 2, "제목3", "내용3", 103));
    }

    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;
        this.actionMethodName = actionMethodName;

        switch(actionMethodName) {
            case "write":
                doWrite();
                break;
            case "list":
                showList();
                break;
            case "detail":
                showDetail();
                break;
            case "modify":
                doModify();
                break;
            case "delete":
                doDelete();
                break;
            default:
                System.out.printf("%s(은)는 존재하지 않는 명령어입니다.\n", cmd);
                break;
        }
    }

    public void doWrite() {
        int id = articles.size() + 1;

        System.out.printf("제목 : ");
        String title = sc.nextLine();

        System.out.printf("내용 : ");
        String body = sc.nextLine();

        String regDate = Util.getNowDateStr();

        Article article = new Article(id, regDate, 1, title, body);

        articles.add(article);

        System.out.printf("%d번 글이 작성되었습니다.\n", id);
    }

    public void showList() {
        if(articles.size() == 0) {
            System.out.println("게시물이 없습니다.");
            // void 여도 반환 타입없이 return만 쓰면 해당 메서드를 끝내겠다는 뜻이고 continue와 같은 역할이다.
            return;
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
            return;
        }

        System.out.println("번호 |    작성자 | 조회수 | 제목");

        for(int i = forListArticles.size() - 1; i >= 0; i--) {
            Article article = forListArticles.get(i);

            String writerName = null;

            List<Member> members = Container.memberDao.members;

            // 컨테이너에 들어간 멤버를 반복문을 통해 id 비교를 해서 작성자 저장
            for(Member member : members) {
                if(article.memberId == member.id) {
                    writerName = member.name;
                    break;
                }
            }

            // %4d는 4칸정도의 공간을 가진다는 뜻으로 위의 출력문 번호 | 와 칸수를 맞추기 위해서 작성
            System.out.printf("%4d | %6s | %6d | %s\n", article.id, writerName, article.hit, article.title);
        }
    }

    public void showDetail() {
        // 여기서 split은 공백을 기준으로 쪼갠다는 의미
        String[] cmdBits = cmd.split(" ");
//                cmdBits[0];     // article
//                cmdBits[1];     // detail

        // cmdBits에서 숫자를 입력안했을 때 디버깅 (article detail까지만 명령어를 입력했을 때)
        if(cmdBits.length <= 2) {
            System.out.println("게시물 번호를 입력해주세요");
            return;
        }

        // Integer.parseInt는 "숫자" 를 정수로 바꿔준다.
        int id = Integer.parseInt(cmdBits[2]);     // 숫자

        // getArticleById 메서드를 통해 id를 준다.
        Article foundArticle = getArticleById(id);

        if(foundArticle == null) {
            System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
            return;
        }

        // 조회수 늘리기
        foundArticle.increaseHit();

        System.out.printf("번호 : %d\n", foundArticle.id);
        System.out.printf("최초등록날짜 : %s\n", foundArticle.regDate);
        System.out.printf("제목 : %s\n", foundArticle.title);
        System.out.printf("내용 : %s\n", foundArticle.body);
        System.out.printf("조회수 : %d\n", foundArticle.hit);
    }

    public void doModify() {
        // 여기서 split은 공백을 기준으로 쪼갠다는 의미
        String[] cmdBits = cmd.split(" ");
//                cmdBits[0];     // article
//                cmdBits[1];     // detail

        // cmdBits에서 숫자를 입력안했을 때 디버깅 (article modify까지만 명령어를 입력했을 때)
        if(cmdBits.length <= 2) {
            System.out.println("게시물 번호를 입력해주세요");
            return;
        }

        // Integer.parseInt는 "숫자" 를 정수로 바꿔준다.
        int id = Integer.parseInt(cmdBits[2]);     // 숫자

        // getArticleById 메서드를 통해 id를 준다.
        Article foundArticle = getArticleById(id);

        if(foundArticle == null) {
            System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
            return;
        }

        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();

        foundArticle.title = title;
        foundArticle.body = body;

        System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
    }

    public void doDelete() {
        // 여기서 split은 공백을 기준으로 쪼갠다는 의미
        String[] cmdBits = cmd.split(" ");
//                cmdBits[0];     // article
//                cmdBits[1];     // detail

        // cmdBits에서 숫자를 입력안했을 때 디버깅 (article delete까지만 명령어를 입력했을 때)
        if(cmdBits.length <= 2) {
            System.out.println("게시물 번호를 입력해주세요");
            return;
        }

        // Integer.parseInt는 "숫자" 를 정수로 바꿔준다.
        int id = Integer.parseInt(cmdBits[2]);     // 숫자

        // 삭제 되었다가 다시 write를 썼을 때 index는 빈 공간부터 넣어지고 id는 계속해서 증가하기 때문에
        // index를 찾는 변수를 만들어줘야한다.
        // index가 존재하지 않을 때를 대비하여 기본 변수 값을 -1로 세팅한다.
        int foundIndex = getArticleIndexById(id);

        if(foundIndex == -1) {
            System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
            return;
        }

        articles.remove(foundIndex);
        System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
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
}