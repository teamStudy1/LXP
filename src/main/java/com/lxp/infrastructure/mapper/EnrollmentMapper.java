package com.lxp.infrastructure.mapper;

import com.lxp.domain.enrollment.Enrollment;
import com.lxp.domain.enrollment.EnrollmentView;
import com.lxp.infrastructure.row.EnrollmentRow;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnrollmentMapper {

    public EnrollmentRow toRow(EnrollmentView enrollmentView) {
        return new EnrollmentRow(
                enrollmentView.courseId(),
                enrollmentView.studentId(),
                enrollmentView.status().toString(),
                enrollmentView.enrollmentAt(),
                enrollmentView.updatedAt()
        );
    }


    public static Enrollment toDomain(EnrollmentRow row) {
        return Enrollment.load(
                row.userId(),
                row.courseId(),
                row.status(),
                row.enrollmentAt(),
                row.updated_at()
        );
    }

    public static EnrollmentRow fromResultSet(ResultSet rs) throws SQLException {
        return new EnrollmentRow(
                rs.getLong("user_id"),
                rs.getLong("course_id"),
                rs.getString("status"),
                rs.getTimestamp("enrolled_at"),
                rs.getTimestamp("updated_at")
        );
    }



}
