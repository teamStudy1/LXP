package com.lxp.course.web.dto.response;

import com.lxp.course.domain.model.Course;
import com.lxp.user.domain.model.User;

import java.time.LocalDateTime;

public record CourseAllResponse(
        Long id,
        String title,
        User user,
        String totalSeconds,
        int totalLectureCount,
        LocalDateTime createdAt,
        CourseDetailResponse detail) {

    public static CourseAllResponse from(Course course, User user) {
        return new CourseAllResponse(
                course.getId(),
                course.getTitle(),
                user,
                course.getTotalSeconds(),
                course.getTotalLectureCount(),
                course.getCreatedAt(),
                CourseDetailResponse.from(course.getCourseDetail()));
    }
}
