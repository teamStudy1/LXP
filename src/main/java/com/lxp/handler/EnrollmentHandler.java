package com.lxp.handler;

import java.sql.SQLException;
import java.util.Scanner;

public class EnrollmentHandler {
    private final Scanner scanner;
    private final com.lxp.api.controller.EnrollmentController enrollmentController;

    public EnrollmentHandler(com.lxp.api.controller.EnrollmentController enrollmentController) {
        this.scanner = new Scanner(System.in);
        this.enrollmentController = enrollmentController;
    }

    public boolean start() {
        System.out.println("=== Enrollment Management System ===");
        while (true) {
            printMenu();
            String command = scanner.nextLine().trim();
            try {
                if (!handleEnrollment(command)) {
                    break; // exit
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        return false;
    }

    private void printMenu() {
        System.out.println("1. 수강신청 조회");
        System.out.println("2. 수강신청 생성");
        System.out.println("3. 수강신청 철회");
        System.out.println("0. 뒤로가기");
        System.out.print("선택: ");
    }

    private boolean handleEnrollment(String command) throws SQLException {
        switch (command) {
            case "1":
                requestFindAllEnrollment();
                break;
            case "0":
                return false;
            default:
                System.out.println("잘못된 입력입니다.");
        }
        return true;
    }

    public void requestFindAllEnrollment() throws SQLException {

        //        enrollmentController.findAll();
        System.out.println("수강 신청 조회가 완료되었습니다.");
    }

    public void requestSaveEnrollment() throws SQLException {
        System.out.print("학생 ID: ");
        Long studentId = Long.parseLong(scanner.nextLine());
        System.out.print("과목 ID: ");
        Long courseId = Long.parseLong(scanner.nextLine());

        enrollmentController.enroll(studentId, courseId);
        System.out.println("수강 신청이 완료되었습니다.");
    }
}
