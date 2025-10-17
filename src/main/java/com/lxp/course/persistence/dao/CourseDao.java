package com.lxp.course.persistence.dao;

import com.lxp.course.persistence.mapper.CourseMapper;
import com.lxp.course.persistence.row.CourseRow;
import com.lxp.infrastructure.mapper.CategoryMapper;
import com.lxp.infrastructure.row.CategoryRow;
import com.lxp.util.QueryType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

public class CourseDao {
    private final DataSource dataSource;

    public CourseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<CourseRow> findById(Long id) throws SQLException {
        String query = QueryType.COURSE_FIND_BY_ID.getQuery();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    return CourseMapper.fromResultSet(rs);
                }
                return Optional.empty();
            }
        }
    }

    public List<CategoryRow> findAll() throws SQLException {
        String query = QueryType.COURSE_FIND_BY_ID_JOIN_CATEGORY.getQuery();
        List<CategoryRow> categoryRows = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                CategoryRow row = CategoryMapper.fromResultSet(rs);
                categoryRows.add(row);
            }
        }
        return categoryRows;
    }

}
