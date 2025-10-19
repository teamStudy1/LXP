package com.lxp.category.persistance;

import com.lxp.category.domain.Category;
import com.lxp.category.domain.CategoryRepository;
import com.lxp.category.persistance.dao.CategoryDao;
import com.lxp.category.persistance.mapper.CategoryMapper;
import com.lxp.category.persistance.row.CategoryRow;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCategoryRepository implements CategoryRepository {
    private final CategoryDao categoryDao;

    public JdbcCategoryRepository(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    // 만약에 해당 categoryId없다면 error?
    public Category findAllByParents(Long categoryId) throws SQLException {
        List<CategoryRow> categoryRows = categoryDao.findAncestorsByCategoryId(categoryId);
        return CategoryMapper.toDomainHierarchy(categoryRows);
    }

    public Category findAllByDescendants(Long categoryId) throws SQLException {
        List<CategoryRow> categoryRows = categoryDao.findAllDescendantsByCategoryId(categoryId);
        return CategoryMapper.toDomainDescentdants(categoryRows, categoryId);
    }

    public Optional<Category> findById(Long categoryId) throws SQLException {
        Optional<CategoryRow> optionalRow = categoryDao.findById(categoryId);

        return optionalRow.map(CategoryMapper::toDomain);
    }

    public void save(Category newCategory) throws SQLException {
        CategoryRow row = CategoryMapper.toRow(newCategory);
        categoryDao.save(row);
    }

    public List<Category> findAll() throws SQLException {
        List<CategoryRow> rootCategories = categoryDao.findAllByParentId(null);

        List<Category> categories = new ArrayList<>();
        for (CategoryRow category : rootCategories) {
            categories.add(findAllByDescendants(category.categoryId()));
        }
        return categories;
    }

    public void update(Category category) {
        // TODO(추후 구현)
    }

    public List<Category> searchName(String name) throws SQLException {
        List<CategoryRow> categoryRow = categoryDao.findByNameContaining(name);
        return categoryRow.stream().map(CategoryMapper::toDomain).toList();
    }
}
