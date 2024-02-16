package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("== 프로그램 시작 ==");

        Scanner sc = new Scanner(System.in);
        System.out.printf("명령어) ");
        String cmd = sc.nextLine();
        System.out.printf("입력된 명령어 : %s\n", cmd);

        System.out.println("소스코드 수정");

        sc.close();

        System.out.println("== 프로그램 끝 ==");
    }
}
