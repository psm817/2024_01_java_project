package org.example;

import org.example.controller.ArticleController;
import org.example.controller.Controller;
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
    public void start() {
        System.out.println("== 프로그램 시작 ==");

        Scanner sc = new Scanner(System.in);

        MemberController memberController = new MemberController(sc);
        ArticleController articleController = new ArticleController(sc);

        // 테스트를 위한 데이터 게시물 3개 생성
        articleController.makeTestData();

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

            // 여기서 split은 공백을 기준으로 쪼갠다는 의미
            String[] cmdBits = cmd.split(" ");      // article xxx xxx

            if(cmdBits.length == 1) {
                System.out.printf("%s(은)는 존재하지 않는 명령어입니다.\n", cmd);
                continue;
            }
            String controllerName = cmdBits[0];             // 0번째 article이나 member을 뽑아냄
            String actionMethodName = cmdBits[1];           // 1번째 각 명령어를 뽑아냄 (join, write, detail, modify, list, delete)

            Controller controller = null;

            if(controllerName.equals("article")) {
                controller = articleController;
            }

            else if(controllerName.equals("member")) {
                controller = memberController;
            }

            else {
                System.out.printf("%s(은)는 존재하지 않는 명령어입니다.\n", cmd);
                continue;
            }

            controller.doAction(cmd, actionMethodName);
        }

        sc.close();

        System.out.println("== 프로그램 끝 ==");
    }
}

