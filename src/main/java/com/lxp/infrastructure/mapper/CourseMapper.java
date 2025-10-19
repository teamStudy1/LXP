package com.lxp.infrastructure.mapper;

import com.lxp.api.dto.CourseView;
import com.lxp.domain.course.Course;
import com.lxp.infrastructure.row.course.CourseRow;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CourseMapper {
    public static Course toDomain(CourseRow row) {
        if (row == null) return null;
        Set<String> tags = new HashSet<>();
        String tagsString = row.tags();
        if (tagsString != null && !tagsString.isEmpty()) {
            tags.addAll(Arrays.asList(tagsString.split(", ")));
        }

        return new Course(
                row.courseId(),
                row.title(),
                row.instructorId(),
                row.instructorName(),
                row.content(),
                row.contentDetail(),
                tags,
                row.totalTime(),
                row.totalLectureCount(),
                row.createdAt() != null ? row.createdAt().toLocalDateTime() : null,
                row.updatedAt() != null ? row.updatedAt().toLocalDateTime() : null
        );
    }

    public static CourseView toView(Course course) {
        if (course == null) return null;
        return new CourseView(
                course.getId(),
                course.getTitle(),
                course.getInstructorName(),
                course.getContent(),
                course.getContentDetail(),
                course.getTags(),
                course.getTotalTime(),
                course.getTotalLectureCount(),
                course.getCreatedAt()
        );
    }
}