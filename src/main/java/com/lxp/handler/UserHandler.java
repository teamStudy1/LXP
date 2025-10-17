package com.lxp.handler;

import com.lxp.api.controller.UserController;
import com.lxp.api.dto.CreateUserRequest;
import com.lxp.api.dto.UpdateUserRoleRequest;

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
        System.out.println("2. 사용자 권한 조회");
        System.out.println("3. 회원 가입");
        System.out.println("4. 사용자 권한 변경");
        System.out.println("0. 뒤로가기");
        System.out.print("선택: ");
    }

    private boolean handleEnrollment(String command) throws SQLException {
        switch (command) {
            case "1":
                requestUserById();
                break;
            case "2":
                requestUserRoleById();
                break;
            case "3":
                requestSaveUser();
                break;
            case "4":
                requestUpdateUserRole();
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
        try {
            System.out.println(userController.getUserById(id));
        } catch (Exception e) {
            System.out.println("사용자 조회에 실패했습니다. " + e.getMessage() + "\n");
        }
        scanner.nextLine();
    }

    public void requestUserRoleById() {
        System.out.print("조회할 사용자의 id를 입력해 주세요: ");
        long id = scanner.nextInt();
        try {
            System.out.println(userController.getUserRoleById(id));
        } catch (Exception e) {
            System.out.println("사용자 권한 조회에 실패했습니다. " + e.getMessage() + "\n");
        }
        scanner.nextLine();
    }

    public void requestSaveUser() {
        System.out.print("이메일을 입력해 주세요: ");
        String email = scanner.nextLine();
        System.out.print("비밀번호를 입력해 주세요: ");
        String password = scanner.nextLine();
        System.out.print("이름을 입력해 주세요: ");
        String name = scanner.nextLine();
        System.out.print("자기 소개를 입력해 주세요: ");
        String introduction = scanner.nextLine();
        introduction = introduction != null || introduction.isEmpty() ? null : introduction;
        System.out.print("이력을 입력해 주세요: ");
        String resume = scanner.nextLine();
        resume = resume != null || resume.isEmpty() ? null : resume;

        CreateUserRequest request = new CreateUserRequest(
                email, password, name, introduction, resume
        );

        try {
            System.out.println("회원가입에 성공했습니다. 사용자 id: " + userController.saveUser(request));
        } catch (Exception e) {
            System.out.println("회원가입에 실패했습니다. " + e.getMessage());
        }
    }

    public void requestUpdateUserRole() {
        System.out.print("변경할 사용자의 id를 입력해 주세요: ");
        long userId = scanner.nextLong();
        scanner.nextLine();
        System.out.print("변경할 권한을 입력해 주세요 (STUDENT, INSTRUCTOR): ");
        String userRole = scanner.nextLine();

        UpdateUserRoleRequest request = new UpdateUserRoleRequest(userId, userRole);
        try {
            userController.updateUserRole(request);
        } catch (SQLException e) {
            System.out.println("권한 변경에 실패했습니다. " + e.getMessage());
        }
    }
}
