package com.lxp.course.web.dto.request;

public record LectureAddRequest(
        Long courseId, Long sectionId, String title, String videoUrl, int duration) {}
