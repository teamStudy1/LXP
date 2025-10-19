package com.lxp.handler;

import com.lxp.api.controller.CourseController;
import com.lxp.api.dto.CourseView;
import com.lxp.domain.course.Course;
import com.lxp.util.TimeConverter;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseHandler {
    private final Scanner scanner;
    private final CourseController courseController;

    public CourseHandler(CourseController courseController) {
        this.scanner = new Scanner(System.in);
        this.courseController = courseController;
    }

    public void start() {
        System.out.println("=== Course Management System ===");
        while (handleCourse()) {
            printMenu();
            String command = scanner.nextLine().trim();
            try {
                if (!handleCourse()) {
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

    private boolean handleCourse() {
        printMenu();
        String command = scanner.nextLine().trim();
        try {
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
        } catch (Exception e) {
            System.err.println("오류가 발생했습니다: " + e.getMessage());
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            System.out.println("\n[ 강좌 상세 정보 ]");
            System.out.println("------------------------------------------");
            System.out.println(" - ID: " + course.id());
            System.out.println(" - 제목: " + course.title());
            System.out.println(" - 강사: " + course.instructorName());
            System.out.println(" - 총 시간: " + TimeConverter.getFormattedDurationFromHours(course.totalTime()));
            System.out.println(" - 총 강의 수: " + course.totalLectureCount());
            System.out.println(" - 생성일: " + (course.createdAt() != null ? course.createdAt().format(formatter) : "N/A"));
            System.out.println(" - 상세 설명: " + course.contentDetail());
            System.out.println("------------------------------------------");

        } catch (NumberFormatException e) {
            System.out.println("숫자 ID를 입력해야 합니다.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void createCourse() {
        System.out.println("\n--- 4. 강좌 생성 ---");
        try {
            System.out.print("강좌 제목: ");
            String title = scanner.nextLine();
            System.out.print("강사 ID: ");
            long instructorId = Long.parseLong(scanner.nextLine());
            System.out.print("총 강의 시간 (예: 13.5): ");
            double totalTime = Double.parseDouble(scanner.nextLine());
            System.out.print("총 강의 수: ");
            int totalLectureCount = Integer.parseInt(scanner.nextLine());
            System.out.print("강좌 소개 (목록용): ");
            String content = scanner.nextLine();
            System.out.print("강좌 상세 설명: ");
            String contentDetail = scanner.nextLine();
            System.out.print("태그 (쉼표(,)로 구분하여 여러 개 입력 가능): ");
            String tagInput = scanner.nextLine();

            Set<String> tagNames = new HashSet<>();
            if (tagInput != null && !tagInput.trim().isEmpty()) {
                tagNames = Arrays.stream(tagInput.split(","))
                        .map(String::trim)
                        .collect(Collectors.toSet());
            }

            Course createdCourse = courseController.createCourse(title, instructorId, totalTime, totalLectureCount, content, contentDetail, tagNames);
            System.out.println("강좌가 성공적으로 생성되었습니다! (ID: " + createdCourse.getId() + ")");

        } catch (NumberFormatException e) {
            System.out.println("ID, 시간, 강의 수는 숫자로 입력해야 합니다.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void updateCourse() {
        System.out.println("\n--- 5. 강좌 수정 ---");
        try {
            System.out.print("수정할 강좌의 ID를 입력하세요: ");
            long id = Long.parseLong(scanner.nextLine());
            System.out.print("새로운 강좌 제목을 입력하세요: ");
            String newTitle = scanner.nextLine();

            boolean success = courseController.updateCourseTitle(id, newTitle);

            if (success) {
                System.out.println("강좌 제목이 성공적으로 수정되었습니다.");
            } else {
                System.out.println("해당 ID의 강좌를 찾을 수 없어 수정에 실패했습니다.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID는 숫자로 입력해야 합니다.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void printCourseList(List<CourseView> courses) {
        if (courses.isEmpty()) {
            System.out.println("조회된 강좌가 없습니다.");
        } else {
            System.out.println("총 " + courses.size() + "개의 강좌를 찾았습니다.");
            // [수정] 헤더에 '강좌 설명'을 다시 추가하고 전체 너비 조정
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-4s | %-25s | %-8s | %-20s | %-25s | %-15s | %s%n", "ID", "강좌 제목", "강사 이름", "강좌 설명", "태그", "총 강의 시간", "생성 시간");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            for (CourseView c : courses) {
                String formattedTime = TimeConverter.getFormattedDurationFromHours(c.totalTime());
                String formattedCreatedAt = (c.createdAt() != null) ? c.createdAt().format(formatter) : "";

                String shortContent = c.content();
                if (shortContent != null && shortContent.length() > 40) {
                    shortContent = shortContent.substring(0, 40) + "...";
                } else if (shortContent == null) {
                    shortContent = "";
                }

                String tagsDisplay = (c.tags() != null && !c.tags().isEmpty()) ? String.join(", ", c.tags()) : "태그 없음";

                System.out.printf(
                        "%-4d | %-25s | %-8s | %-20s | %-25s | %-15s | %s%n",
                        c.id(),
                        c.title(),
                        c.instructorName(),
                        shortContent,
                        tagsDisplay,
                        formattedTime,
                        formattedCreatedAt
                );
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }
}