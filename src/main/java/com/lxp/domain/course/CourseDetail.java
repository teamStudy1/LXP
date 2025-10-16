package com.lxp.domain.course;

import java.time.Instant;
import java.util.Objects;
//VO 값 객체
record CourseDetail(String sb, Long courseId, String content, String contentDetail, Instant createdAt, Instant updatedAt)

{

}
