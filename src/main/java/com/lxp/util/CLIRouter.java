package com.lxp.util;

import com.lxp.handler.CategoryHandler;
import com.lxp.handler.CourseHandler;
import com.lxp.handler.UserHandler;
import java.sql.SQLException;
import java.util.Scanner;

public class CLIRouter {
    private final Scanner scanner;
    private final UserHandler userHandler;
    private final CourseHandler courseHandler;
    private final CategoryHandler categoryHandler;

    public CLIRouter(
            CourseHandler courseHandler, UserHandler userHandler, CategoryHandler categoryHandler) {
        this.scanner = new Scanner(System.in);
        this.courseHandler = courseHandler;
        this.userHandler = userHandler;
        this.categoryHandler = categoryHandler;
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
        System.out.println("0. 종료");
        System.out.print("선택: ");
    }

    private boolean handleCommand(String command) throws SQLException {
        switch (command) {
            case "1":
                categoryHandler.start();
                break;
            case "3":
                userHandler.start();
                break;
            case "2":
                courseHandler.start();
                break;
            case "0":
                return false;
            default:
                System.out.println("잘못된 입력입니다.");
        }
        return true;
    }
}
