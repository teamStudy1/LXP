package com.lxp.course.persistence.mapper;

import com.lxp.course.persistence.row.TagRow;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper {
    public static TagRow fromResultSet(ResultSet rs) throws SQLException {
        return new TagRow(
                rs.getLong("tag_id"),
                rs.getString("name"),
                rs.getTimestamp("created_at")
        );
    }
}
