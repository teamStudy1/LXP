package com.lxp.category.persistance.mapper;

import com.lxp.category.domain.Category;
import com.lxp.category.persistance.row.CategoryRow;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CategoryMapper {
    public static CategoryRow fromResultSet(ResultSet rs) throws SQLException {
        long parentIdRaw = rs.getLong("parent_id");
        Long parentId = rs.wasNull() ? null : parentIdRaw;

        return new CategoryRow(
                rs.getLong("category_id"), rs.getString("name"), parentId, rs.getInt("depth"), null, null);
    }

    public static Category toDomainHierarchy(List<CategoryRow> rows) {
        if (rows == null || rows.isEmpty()) {
            return null;
        }

        Category childNode = null;
        Category parentNode = null;

        for (CategoryRow row : rows) {

            parentNode = toDomain(row);

            if (childNode != null) {
                parentNode.addChildren(childNode);
            }

            childNode = parentNode;
        }

        return parentNode;
    }

    public static Category toDomain(CategoryRow row) {
        return new Category(row.categoryId(), row.name(), row.depth());
    }

    public static CategoryRow toRow(Category category) {
        return new CategoryRow(
                null,
                category.getName(),
                category.getParent() == null ? null : category.getParent().getId(),
                category.getDepth(),
                null,
                null);
    }

    public static Category toDomainDescentdants(List<CategoryRow> rows, Long rootId) {
        if (rows == null || rows.isEmpty()) {
            return null;
        }
        Map<Long, Category> nodeMap =
                rows.stream()
                        .map(CategoryMapper::toDomain)
                        .collect(Collectors.toMap(Category::getId, Function.identity()));

        for (CategoryRow row : rows) {
            Long parentId = row.parentId();

            if (parentId != null && nodeMap.containsKey(parentId)) {
                Category childNode = nodeMap.get(row.categoryId());
                Category parentNode = nodeMap.get(parentId);
                parentNode.addChildren(childNode);
            }
        }

        return nodeMap.get(rootId);
    }
}
