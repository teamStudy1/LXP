package com.lxp.api;

import com.lxp.domain.category.Category;

public class CategoryDto {

    public record CreateRequest(String name, Long parentId) {}

    public record UpdateRequest(Long id, String name, Long parentId) {}

    public  record Responese(Long id, String name, Long parentId, int depth) {

        public static Responese from(Category category) {
            return new Responese(category.getId(),
                    category.getName(),
                    category.getParentId(),
                    category.getDepth());
        }
    }
}
