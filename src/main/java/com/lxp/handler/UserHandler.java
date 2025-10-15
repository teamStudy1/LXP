package com.lxp.handler;

import com.lxp.api.controller.UserController;

import java.sql.SQLException;
import java.util.Scanner;

public class UserHandler {
    private final Scanner scanner;
    private final UserController userController;

    public UserHandler(UserController userController) {
        this.scanner = new Scanner(System.in);
        this.userController = userController;
    }

    public boolean start() {
        System.out.println("=== User Management System ===");
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
        System.out.println("1. 사용자 조회");
        System.out.println("0. 뒤로가기");
        System.out.print("선택: ");
    }

    private boolean handleEnrollment(String command) throws SQLException {
        switch (command) {
            case "1":
                requestUserById();
                break;
            case "0":
                return false;
            default:
                System.out.println("잘못된 입력입니다.");
        }
        return true;
    }

    public void requestUserById() {
        System.out.print("조회할 사용자의 id를 입력해 주세요: ");
        long id = scanner.nextInt();
        System.out.println(userController.getUserById(id));
        scanner.nextLine();
    }

}
