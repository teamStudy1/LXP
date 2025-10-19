package com.lxp.infrastructure.mapper;

import com.lxp.infrastructure.row.CategoryRow;
import com.lxp.service.query.CategoryView;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
* 보조 요리사 클래스
* '날것 접시(CategoryRow)에 담긴 재료를 식재료 쟁반(CategoryView)으로 옮겨 담거나
DB 결과 (ResultSet)를 '날것 접시'로 옮겨 담는 단순 반복 작업 처리
*/
public class CategoryMapper {

    public static CategoryRow fromResultSet(ResultSet rs) throws SQLException {
        return new CategoryRow(
                rs.getLong("category_id"),
                rs.getString("name"),
                rs.getObject("parent_id", Long.class),
                rs.getInt("depth"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at")
        );
    }

    /**
     * CategoryRow 객체를 CategoryView 객체로 변환합니다.
     */
    public static CategoryView toView(CategoryRow row) {
        return new CategoryView(
                row.categoryId(),
                row.name(),
                row.parentId(),
                row.depth(),
                row.createdAt()
        );
    }
}