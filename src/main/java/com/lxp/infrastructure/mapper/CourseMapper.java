package com.lxp.infrastructure.mapper;

import com.lxp.api.dto.CourseView;
import com.lxp.domain.course.Course;
import com.lxp.infrastructure.row.course.CourseRow;

import java.util.ArrayList;

public class CourseMapper {
    public static Course toDomain(CourseRow row) {
        if (row == null) {
            return null;
        }

        return new Course(
                row.courseId(),
                row.title(),
                row.instructorId(),
                new ArrayList<>(),
                null,
                row.createdAt() != null ? row.createdAt().toLocalDateTime() : null
        );
    }

    public static CourseView toView(Course course) {
        if (course == null) {
            return null;
        }
        return new CourseView(
                course.getId(),
                course.getTitle(),
                course.getInstructorId(),
                course.getCreatedAt()
        );
    }
}
