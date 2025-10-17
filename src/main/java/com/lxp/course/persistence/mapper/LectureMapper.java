package com.lxp.course.persistence.mapper;

import com.lxp.course.persistence.row.LectureRow;

import java.sql.ResultSet;
import java.sql.SQLException;
public class LectureMapper {

    public static LectureRow fromResultSet(ResultSet rs) throws SQLException {
        return new LectureRow(
                rs.getLong("lecture_id"),
                rs.getLong("section_id"),
                rs.getString("name"),
                rs.getInt("lecture_time"),
                rs.getInt("order_num"),
                rs.getString("video_url"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at")
        );
    }
}
