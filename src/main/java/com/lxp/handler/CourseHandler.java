package com.lxp.handler;

import com.lxp.course.web.CourseController;
import com.lxp.course.web.dto.request.*;
import com.lxp.course.web.dto.response.CourseAllResponse;
import com.lxp.course.web.dto.response.CourseResponse;
import com.lxp.formatter.CourseFormatter;
import java.sql.SQLException;
import java.util.*;

public class CourseHandler {
    private final Scanner scanner;
    private final CourseController courseController;

    public CourseHandler(CourseController courseController) {
        this.scanner = new Scanner(System.in);
        this.courseController = courseController;
    }

    public boolean start() {
        System.out.println("\n=== 강좌 관리시스템에 접속했습니다");
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
                requestFindAllCourse();
                break;
            case "2":
                requestFindAllByKeyword();
                break;
            case "3":
                requestFindByIdCourse();
                break;
            case "4":
                requestCreateCourse();
                break;
            case "0":
                return false;
            default:
                System.out.println("잘못된 입력입니다.");
        }
        return true;
    }

    private void requestFindAllByKeyword() throws SQLException {
        System.out.println("\n--- 강좌 검색 ---");
        System.out.println("\n--- title이나 tag 또는 강사명 ---");
        String keyword = scanner.nextLine();
        List<CourseAllResponse> courses = courseController.findAllByKeyword(keyword);
        if (courses.isEmpty()) {
            System.out.println("찾을  강좌가 없습니다.");
            return;
        }
        String formattedList = CourseFormatter.formatList(courses);
        System.out.println(formattedList);
    }

    public void requestFindAllCourse() throws SQLException {
        System.out.println("\n--- 전체 강좌 목록 ---");
        List<CourseAllResponse> courses = courseController.findAll();
        if (courses.isEmpty()) {
            System.out.println("등록된 강좌가 없습니다.");
            return;
        }
        String formattedList = CourseFormatter.formatList(courses);
        System.out.println(formattedList);
    }

    public void requestFindByIdCourse() throws SQLException {
        System.out.print("course ID: ");
        Long courseId = Long.parseLong(scanner.nextLine());
        CourseResponse response = courseController.findById(courseId);
        CourseFormatter courseFormatter = new CourseFormatter();
        String output = courseFormatter.format(response);
        System.out.println(output);
        System.out.println("강좌 상세 조회가 완료 되었습니다.");
    }

    public void requestCreateCourse() throws SQLException {
        System.out.println("--- 강좌 기본 정보 입력 ---");
        System.out.println("형식: 강좌 제목,강사 ID,카테고리 ID,내용,내용 상세");
        System.out.print("입력: ");
        String courseInput = scanner.nextLine();
        String[] courseParts = courseInput.split(",", -1);

        if (courseParts.length != 5) {
            System.out.println("오류: 입력 형식이 올바르지 않습니다. 5개의 값을 입력해야 합니다.");
        }

        String title = courseParts[0].trim();
        Long instructorId = Long.parseLong(courseParts[1].trim());
        Long categoryId = Long.parseLong(courseParts[2].trim());
        String content = courseParts[3].trim();
        String contentDetail = courseParts[4].trim();

        System.out.println("\n--- 태그 정보 입력 ---");
        System.out.println("형식: tagName1,tagName2,tagName3,...");
        System.out.print("입력: ");
        String tagInput = scanner.nextLine();
        List<String> tagNames =
                Arrays.stream(tagInput.split(","))
                        .map(String::trim)
                        .filter(name -> !name.isEmpty())
                        .toList();

        List<SectionRequest> sections = new ArrayList<>();
        int sectionOrder = 1;

        while (true) {
            System.out.printf("\n--- [%d번째 섹션] 정보 입력 ('완료' 입력 시 종료) ---\n", sectionOrder);
            System.out.print("섹션 제목 입력: ");
            String sectionTitle = scanner.nextLine();
            if ("완료".equalsIgnoreCase(sectionTitle.trim())) {
                break;
            }

            List<LectureRequest> lectures = new ArrayList<>();
            int lectureOrder = 1;
            while (true) {
                System.out.printf("  > [%d번째 강의] 정보 입력 ('다음' 입력 시 섹션 종료)\n", lectureOrder);
                System.out.println("  > 형식: title,videoUrl,duration(초 단위 숫자)");
                System.out.print("  > 입력: ");
                String lectureInput = scanner.nextLine();
                if ("다음".equalsIgnoreCase(lectureInput.trim())) {
                    break;
                }

                String[] lectureParts = lectureInput.split(",", -1);
                if (lectureParts.length != 3) {
                    System.out.println("  > 오류: 입력 형식이 올바르지 않습니다. 3개의 값을 입력해야 합니다.");
                    continue;
                }

                String lectureTitle = lectureParts[0].trim();
                String videoUrl = lectureParts[1].trim();
                int duration = Integer.parseInt(lectureParts[2].trim());

                lectures.add(new LectureRequest(lectureTitle, videoUrl, duration));
                lectureOrder++;
            }

            sections.add(new SectionRequest(sectionTitle, lectures));
            sectionOrder++;
        }

        CourseRequest request =
                new CourseRequest(
                        title, instructorId, categoryId, content, contentDetail, tagNames, sections);

        courseController.save(request);
        System.out.println("\n--- 모든 정보 입력 완료 ---");
        System.out.println("강좌 생성을 위한 요청 객체가 생성되었습니다.");
    }

    //
    //    public void requestUpdateCourse() {
    //        try {
    //            System.out.print("수정할 강좌의 ID를 입력하세요: ");
    //            Long courseId = Long.parseLong(scanner.nextLine().trim());
    //
    //            while (true) {
    //                printUpdateMenu(courseId);
    //                String choice = scanner.nextLine().trim();
    //
    //                switch (choice) {
    //                    case "1":
    //                        handleCourseInfoUpdate(courseId);
    //                        break;
    //                    case "2":
    //                        handleAddSection(courseId);
    //                        break;
    //                    case "3":
    //                        handleDeleteSection(courseId);
    //                        break;
    //                    case "4":
    //                        handleAddLecture(courseId);
    //                        break;
    //                    case "5":
    //                        handleDeleteLecture(courseId);
    //                        break;
    //                    case "0":
    //                        System.out.println("강좌 수정을 마칩니다.");
    //                        return; // 수정 루프 종료
    //                    default:
    //                        System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
    //                }
    //            }
    //        } catch (NumberFormatException e) {
    //            System.err.println("오류: 유효한 숫자 ID를 입력해야 합니다.");
    //        } catch (IllegalArgumentException e) {
    //            System.err.println("오류: " + e.getMessage());
    //        } catch (Exception e) {
    //            System.err.println("알 수 없는 오류가 발생했습니다: " + e.getMessage());
    //        }
    //    }
    //    private void printUpdateMenu(Long courseId) {
    //        System.out.printf("\n--- [강좌 ID: %d] 수정 메뉴 ---\n", courseId);
    //        System.out.println("1. 강좌 기본 정보 수정");
    //        System.out.println("2. 섹션 추가");
    //        System.out.println("3. 섹션 삭제");
    //        System.out.println("4. 강의 추가");
    //        System.out.println("5. 강의 삭제");
    //        System.out.println("0. 수정 완료 및 돌아가기");
    //        System.out.print("선택: ");
    //    }
    //
    //    private void handleCourseInfoUpdate(Long courseId) {
    //        System.out.println("\n--- 1. 강좌 기본 정보 수정 ---");
    //        System.out.print("새 강좌 제목: ");
    //        String title = scanner.nextLine();
    //        System.out.print("새 강좌 소개: ");
    //        String content = scanner.nextLine();
    //        System.out.print("새 강좌 상세 소개: ");
    //        String contentDetail = scanner.nextLine();
    //
    //        CourseUpdateRequest request = new CourseUpdateRequest(courseId, title, content,
    // contentDetail);
    //        courseController.updateCourseInfo(request); // Controller 호출
    //        System.out.println("✅ 강좌 기본 정보가 성공적으로 수정되었습니다.");
    //    }
    //
    //    private void handleAddSection(Long courseId) {
    //        System.out.println("\n--- 2. 섹션 추가 ---");
    //        System.out.print("추가할 섹션 제목: ");
    //        String title = scanner.nextLine();
    //
    //
    //        SectionAddRequest request = new SectionAddRequest(courseId, title,
    // Collections.emptyList());
    //        courseController.addSection(request);
    //        System.out.println("✅ 섹션이 성공적으로 추가되었습니다.");
    //    }
    //
    //    private void handleDeleteSection(Long courseId) {
    //        System.out.println("\n--- 3. 섹션 삭제 ---");
    //        System.out.print("삭제할 섹션 ID: ");
    //        Long sectionId = Long.parseLong(scanner.nextLine().trim());
    //
    //        SectionDeleteRequest request = new SectionDeleteRequest(courseId, sectionId);
    //        courseController.deleteSection(request);
    //        System.out.println("✅ 섹션이 성공적으로 삭제되었습니다.");
    //    }
    //
    //    private void handleAddLecture(Long courseId) {
    //        System.out.println("\n--- 4. 강의 추가 ---");
    //        System.out.print("강의를 추가할 섹션 ID: ");
    //        Long sectionId = Long.parseLong(scanner.nextLine().trim());
    //        System.out.print("새 강의 제목: ");
    //        String title = scanner.nextLine();
    //        System.out.print("새 강의 영상 URL: ");
    //        String videoUrl = scanner.nextLine();
    //        System.out.print("새 강의 시간 (초 단위): ");
    //        int duration = Integer.parseInt(scanner.nextLine().trim());
    //
    //        LectureAddRequest request = new LectureAddRequest(courseId, sectionId, title, videoUrl,
    // duration);
    //        courseController.addLecture(request);
    //        System.out.println("✅ 강의가 성공적으로 추가되었습니다.");
    //    }
    //
    //    private void handleDeleteLecture(Long courseId) {
    //        System.out.println("\n--- 5. 강의 삭제 ---");
    //        System.out.print("강의를 삭제할 섹션 ID: ");
    //        Long sectionId = Long.parseLong(scanner.nextLine().trim());
    //        System.out.print("삭제할 강의 ID: ");
    //        Long lectureId = Long.parseLong(scanner.nextLine().trim());
    //
    //        LectureDeleteRequest request = new LectureDeleteRequest(courseId, sectionId, lectureId);
    //        courseController.deleteLecture(request);
    //        System.out.println("✅ 강의가 성공적으로 삭제되었습니다.");
    //    }
}
