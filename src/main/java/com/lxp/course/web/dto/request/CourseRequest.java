package com.lxp.course.web.dto.request;

import java.util.List;

public record CourseRequest(
        String title,
        Long instructorId,
        Long categoryId,
        String content,
        String contentDetail,
        List<String> tagNames,
        List<SectionRequest> sections) {}
