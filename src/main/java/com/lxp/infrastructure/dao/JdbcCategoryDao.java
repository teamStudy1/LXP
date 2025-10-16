package com.lxp.infrastructure.dao;

import com.lxp.domain.category.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCategoryDao implements CategoryDao {

    private final DataSource dataSource;

    public JdbcCategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Category save(Category category) {

        if (category.getId() == null) {

            String sql = "Insert into Category (name, description) values (?, ?, ?)";
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setString(1, category.getName());
                pstmt.setObject(2, category.getParentId());
                pstmt.setInt(3, category.getDepth());
                pstmt.executeUpdate();

                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    Long newId = generatedKeys.getLong(1);
                    return new Category(newId, category.getName(), category.getParentId(), category.getDepth());
                } else {
                    throw new SQLException("ID 생성에 실패했습니다. ");
                }

            } catch (Exception e) {
                throw new RuntimeException("DB 저장 중 오류 발생", e);
            }

        } else {

            String sql = "UPDATE Category SET name = ?, parent_id = ? , depth = ?  WHERE category_id = ?";
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, category.getName());
                pstmt.setObject(2, category.getParentId());
                pstmt.setInt(3, category.getDepth());
                pstmt.setLong(4, category.getId());
                pstmt.executeUpdate();
                return category;
            } catch (Exception e) {
                throw new RuntimeException("DB 업데이트 중 오류 발생", e);
            }
        }
    }
    @Override
    public Optional<Category> findById(Long id) {
        String sql = "SELECT * FROM Category WHERE category_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);;
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRowToCategory(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 조회 중 오류 발생", e);
        }
        return Optional.empty();
    }

    /*
     특정 ID를 부모로 갖는 하위 카테고리가 있는지 확인합니다.
  * */
    @Override
    public boolean existsByParentId(Long parentId) {

         String sql = "SELECT * FROM Category WHERE parent_id = ?";
         try (Connection conn = dataSource.getConnection();
              PreparedStatement pstmt = conn.prepareStatement(sql)) {
                  pstmt.setLong(1, parentId);
                  ResultSet rs = pstmt.executeQuery();
                  if (rs.next()) {
                      return rs.getBoolean(1);
                  }
         } catch (SQLException e) {
                  throw new RuntimeException("DB확인 중 오류 발생", e);
         }
        return false;
    }

    /*
    * 특정 부모에 속한 모든 하위 카테고리 목록 조회
    * */

    @Override
    public List<Category> findAllByParentId(Long parentId) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Category WHERE parent_id = ?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, parentId);
            ResultSet rs = pstmt.executeQuery();

            while  (rs.next()) {
                categories.add(mapRowToCategory(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 조회 중 오류 발생", e);
        }
        return categories;
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT EXISTS (SELECT 1 FROM Category WHERE category_id = ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 확인 중 오류 발생", e);
        }
        return false;
    }
    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Category WHERE category_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB 삭제 중 오류 발생", e);
        }
    }
    private Category mapRowToCategory(ResultSet rs) throws SQLException {
        return new Category(
                rs.getLong("category_id"),
                rs.getString("name"),
                rs.getObject("parent_id", Long.class),
                rs.getInt("depth")
        );
    }

}
