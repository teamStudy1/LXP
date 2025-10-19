package com.lxp.course.web.dto.response;

import com.lxp.course.domain.model.Lecture;
import java.time.LocalDateTime;

public record LectureResponse(
        Long id,
        String title,
        int lectureOrder,
        String videoUrl,
        int duration,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
    public static LectureResponse from(Lecture lecture) {
        return new LectureResponse(
                lecture.getId(),
                lecture.getTitle(),
                lecture.getLectureOrder(),
                lecture.getVideoUrl(),
                lecture.getDuration(),
                lecture.getCreatedAt(),
                lecture.getUpdatedAt());
    }
}
