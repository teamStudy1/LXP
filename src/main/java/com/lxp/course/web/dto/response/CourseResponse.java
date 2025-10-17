package com.lxp.course.web.dto.response;

import com.lxp.course.domain.model.Course;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record CourseResponse(
        Long id,
        String title,
        Long instructorId,
        Long categoryId,
        double totalSeconds,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        CourseDetailResponse detail,
        Set<TagResponse> tags,
        List<SectionResponse> sections
) {
    public static CourseResponse from(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getInstructorId(),
                course.getCategoryId(),
                course.getTotalSeconds(),
                course.getCreatedAt(),
                course.getUpdatedAt(),
                CourseDetailResponse.from(course.getCourseDetail()),
                course.getTags().stream()
                        .map(TagResponse::from)
                        .collect(Collectors.toSet()),
                course.getSections().stream()
                        .map(SectionResponse::from)
                        .collect(Collectors.toList())
        );
    }
}