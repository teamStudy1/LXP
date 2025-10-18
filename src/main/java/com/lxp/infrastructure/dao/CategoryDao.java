package com.lxp.infrastructure.dao;

import com.lxp.config.DataSourceFactory;
import com.lxp.domain.category.Category;
import com.lxp.infrastructure.mapper.CategoryMapper;
import com.lxp.infrastructure.row.CategoryRow;
import com.lxp.service.query.CategoryView;
import com.lxp.util.QueryType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CategoryDao.java
 * <p>
 * DB와의 통신을 전담하는 '전문 요리사' 클래스입니다.
 * 이제 Service가 이해할 수 있는 '완성된 요리(CategoryView)'를 직접 반환합니다.
 */
public class CategoryDao {

    /**
     * 새로운 카테고리를 DB에 저장하고, 저장된 결과를 CategoryView로 반환합니다.
     *
     * @param category Service로부터 받은, 비즈니스 규칙이 적용된 Category 객체
     * @return DB에 완전히 저장된 후의 모습을 담은 CategoryView 객체
     */
    public CategoryView save(Category category) throws SQLException {
        String sql = QueryType.CATEGORY_SAVE.getQuery();
        try (Connection conn = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, category.getName());
            if (category.getParentId() != null) {
                pstmt.setLong(2, category.getParentId());
            } else {
                pstmt.setNull(2, Types.BIGINT);
            }
            pstmt.setInt(3, category.getDepth());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long newId = generatedKeys.getLong(1);
                    // 저장 후, findById를 통해 가장 최신 상태의 데이터를 View로 가져옵니다.
                    return findById(newId)
                            .orElseThrow(() -> new SQLException("저장된 카테고리를 다시 조회하는 데 실패했습니다."));
                } else {
                    throw new SQLException("카테고리 생성 실패: ID를 가져오지 못했습니다.");
                }
            }
        }
    }

    /**
     * ID로 카테고리를 찾아 CategoryView로 반환합니다.
     */
    public Optional<CategoryView> findById(Long id) throws SQLException {
        String sql = QueryType.CATEGORY_FIND_BY_ID.getQuery();
        try (Connection conn = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Mapper를 통해 ResultSet -> Row -> View 변환 과정을 거칩니다.
                    CategoryRow row = CategoryMapper.fromResultSet(rs);
                    return Optional.of(CategoryMapper.toView(row));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * 부모 ID로 모든 자식 카테고리를 찾아 CategoryView 리스트로 반환합니다.
     */
    public List<CategoryView> findAllByParentId(Long parentId) throws SQLException {
        String sql;
        if (parentId == null) {
            sql = QueryType.CATEGORY_FIND_ALL_BY_PARENT_ID_IS_NULL.getQuery();
        } else {
            sql = QueryType.CATEGORY_FIND_ALL_BY_PARENT_ID.getQuery();
        }

        List<CategoryView> categories = new ArrayList<>();
        try (Connection conn = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (parentId != null) {
                pstmt.setLong(1, parentId);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CategoryRow row = CategoryMapper.fromResultSet(rs);
                    categories.add(CategoryMapper.toView(row));
                }
            }
        }
        return categories;
    }

    /**
     * 카테고리 이름을 수정합니다.
     */
    public void updateName(Long categoryId, String newName) throws SQLException {
        String sql = QueryType.CATEGORY_UPDATE_NAME.getQuery();
        try (Connection conn = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setLong(2, categoryId);
            pstmt.executeUpdate();
        }
    }

    /**
     * 카테고리의 부모와 깊이를 수정합니다.
     */
    public void updateParent(Long categoryId, Long newParentId, int newDepth) throws SQLException {
        String sql = QueryType.CATEGORY_UPDATE_PARENT.getQuery();
        try (Connection conn = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (newParentId != null) {
                pstmt.setLong(1, newParentId);
            } else {
                pstmt.setNull(1, Types.BIGINT);
            }
            pstmt.setInt(2, newDepth);
            pstmt.setLong(3, categoryId);
            pstmt.executeUpdate();
        }
    }

    /**
     * ID로 카테고리를 삭제합니다.
     */
    public void deleteById(Long id) throws SQLException {
        String sql = QueryType.CATEGORY_DELETE_BY_ID.getQuery();
        try (Connection conn = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }
}

