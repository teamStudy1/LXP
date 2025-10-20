package com.lxp.category.persistance.dao;

import com.lxp.category.persistance.mapper.CategoryMapper;
import com.lxp.category.persistance.row.CategoryRow;
import com.lxp.config.DataSourceFactory;
import com.lxp.config.TransactionManager;
import com.lxp.util.QueryType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

public class CategoryDao {
    private final DataSource dataSource;

    public CategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<CategoryRow> findAllDescendantsByCategoryId(Long categoryId) throws SQLException {
        String query = QueryType.CATEGORY_FIND_ALL_DESCENDANTS_BY_PARENT_ID.getQuery();
        List<CategoryRow> categoryRows = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, categoryId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    categoryRows.add(CategoryMapper.fromResultSet(rs));
                }
                return categoryRows;
            }
        }
    }

    public List<CategoryRow> findAncestorsByCategoryId(Long categoryId) throws SQLException {
        String query = QueryType.CATEGORY_FIND_ALL_BY_ID.getQuery();
        List<CategoryRow> categoryRows = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, categoryId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    categoryRows.add(CategoryMapper.fromResultSet(rs));
                }
                return categoryRows;
            }
        }
    }

    public Optional<CategoryRow> findById(Long categoryId) throws SQLException {
        String query = QueryType.CATEGORY_FIND_BY_ID.getQuery();

        try (Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, categoryId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    CategoryRow row = CategoryMapper.fromResultSet(rs);
                    return Optional.of(row);
                }
                return Optional.empty();
            }
        }
    }

    public long save(CategoryRow row) throws SQLException {
        String sql = QueryType.CATEGORY_SAVE.getQuery();
        Connection conn = TransactionManager.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, row.name());
            if (row.parentId() == null) {
                pstmt.setNull(2, Types.BIGINT);
            } else {
                pstmt.setLong(2, row.parentId());
            }
            pstmt.setInt(3, row.depth());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);

                } else {
                    throw new SQLException("카테고리 생성 실패: ID를 가져오지 못했습니다.");
                }
            }
        }
    }

    public List<CategoryRow> findAllByParentId(Long parentId) throws SQLException {
        String sql;
        if (parentId == null) {
            sql = QueryType.CATEGORY_FIND_ALL_BY_PARENT_ID_IS_NULL.getQuery();
        } else {
            sql = QueryType.CATEGORY_FIND_ALL_BY_PARENT_ID.getQuery();
        }

        List<CategoryRow> categories = new ArrayList<>();
        try (Connection conn = DataSourceFactory.getDataSource().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (parentId != null) {
                pstmt.setLong(1, parentId);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(CategoryMapper.fromResultSet(rs));
                }
            }
        }
        return categories;
    }

    public List<CategoryRow> findByNameContaining(String name) throws SQLException {
        String sql = QueryType.CATEGORY_FIND_BY_NAME_CONTAING.getQuery();
        List<CategoryRow> categories = new ArrayList<>();
        try (Connection conn = DataSourceFactory.getDataSource().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + name + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(CategoryMapper.fromResultSet(rs));
                }
            }
        }
        return categories;
    }
}
