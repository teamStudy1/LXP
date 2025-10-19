package com.lxp.formatter;

import static com.lxp.util.TimeConverter.DateTimeFormatter;

import com.lxp.course.web.dto.response.*;
import com.lxp.util.TimeConverter;
import java.util.List;
import java.util.stream.Collectors;

public class CourseFormatter {

    public static String format(CourseResponse course) {
        StringBuilder sb = new StringBuilder();

        appendCourseDetails(sb, course);

        appendSections(sb, course.sections());

        return sb.toString();
    }

    public static String formatList(List<CourseAllResponse> courses) {
        if (courses == null || courses.isEmpty()) {
            return "표시할 강좌가 없습니다.";
        }

        StringBuilder sb = new StringBuilder();

        // 1. 헤더 생성
        sb.append(
                String.format(
                        "%-5s | %-35s | %-7s | %-12s | %-7s | %-20s\n",
                        "ID", "강좌 제목", "강사ID", "총 강의 시간", "강의 수", "생성일"));

        // 2. 구분선 생성
        sb.append("-".repeat(105)).append("\n");

        // 3. 각 강좌 데이터 행 생성
        for (CourseAllResponse course : courses) {
            // LocalDateTime을 정의된 포맷의 문자열로 변환
            String createdAtFormatted = DateTimeFormatter(course.createdAt());

            sb.append(
                    String.format(
                            "%-5d | %-35.35s | %-7s | %-12s | %-7d | %-20s\n",
                            course.id(),
                            course.title(),
                            course.user().getName(),
                            course.totalSeconds(), // TimeConverter 적용 필요
                            course.totalLectureCount(),
                            createdAtFormatted));
        }
        return sb.toString();
    }

    private static void appendCourseDetails(StringBuilder sb, CourseResponse course) {
        String tags = course.tags().stream().map(TagResponse::name).collect(Collectors.joining(", "));

        String categoryPath = CategoryFormatter.formatPath(course.category());

        sb.append("==================================================\n");
        sb.append(String.format(" [강좌] %s (ID: %d)\n", course.title(), course.id()));
        sb.append("==================================================\n");
        sb.append(String.format("  - 강사 이름: %s\n", course.user().getName()));
        sb.append(String.format("  - 카테고리: %s\n", categoryPath));
        sb.append(String.format("  - 태그: [ %s ]\n", tags));
        sb.append(String.format("  - 강의 총 시간: [ %s ]\n", course.totalSeconds()));
        sb.append(String.format("  - 소개: %s\n", course.detail().content()));
        sb.append("--------------------------------------------------\n");
    }

    private static void appendSections(StringBuilder sb, List<SectionResponse> sections) {
        sb.append(" [강의 목차]\n");
        if (sections.isEmpty()) {
            sb.append("  (등록된 섹션이 없습니다.)\n");
            return;
        }

        for (SectionResponse section : sections) {
            sb.append(String.format("\n  § Section %d. %s\n", section.sectionOrder(), section.title()));
            appendLectures(sb, section.lectures());
        }
    }

    private static void appendLectures(StringBuilder sb, List<LectureResponse> lectures) {
        if (lectures.isEmpty()) {
            sb.append("    - (등록된 강의가 없습니다.)\n");
            return;
        }

        for (LectureResponse lecture : lectures) {
            String s = TimeConverter.getFormattedDuration(lecture.duration());
            sb.append(String.format("    - [%d] %s [%s]\n", lecture.lectureOrder(), lecture.title(), s));
        }
    }
}
