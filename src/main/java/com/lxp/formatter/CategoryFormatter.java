package com.lxp.formatter;

import com.lxp.category.web.dto.response.CategoryResponse;
import java.util.ArrayList;
import java.util.List;

public class CategoryFormatter {

    public static String formatPath(CategoryResponse category) {
        if (category == null) {
            return "미지정";
        }

        List<String> pathNames = new ArrayList<>();
        CategoryResponse current = category;

        // 가장 깊은 자식까지 순회하며 경로 이름을 리스트에 추가합니다.
        while (current != null) {
            pathNames.add(current.name());
            // DAO 로직상 경로는 하나만 존재하므로, 첫 번째 자식을 다음 순회 대상으로 삼습니다.
            if (current.children() != null && !current.children().isEmpty()) {
                current = current.children().get(0);
            } else {
                current = null; // 루프 종료 조건
            }
        }
        return String.join(" > ", pathNames);
    }

    public static String formatCategories(List<CategoryResponse> categories) {
        if (categories == null || categories.isEmpty()) return "표시할 카테고리가 없습니다.";

        StringBuilder sb = new StringBuilder();
        sb.append(
                String.format(
                        "%-5s | %-35s | %-5s | %-5s \n",
                        "ID", "카테고리 이름", "계층", "부모 ID"
                )
        );
        sb.append("-".repeat(100)).append("\n");
        for (CategoryResponse category : categories) {
            sb.append(
                    String.format(
                            "%-5s | %-35s | %-5s | %-5s \n",
                            category.id(),
                            category.name(),
                            category.depth(),
                            "없음"
                    )
            );

            for (CategoryResponse child : category.children()) {
                sb.append(
                        String.format(
                                "%-5s | %-35s | %-5s | %-5s \n",
                                child.id(),
                                child.name(),
                                child.depth(),
                                category.id()
                        )
                );
            }
        }
        return sb.toString();
    }

    public static String formatSearchCategory(List<CategoryResponse> categories) {
        if (categories == null || categories.isEmpty()) return "표시할 카테고리가 없습니다.";

        StringBuilder sb = new StringBuilder();
        sb.append(
                String.format(
                        "%-5s | %-35s | %-5s \n",
                        "ID", "카테고리 이름", "계층"
                )
        );
        sb.append("-".repeat(100)).append("\n");
        for (CategoryResponse category : categories) {
            sb.append(
                    String.format(
                            "%-5s | %-35s | %-5s \n",
                            category.id(),
                            category.name(),
                            category.depth()
                    )
            );
        }
        return sb.toString();
    }
}
