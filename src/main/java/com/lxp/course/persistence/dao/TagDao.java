package com.lxp.course.persistence.dao;

import com.lxp.course.persistence.mapper.TagMapper;
import com.lxp.course.persistence.row.TagRow;
import com.lxp.util.QueryType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagDao {
    private final DataSource dataSource;

    public TagDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<TagRow> findAllByCourseId(Long courseId) throws SQLException {
        String query = QueryType.TAG_FIND_ALL_BY_COURSE_ID.getQuery();
        List<TagRow> tags = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, courseId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tags.add(TagMapper.fromResultSet(rs));
                }
            }
        }
        return tags;
    }

}
