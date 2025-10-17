package com.lxp.infrastructure.mapper;

import com.lxp.infrastructure.row.course.TagRow;

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
