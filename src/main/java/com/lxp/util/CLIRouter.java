package com.lxp.util;

import com.lxp.api.controller.EnrollmentController;

import java.sql.SQLException;
import java.util.Scanner;

public class CLIRouter {
    private final Scanner scanner;
    private final EnrollmentController enrollmentController;

    public CLIRouter(EnrollmentController enrollmentController) {
        this.scanner = new Scanner(System.in);
        this.enrollmentController = enrollmentController;
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
        System.out.println("1. 카테고리 강좌 조회");
        System.out.println("2. 모든 강좌 목록");
        System.out.println("3. 단일 강좌 조회");
        System.out.println("4. 강좌 등록");
        System.out.println("5. 수강신청");
        System.out.println("0. 종료");
        System.out.print("선택: ");
    }

    private boolean handleCommand(String command) throws SQLException {
        switch (command) {
            case "5":
                handleEnrollment();
                break;
            case "0":
                return false;
            default:
                System.out.println("잘못된 입력입니다.");
        }
        return true;
    }


    private void handleEnrollment() throws SQLException {
        System.out.print("학생 ID: ");
        Long studentId = Long.parseLong(scanner.nextLine());
        System.out.print("과목 ID: ");
        Long courseId = Long.parseLong(scanner.nextLine());

        enrollmentController.enroll(studentId, courseId);
        System.out.println("✅ 수강 신청이 완료되었습니다.");
    }
}
