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
}
