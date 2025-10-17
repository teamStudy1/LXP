package com.lxp.handler;

import com.lxp.api.controller.CourseController;
import com.lxp.api.dto.CourseView;
import com.lxp.domain.course.Course;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CourseHandler {
    private final Scanner scanner;
    private final CourseController courseController;

    public CourseHandler(CourseController courseController) {
        this.scanner = new Scanner(System.in);
        this.courseController = courseController;
    }

    public void start() {
        System.out.println("=== Course Management System ===");
        while (true) {
            printMenu();
            String command = scanner.nextLine().trim();
            try {
                if (!handleCourse(command)) {
                    break;
                }
            } catch (Exception e) {
                System.err.println("오류가 발생했습니다: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("\n1. 강좌 목록 | 2. 강좌 검색 | 3. 강좌 상세 조회 | 4. 강좌 생성 | 5. 강좌 수정 | 0. 뒤로가기");
        System.out.print("선택: ");
    }

    private boolean handleCourse(String command) throws SQLException {
        switch (command) {
            case "1": listAllCourses(); break;
            case "2": searchCourse(); break;
            case "3": getCourseDetails(); break;
            case "4": createCourse(); break;
            case "5": updateCourse(); break;
            case "0":
                System.out.println("메인 메뉴로 돌아갑니다.");
                return false;
            default:
                System.out.println("잘못된 입력입니다.");
                break;
        }
        return true;
    }

    private void listAllCourses() throws SQLException {
        System.out.println("\n--- 1. 전체 강좌 목록 ---");
        List<CourseView> courses = courseController.getAllCourses();
        printCourseList(courses);
    }

    private void searchCourse() throws SQLException {
        System.out.println("\n--- 2. 강좌 검색 ---");
        System.out.print("검색할 키워드를 입력하세요: ");
        String keyword = scanner.nextLine();
        List<CourseView> courses = courseController.searchCoursesByTitle(keyword);
        printCourseList(courses);
    }

    private void getCourseDetails() {
        System.out.println("\n--- 3. 강좌 상세 조회 ---");
        System.out.print("조회할 강좌 ID를 입력하세요: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            CourseView course = courseController.getCourseById(id);
            System.out.println("[상세 정보]");
            System.out.println(" - ID: " + course.id());
            System.out.println(" - 제목: " + course.title());
            System.out.println(" - 강사 ID: " + course.instructorId());
            System.out.println(" - 생성일: " + course.createdAt());
        } catch (NumberFormatException e) {
            System.out.println("숫자 ID를 입력해야 합니다.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void createCourse() {
        System.out.println("\n--- 4. 강좌 생성 ---");
        try {
            System.out.print("새 강좌 제목: ");
            String title = scanner.nextLine();
            System.out.print("강사 ID: ");
            long instructorId = Long.parseLong(scanner.nextLine());
            Course createdCourse = courseController.createCourse(title, instructorId);
            System.out.println("강좌가 성공적으로 생성되었습니다! (ID: " + createdCourse.getId() + ")");
        } catch (NumberFormatException e) {
            System.out.println("강사 ID는 숫자로 입력해야 합니다.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateCourse() {
        System.out.println("\n--- 5. 강좌 수정 ---");
        try {
            System.out.print("수정할 강좌 ID: ");
            long id = Long.parseLong(scanner.nextLine());
            System.out.print("새로운 강좌 제목: ");
            String newTitle = scanner.nextLine();
            boolean success = courseController.updateCourseTitle(id, newTitle);
            if (success) {
                System.out.println("강좌 제목이 성공적으로 수정되었습니다.");
            }
        } catch (NumberFormatException e) {
            System.out.println("숫자 ID를 입력해야 합니다.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printCourseList(List<CourseView> courses) {
        if (courses.isEmpty()) {
            System.out.println("조회된 강좌가 없습니다.");
        } else {
            System.out.println("총 " + courses.size() + "개의 강좌를 찾았습니다.");
            courses.forEach(c -> System.out.printf("ID: %-3d | 제목: %s%n", c.id(), c.title()));
        }
    }
}