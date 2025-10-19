package com.lxp.course.web.dto.request;

import java.util.List;

public record SectionAddRequest(Long courseId, String title, List<LectureRequest> lectures) {}
