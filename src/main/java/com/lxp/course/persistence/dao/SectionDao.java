package com.lxp.course.persistence.dao;

import com.lxp.config.TransactionManager;
import com.lxp.course.persistence.mapper.SectionMapper;
import com.lxp.course.persistence.row.SectionRow;
import com.lxp.exception.DataAccessException;
import com.lxp.util.QueryType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class SectionDao {
    private final DataSource dataSource;

    public SectionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<SectionRow> findAllByCourseId(Long courseId) throws SQLException {
        String query = QueryType.SECTION_FIND_ALL_BY_COURSE_ID.getQuery();
        List<SectionRow> sectionRows = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, courseId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    sectionRows.add(SectionMapper.fromResultSet(rs));
                }
                return sectionRows;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public List<Long> batchInsert(List<SectionRow> rows) throws SQLException {
        String query = QueryType.SECTION_BATCH_INSERT.getQuery();
        Connection conn = TransactionManager.getConnection();
        List<Long> generatedIds = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (SectionRow row : rows) {
                pstmt.setLong(1, row.courseId());
                pstmt.setString(2, row.name());
                pstmt.setInt(3, row.orderNum());
                pstmt.addBatch();
            }
            pstmt.executeBatch();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                while (rs.next()) {
                    generatedIds.add(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return generatedIds;
    }
}
