package com.lxp.handler;

import com.lxp.api.controller.CourseController;
import java.sql.SQLException;
import java.util.Scanner;

public class CourseHandler {
    private final Scanner scanner;
    private final CourseController courseController;

    public CourseHandler(CourseController courseController) {
        this.scanner = new Scanner(System.in);
        this.courseController = courseController;
    }

    public boolean start() {
        System.out.println("=== Course Management System ===");
        while (true) {
            printMenu();
            String command = scanner.nextLine().trim();
            try {
                if (!handleCourse(command)) {
                    break; // exit
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        return false;
    }

    private void printMenu() {
        System.out.println("1. 강좌 목록");
        System.out.println("2. 강좌 검색");
        System.out.println("3. 강좌 상세 조회");
        System.out.println("4. 강좌 생성");
        System.out.println("5. 강좌 수정");
        System.out.println("0. 뒤로가기");
        System.out.print("선택: ");
    }

    private boolean handleCourse(String command) throws SQLException {
        switch (command) {
            case "1":
                break;
            case "0":
                return false;
            default:
                System.out.println("잘못된 입력입니다.");
        }
        return true;
    }
}
