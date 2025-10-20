package com.lxp.handler;

import com.lxp.category.web.CategoryController;
import com.lxp.category.web.dto.response.CategoryResponse;
import com.lxp.formatter.CategoryFormatter;
import com.lxp.user.web.UserController;
import java.util.List;
import java.util.Scanner;

/*
 * 손님의 주문을 직접 받는 '서버' 클래스
 * CLI 화면을 보여주고 입력받아 홀 매니저 (Controller)에게 전달
 * */

public class CategoryHandler {
    private final Scanner scanner;
    private final CategoryController categoryController;
    private final UserController userController;

    public CategoryHandler(CategoryController categoryController, UserController userController) {
        this.scanner = new Scanner(System.in);
        this.categoryController = categoryController;
        this.userController = userController;
    }

    public void start() {
        System.out.println("\n=== 카테고리 관리시스템에 접속했습니다");

        try {
            //            System.out.println("관리자 ID를 입력하세요 : ");
            //            long userId = Long.parseLong(scanner.nextLine());
            //            UserRole role = userController.getUserRoleById(userId);
            //
            //            if (role != UserRole.ADMIN) {
            //                System.out.println("접근 권한이 없습니다. 관리자만 접근 할 수 있습니다 ");
            //                return; //
            //            }
            //            System.out.println("관리자 인증이 완료 되었습니다. 메뉴를 선택해주세요. ");
            while (true) {
                printMenu();
                String command = scanner.nextLine().trim();
                if (!handleComand(command)) {
                    System.out.println("메인 메뉴로 돌아갑니다. ");
                    break;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("오류 : ID는 숫자로 입력해야 합니다");
        } catch (Exception e) {
            System.out.println("오류가 발생했습니다. " + e.getMessage());
        }
    }

    /*
     * 메뉴판 출력
     * */
    private void printMenu() {
        System.out.println("\n1. 전체 카테고리 보기 ");
        System.out.println("2. 카테고리 생성");
        System.out.println("3. 카테고리 이름 수정");
        System.out.println("4. 카테고리 이동");
        //        System.out.println("5. 카테고리 삭제");
        System.out.println("6. 카테고리 이름으로 검색"); // 추가기능
        System.out.println("0. 뒤로 가기");
        System.out.println("선택: ");
    }

    private boolean handleComand(String command) throws Exception {
        switch (command) {
            case "1":
                requestCategoryTree();
                break;
            case "2":
                requestCreateCategory();
                break;
            case "3":
                requestUpdateCategoryName();
                break;
            case "4":
                requestMoveCategory();
                break;
                //            case "5":
                //                requestDeleteCategory();
                //                break;
            case "6":
                requestSearchCategory();
                break;
            case "0":
                return false; // 루프 종료
            default:
                System.out.println("잘못된 입력입니다. 다시 선택해주세요");
                break;
        }
        return true; // 루프 계속
    }

    private void requestCategoryTree() throws Exception {
        System.out.println("\n--- 전체 카테고리 목록 ---");
        List<CategoryResponse> categoryTree = categoryController.getCategoryTree();
        if (categoryTree.isEmpty()) {
            System.out.println("표시할 카테고리가 없습니다.");
        } else {
            System.out.println(CategoryFormatter.formatCategories(categoryTree));
        }
    }

    private void requestCreateCategory() throws Exception {
        System.out.println("--- 카테고리 기본 정보 입력 ---");
        System.out.println("형식: 카테고리 이름, 부모 카테고리 ID(최상위는 x 입력)");
        System.out.println("입력: ");
        String input = scanner.nextLine();
        String[] parts = input.split(",", -1);

        categoryController.createCategory(parts[0].trim(), parts[1].equals("x") ? null : Long.parseLong(parts[1].trim()));
        System.out.println("성공");
    }

    private void requestUpdateCategoryName() throws Exception {
        System.out.println("--- 카테고리 이름 수정 ---");
        System.out.println("형식 : 수정할 카테고리 ID,수정할 이름");
        String input = scanner.nextLine();
        String[] parts = input.split(",", -1);

        categoryController.updateCategoryName(Long.parseLong(parts[0].trim()), parts[1].trim());
        System.out.println("성공 : 카테고리 이름이 수정되었습니다.");
    }

    private void requestMoveCategory() throws Exception {
        System.out.println("--- 카테고리 이동 ---");
        System.out.println("형식 : 이동할 카테고리 ID,부모 카테고리 ID(상위이면 x)");
        String input = scanner.nextLine();
        String[] parts = input.split(",", -1);

        Long newParentId = parts[1].trim().equals("x") ? null : Long.parseLong(parts[1].trim());

        categoryController.moveCategory(Long.parseLong(parts[0].trim()), newParentId);
        System.out.println("성공: 카테고리가 이동되었습니다.");
    }

    //    private void requestDeleteCategory() throws Exception {
    //        System.out.println("삭제할 카테고리 ID를 입력하세요 :");
    //        long categoryId = Long.parseLong(scanner.nextLine());
    //
    //        categoryController.deleteCategory(categoryId);
    //        System.out.println("성공: 카테고리가 삭제되었습니다. ");
    //    }
    //
    private void requestSearchCategory() throws Exception {
        System.out.println("검색할 카테고리 이름을 입력하세요 : ");
        String name = scanner.nextLine();

        List<CategoryResponse> results = categoryController.searchCategoryByName(name);

        System.out.println("\n--- ' " + name + " ' 검색결과 ---");
        if (results.isEmpty()) {
            System.out.println("검색된 카테고리가 없습니다.");
        } else {
            // 검색 결과는 계층 구조가 아닌 단순 목록으로 보여줍니다.
//            for (CategoryResponse category : results) {
//                System.out.println(
//                        "ID: " + category.id() + ", 이름: " + category.name() + ", 깊이:" + category.depth());
//            }
            System.out.println(CategoryFormatter.formatSearchCategory(results));
        }
    }
}
