package com.lxp.util;

import com.lxp.handler.CourseHandler;
import com.lxp.handler.EnrollmentHandler;
import com.lxp.handler.UserHandler;

import java.util.Scanner;

public class CLIRouter {
    private final Scanner scanner;
    private final CourseHandler courseHandler;
    private final EnrollmentHandler enrollmentHandler;
    private final UserHandler userHandler;

    public CLIRouter(CourseHandler courseHandler, EnrollmentHandler enrollmentHandler, UserHandler userHandler) {
        this.scanner = new Scanner(System.in);
        this.courseHandler = courseHandler;
        this.enrollmentHandler = enrollmentHandler;
        this.userHandler = userHandler;
    }

    public void start() {
        System.out.println("=== LXP Course Management System ===");
        while (true) {
            printMenu();
            String command = scanner.nextLine().trim();
            if (command.equals("0")) {
                break;
            }
            handleCommand(command);
        }
    }

    private void printMenu() {
        System.out.println("\n[메인 메뉴]");
        System.out.println("1. 카테고리 관리");
        System.out.println("2. 강좌 관리");
        System.out.println("3. 사용자 관리");
        System.out.println("4. 수강신청 관리");
        System.out.println("0. 종료");
        System.out.print("선택: ");
    }

    private void handleCommand(String command) {
        switch (command) {
            case "2":
                courseHandler.start();
                break;
            case "1":
                System.out.println("해당 기능은 아직 구현되지 않았습니다.");
                break;
            case "3":
                userHandler.start();
                break;
            case "4":
                System.out.println("해당 기능은 아직 구현되지 않았습니다.");
                break;
            default:
                System.out.println("잘못된 입력입니다. 메뉴에 있는 번호를 입력해주세요.");
                break;
        }
    }
}