package org.example;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

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
                for(int i = 0; i < articles.size(); i++) {
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

                Article article = new Article(id, title, body);

                articles.add(article);

                System.out.printf("%d번 글이 작성되었습니다.\n", id);
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
    String title;
    String body;

    public Article (int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }
}