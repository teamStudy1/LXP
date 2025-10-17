package com.lxp.infrastructure.mapper;

import com.lxp.infrastructure.row.CategoryRow;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper {
    public static CategoryRow fromResultSet(ResultSet rs) throws SQLException {
        long parentIdRaw = rs.getLong("parent_id");
        Long parentId = rs.wasNull() ? null : parentIdRaw;

        return new CategoryRow(
                rs.getLong("category_id"),
                rs.getString("name"),
                parentId,
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at")
        );
    }
}
