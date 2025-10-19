package com.lxp.course.web.dto.request;

import java.util.List;

public record SectionRequest(String title, List<LectureRequest> lectures) {}
