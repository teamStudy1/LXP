package com.lxp.course.web.dto.response;

import com.lxp.course.domain.model.Course;
import java.time.LocalDateTime;

public record CourseAllResponse(
        Long id,
        String title,
        Long instructorId,
        String totalSeconds,
        int totalLectureCount,
        LocalDateTime createdAt,
        CourseDetailResponse detail) {

    public static CourseAllResponse from(Course course) {
        return new CourseAllResponse(
                course.getId(),
                course.getTitle(),
                course.getInstructorId(),
                course.getTotalSeconds(),
                course.getTotalLectureCount(),
                course.getCreatedAt(),
                CourseDetailResponse.from(course.getCourseDetail()));
    }
}
