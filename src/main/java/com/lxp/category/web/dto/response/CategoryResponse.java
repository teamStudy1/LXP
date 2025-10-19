package com.lxp.category.web.dto.response;

import com.lxp.category.domain.Category;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record CategoryResponse(Long id, String name, int depth, List<CategoryResponse> children) {
    public static CategoryResponse from(Category category) {
        if (category == null) {
            return null;
        }

        List<CategoryResponse> childResponses =
                category.getChildren() != null
                        ? category.getChildren().stream()
                                .map(CategoryResponse::from)
                                .collect(Collectors.toList())
                        : Collections.emptyList();

        return new CategoryResponse(
                category.getId(), category.getName(), category.getDepth(), childResponses);
    }
}
