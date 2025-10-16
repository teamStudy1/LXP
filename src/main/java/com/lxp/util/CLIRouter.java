package com.lxp.util;

import com.lxp.handler.CourseHandler;
import com.lxp.handler.EnrollmentHandler;
import com.lxp.handler.UserHandler;

import java.sql.SQLException;
import java.util.Scanner;

public class CLIRouter {
    private final Scanner scanner;
    private final EnrollmentHandler enrollmentHandler;
    private final UserHandler userHandler;
    private final CourseHandler courseHandler;

    public CLIRouter(EnrollmentHandler enrollmentHandler, CourseHandler courseHandler, UserHandler userHandler) {
        this.scanner = new Scanner(System.in);
        this.enrollmentHandler = enrollmentHandler;
        this.courseHandler = courseHandler;
        this.userHandler = userHandler;
    }

    public void start() {
        System.out.println("=== Course Management System ===");
        while (true) {
            printMenu();
            String command = scanner.nextLine().trim();
            try {
                if (!handleCommand(command)) {
                    break; // exit
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("1. 카테고리");
        System.out.println("2. 강좌");
        System.out.println("3. 사용자");
        System.out.println("4. 수강신청");
        System.out.println("0. 종료");
        System.out.print("선택: ");
    }

    private boolean handleCommand(String command) throws SQLException {
        switch (command) {
            case "3":
                userHandler.start();
                break;
            case "4":
                enrollmentHandler.start();
                break;
            case "2":
                courseHandler.start();
                return false;
            default:
                System.out.println("잘못된 입력입니다.");
        }
        return true;
    }
}
