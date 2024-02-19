package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.printf("명령어) ");
            String cmd = sc.nextLine();

            // 게시글 번호 변수 만들기
            int lastArticleId = 0;

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
                System.out.println("게시물이 없습니다.");
            }

            else if(cmd.equals("article write")) {
                int id = lastArticleId + 1;
                System.out.printf("제목 : ");
                String title = sc.nextLine();
                System.out.printf("내용 : ");
                String body = sc.nextLine();

                lastArticleId = id;

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
