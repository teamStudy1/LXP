package com.lxp.course.persistence.dao;

import com.lxp.config.TransactionManager;
import com.lxp.course.domain.model.Tag;
import com.lxp.course.persistence.mapper.TagMapper;
import com.lxp.course.persistence.row.TagRow;
import com.lxp.exception.DataAccessException;
import com.lxp.util.QueryType;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;

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
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return tags;
    }

    public List<TagRow> findByNameIn(List<String> names) throws SQLException {
        if (names == null || names.isEmpty()) {
            return Collections.emptyList();
        }

        String base = QueryType.TAG_FIND_BY_NAME_IN.getQuery();
        String placeholders = names.stream().map(id -> "?").collect(Collectors.joining(","));

        String query = base.replace(":in_clause", placeholders);

        List<TagRow> tags = new ArrayList<>();

        Connection connection = TransactionManager.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            int i = 1;
            for (String name : names) {
                pstmt.setString(i++, name);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tags.add(TagMapper.fromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return tags;
    }

    public List<TagRow> batchInsert(List<String> newTagNames) throws SQLException {
        String query = QueryType.TAG_INSERT.getQuery();
        List<TagRow> createdTags = new ArrayList<>();

        Connection connection = TransactionManager.getConnection();
        try (PreparedStatement pstmt =
                connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            for (String name : newTagNames) {
                pstmt.setString(1, name);
                pstmt.addBatch();
            }

            pstmt.executeBatch();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                int index = 0;
                while (generatedKeys.next()) {
                    Long tagId = generatedKeys.getLong(1);
                    String name = newTagNames.get(index++);

                    createdTags.add(new TagRow(tagId, name, new Timestamp(System.currentTimeMillis())));
                }
            }
        }
        return createdTags;
    }

    public void batchInsert(Long courseId, Set<Tag> tags) throws SQLException {
        String query = QueryType.COURSE_TAG_BATCH_INSERT.getQuery();
        Connection conn = TransactionManager.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (Tag tag : tags) {
                pstmt.setLong(1, courseId);
                pstmt.setLong(2, tag.getId());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }
}
