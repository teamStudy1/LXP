package com.lxp.course.persistence.mapper;

import com.lxp.course.domain.model.Section;
import com.lxp.course.persistence.row.SectionRow;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SectionMapper {

    public static SectionRow fromResultSet(ResultSet rs) throws SQLException {
        return new SectionRow(
                rs.getLong("section_id"),
                rs.getLong("course_id"),
                rs.getString("name"),
                rs.getInt("order_num"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at"));
    }

    public static SectionRow toRow(Section section, Long courseId) {
        return new SectionRow(
                null, courseId, section.getTitle(), section.getSectionOrder(), null, null);
    }
}
