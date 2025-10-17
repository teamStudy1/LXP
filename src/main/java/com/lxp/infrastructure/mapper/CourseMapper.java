package com.lxp.infrastructure.mapper;

import com.lxp.domain.course.Course;
import com.lxp.domain.course.CourseDetail;
import com.lxp.domain.course.Section;
import com.lxp.domain.course.Tag;
import com.lxp.infrastructure.row.course.CourseRow;
import com.lxp.infrastructure.row.course.LectureRow;
import com.lxp.infrastructure.row.course.SectionRow;
import com.lxp.infrastructure.row.course.TagRow;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class CourseMapper {

    public static Optional<CourseRow> fromResultSet(ResultSet rs) throws SQLException {
        return Optional.of(new CourseRow(
                rs.getLong("course_id"),
                rs.getString("title"),
                rs.getLong("instructor_id"),
                rs.getLong("category_id"),
                rs.getInt("total_time"),
                rs.getInt("total_lecture_count"),
                rs.getString("content"),
                rs.getString("content_detail"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at")
        ));
    }

    public static Course toDomain(
            CourseRow courseRow,
            List<SectionRow> sectionRows,
            List<LectureRow> lectureRows,
            List<TagRow> tagRows
    ) {
        Map<Long, List<LectureRow>> lecturesBySectionId = lectureRows.stream()
                .collect(Collectors.groupingBy(LectureRow::sectionId));

        List<Section> sections = sectionRows.stream()
                .map(sr -> {
                    Section section = new Section(
                            sr.sectionId(),
                            sr.name(),
                            sr.orderNum(),
                            sr.createdAt().toLocalDateTime(),
                            sr.updatedAt().toLocalDateTime()
                    );

                    List<LectureRow> lrs = lecturesBySectionId.getOrDefault(sr.sectionId(), Collections.emptyList())
                            .stream()
                            .toList();

                    for (LectureRow lr : lrs) {
                        section.createLectureAt(
                                lr.name(),
                                lr.videoUrl(),
                                lr.lectureTime(),
                                lr.orderNum()
                        );
                    }
                    return section;
                })
                .toList();


        Set<Tag> tags = tagRows.stream()
                .map(tr -> new Tag(
                        tr.tagId(),
                        tr.name(),
                        tr.createdAt() != null ? tr.createdAt().toLocalDateTime() : null
                ))
                .collect(Collectors.toCollection(HashSet::new));

        // 4) CourseDetail 매핑 (필요에 맞게 수정)
        CourseDetail detail = new CourseDetail(
                courseRow.content(),
                courseRow.contentDetail()
        );

        Course course = new Course(
                courseRow.courseId(),
                courseRow.title(),
                courseRow.instructorId(),
                courseRow.createdAt().toLocalDateTime(),
                courseRow.updatedAt().toLocalDateTime(),
                detail,
                tags,
                sections
        );

        return course;
    }


}
