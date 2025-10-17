package com.lxp.course.web.dto.response;

import com.lxp.course.domain.model.CourseDetail;

public record CourseDetailResponse(
        String content,
        String contentDetail
) {
    public static CourseDetailResponse from(CourseDetail detail) {
        if (detail == null) {
            return new CourseDetailResponse(null, null);
        }
        return new CourseDetailResponse(detail.content(), detail.contentDetail());
    }

}
