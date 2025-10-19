package com.lxp.course.web.dto.response;

import com.lxp.category.domain.Category;
import com.lxp.category.web.dto.response.CategoryResponse;
import com.lxp.course.domain.model.Course;
import com.lxp.user.domain.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record CourseResponse(
        Long id,
        String title,
        User user,
        CategoryResponse category,
        String totalSeconds,
        int totalLectureCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        CourseDetailResponse detail,
        Set<TagResponse> tags,
        List<SectionResponse> sections) {

    public static CourseResponse from(Course course, Category category, User user) {
        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                user,
                CategoryResponse.from(category),
                course.getTotalSeconds(),
                course.getTotalLectureCount(),
                course.getCreatedAt(),
                course.getUpdatedAt(),
                CourseDetailResponse.from(course.getCourseDetail()),
                course.getTags().stream().map(TagResponse::from).collect(Collectors.toSet()),
                course.getSections().stream().map(SectionResponse::from).collect(Collectors.toList()));
    }
}
