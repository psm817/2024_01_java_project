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
        ArticleController articleController = new ArticleController(sc, articles);

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
               articleController.doWrite();
            }

            else if(cmd.startsWith("article list")) {
                articleController.showList(cmd);
            }

            // ------- 상세보기 -------
            // startsWith는 해당 문자열로 시작하는지 비교하는 메서드 (여기서는 article detail로 시작하냐?)
            else if(cmd.startsWith("article detail ")) {
                articleController.showDetail(cmd);
            }

            // ------- 수정 -------
            // startsWith는 해당 문자열로 시작하는지 비교하는 메서드 (여기서는 article modify로 시작하냐?)
            else if(cmd.startsWith("article modify ")) {
                articleController.doModify(cmd);
            }

            // ------- 삭제 -------
            // startsWith는 해당 문자열로 시작하는지 비교하는 메서드 (여기서는 article delete로 시작하냐?)
            else if(cmd.startsWith("article delete ")) {
                articleController.doDelete(cmd);
            }

            else {
                System.out.printf("%s(은)는 존재하지 않는 명령어입니다.\n", cmd);
            }
        }

        sc.close();

        System.out.println("== 프로그램 끝 ==");
    }



    private void makeTestData() {
        System.out.println("테스트를 위한 데이터를 생성합니다.");

        articles.add(new Article(1, Util.getNowDateStr(), "제목1", "내용1", 10));
        articles.add(new Article(2, Util.getNowDateStr(), "제목2", "내용2", 32));
        articles.add(new Article(3, Util.getNowDateStr(), "제목3", "내용3", 103));
    }
}

