package com.lxp.course.web.dto.request;

public record CourseUpdateRequest(
        Long courseId, String title, String content, String contentDetail) {}
