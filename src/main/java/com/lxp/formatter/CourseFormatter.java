package com.lxp.formatter;

import com.lxp.course.web.dto.response.CourseResponse;
import com.lxp.course.web.dto.response.LectureResponse;
import com.lxp.course.web.dto.response.SectionResponse;
import com.lxp.course.web.dto.response.TagResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CourseFormatter {

    public String format(CourseResponse course) {
        StringBuilder sb = new StringBuilder();

        // 1. 강좌 기본 정보 출력
        appendCourseDetails(sb, course);

        // 2. 섹션 및 강의 목록 출력 (들여쓰기 적용)
        appendSections(sb, course.sections());

        return sb.toString();
    }

    private void appendCourseDetails(StringBuilder sb, CourseResponse course) {
        String tags = course.tags().stream()
                .map(TagResponse::name)
                .collect(Collectors.joining(", "));

        sb.append("==================================================\n");
        sb.append(String.format(" [강좌] %s (ID: %d)\n", course.title(), course.id()));
        sb.append("==================================================\n");
        sb.append(String.format("  - 강사 ID: %d\n", course.instructorId()));
        sb.append(String.format("  - 카테고리 ID: %d\n", course.categoryId()));
        sb.append(String.format("  - 태그: [ %s ]\n", tags));
        sb.append(String.format("  - 소개: %s\n", course.detail().content()));
        sb.append("--------------------------------------------------\n");
    }

    private void appendSections(StringBuilder sb, List<SectionResponse> sections) {
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

    private void appendLectures(StringBuilder sb, List<LectureResponse> lectures) {
        if (lectures.isEmpty()) {
            sb.append("    - (등록된 강의가 없습니다.)\n");
            return;
        }

        for (LectureResponse lecture : lectures) {
            int minutes = lecture.duration() / 60;
            int seconds = lecture.duration() % 60;
            sb.append(String.format("    - [%d] %s (%d분 %02d초)\n",
                    lecture.lectureOrder(),
                    lecture.title(),
                    minutes,
                    seconds
            ));
        }
    }
}