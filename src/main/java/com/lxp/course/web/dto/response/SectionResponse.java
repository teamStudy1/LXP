package com.lxp.course.web.dto.response;

import com.lxp.course.domain.model.Section;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record SectionResponse(
        Long id,
        String title,
        int sectionOrder,
        List<LectureResponse> lectures
) {
    public static SectionResponse from(Section section) {
        return new SectionResponse(
                section.getId(),
                section.getTitle(),
                section.getSectionOrder(),
                section.getLectures().stream()
                        .map(LectureResponse::from)
                        .collect(Collectors.toList())
        );
    }
}