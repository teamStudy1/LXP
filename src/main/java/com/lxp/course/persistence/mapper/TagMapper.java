package com.lxp.course.persistence.mapper;

import com.lxp.course.domain.model.Tag;
import com.lxp.course.persistence.row.TagRow;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper {
    public static TagRow fromResultSet(ResultSet rs) throws SQLException {
        return new TagRow(rs.getLong("tag_id"), rs.getString("name"), rs.getTimestamp("created_at"));
    }

    public static Tag toDomain(TagRow tagRow) {
        return new Tag(tagRow.tagId(), tagRow.name(), tagRow.createdAt().toLocalDateTime());
    }

    public static TagRow toRow(Tag tag) {
        return new TagRow(null, tag.getName(), null);
    }
}
