package com.lxp.infrastructure.dao.course;

import com.lxp.infrastructure.mapper.CourseMapper;
import com.lxp.infrastructure.mapper.SectionMapper;
import com.lxp.infrastructure.mapper.TagMapper;
import com.lxp.infrastructure.row.course.CourseRow;
import com.lxp.infrastructure.row.course.TagRow;
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

}
