package com.lxp.course.persistence.dao;

import com.lxp.config.TransactionManager;
import com.lxp.course.persistence.mapper.CourseMapper;
import com.lxp.course.persistence.row.CourseRow;
import com.lxp.exception.DataAccessException;
import com.lxp.util.QueryType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

public class CourseDao {
    private final DataSource dataSource;

    public CourseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<CourseRow> findById(Long id) {
        String query = QueryType.COURSE_FIND_BY_ID.getQuery();

        try (Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    CourseRow row = CourseMapper.fromResultSet(rs);
                    return Optional.of(row);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Load Course aggregate failed. id=" + id, e);
        }
    }

    public Long save(CourseRow row) throws SQLException {
        String query = QueryType.COURSE_INSERT.getQuery();
        Connection conn = TransactionManager.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, row.title());
            pstmt.setLong(2, row.instructorId());
            pstmt.setLong(3, row.categoryId());
            pstmt.setString(4, row.totalTime());
            pstmt.setInt(5, row.totalLectureCount());
            pstmt.setString(6, row.content());
            pstmt.setString(7, row.contentDetail());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                } else {
                    throw new DataAccessException("Course ID 조회를 실패했습니다.");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("faild Save Course to DB.", e);
        }
    }

    public List<CourseRow> findAll() {
        String query = QueryType.COURSE_FIND_ALL.getQuery();

        List<CourseRow> courses = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                CourseRow row = CourseMapper.fromResultSet(rs);
                courses.add(row);
            }
        } catch (SQLException e) {
            throw new DataAccessException("강좌 전체조회 실패", e);
        }

        return courses;
    }

    public List<CourseRow> findByTitleContaining(String keyword) {
        List<CourseRow> rows = new ArrayList<>();
        String sql = QueryType.COURSE_FIND_BY_KEYWORD.getQuery();

        String searchKeyword = "%" + keyword + "%";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, searchKeyword);
            preparedStatement.setString(2, searchKeyword);
            preparedStatement.setString(3, searchKeyword);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    rows.add(CourseMapper.fromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("키워드로 강좌 검색 실패. keyword=" + keyword, e);
        }
        return rows;
    }

    public void update(CourseRow row) throws SQLException {
        String query = QueryType.COURSE_UPDATE.getQuery();

        Connection conn = TransactionManager.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {

            // PreparedStatement에 파라미터 바인딩
            pstmt.setString(1, row.title());
            pstmt.setString(2, row.content());
            pstmt.setString(3, row.contentDetail());

            pstmt.setLong(4, row.courseId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new DataAccessException(
                        "Course 업데이트 실패: ID에 해당하는 강좌를 찾을 수 없습니다. ID: " + row.courseId());
            }
        } catch (SQLException e) {
            throw new DataAccessException("Course 업데이트 실패. ID: " + row.courseId(), e);
        }
    }
}
