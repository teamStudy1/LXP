package com.lxp.course.persistence.dao;

import com.lxp.course.persistence.mapper.SectionMapper;
import com.lxp.course.persistence.row.SectionRow;
import com.lxp.util.QueryType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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
        }
    }
}
