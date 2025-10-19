package com.lxp.course.persistence.dao;

import com.lxp.config.TransactionManager;
import com.lxp.course.persistence.mapper.LectureMapper;
import com.lxp.course.persistence.row.LectureRow;
import com.lxp.exception.DataAccessException;
import com.lxp.util.QueryType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class LectureDao {
    private final DataSource dataSource;

    public LectureDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<LectureRow> findAllBySectionIds(List<Long> sectionIds) {
        if (sectionIds == null || sectionIds.isEmpty()) {
            return java.util.Collections.emptyList();
        }

        String base = QueryType.LECTURE_FIND_ALL_BY_SECTION_IDS.getQuery();
        String placeholders =
                sectionIds.stream().map(id -> "?").collect(java.util.stream.Collectors.joining(", "));

        String query = base.replace(":in_clause", placeholders);

        List<LectureRow> results = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query)) {

            int i = 1;
            for (Long sectionId : sectionIds) {
                pstmt.setLong(i++, sectionId);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(LectureMapper.fromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return results;
    }

    public void batchInsert(List<LectureRow> rows) throws SQLException {
        String query = QueryType.LECTURE_BATCH_INSERT.getQuery();
        Connection conn = TransactionManager.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (LectureRow row : rows) {
                pstmt.setLong(1, row.sectionId());
                pstmt.setString(2, row.name());
                pstmt.setInt(3, row.lectureTime());
                pstmt.setInt(4, row.orderNum());
                pstmt.setString(5, row.videoUrl());
                pstmt.addBatch();
            }
            pstmt.executeBatch();

        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
