package org.example;

import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);

        List<Article> articles = new ArrayList<>();

        // 게시글 번호 변수 만들기
        int lastArticleId = 0;

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

                System.out.println("번호 | 제목");
                for(int i = articles.size() - 1; i >= 0; i--) {
                    Article article = articles.get(i);
                    System.out.printf("%d | %s\n", article.id, article.title);
                }
            }

            else if(cmd.equals("article write")) {
                int id = lastArticleId + 1;
                System.out.printf("제목 : ");
                String title = sc.nextLine();
                System.out.printf("내용 : ");
                String body = sc.nextLine();

                lastArticleId = id;
                String regDate = Util.getNowDateStr();

                Article article = new Article(id, regDate, title, body);

                articles.add(article);

                System.out.printf("%d번 글이 작성되었습니다.\n", id);
            }

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

                System.out.printf("번호 : %d\n", foundArticle.id);
                System.out.printf("날짜 : %s\n", foundArticle.regDate);
                System.out.printf("제목 : %s\n", foundArticle.title);
                System.out.printf("내용 : %s\n", foundArticle.body);
            }

            // startsWith는 해당 문자열로 시작하는지 비교하는 메서드 (여기서는 article detail로 시작하냐?)
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
}

class Article {
    int id;
    String regDate;
    String title;
    String body;

    public Article (int id, String regDate, String title, String body) {
        this.id = id;
        this.regDate = regDate;
        this.title = title;
        this.body = body;
    }
}