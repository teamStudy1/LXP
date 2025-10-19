package com.lxp.course.persistence.mapper;

import com.lxp.course.domain.model.Course;
import com.lxp.course.domain.model.CourseDetail;
import com.lxp.course.domain.model.Section;
import com.lxp.course.domain.model.Tag;
import com.lxp.course.persistence.row.CourseRow;
import com.lxp.course.persistence.row.LectureRow;
import com.lxp.course.persistence.row.SectionRow;
import com.lxp.course.persistence.row.TagRow;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class CourseMapper {

    public static CourseRow fromResultSet(ResultSet rs) throws SQLException {
        return new CourseRow(
                rs.getLong("course_id"),
                rs.getString("title"),
                rs.getLong("instructor_id"),
                rs.getLong("category_id"),
                rs.getString("total_time"),
                rs.getInt("total_lecture_count"),
                rs.getString("content"),
                rs.getString("content_detail"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at"));
    }

    public static Course toDomain(
            CourseRow courseRow,
            List<SectionRow> sectionRows,
            List<LectureRow> lectureRows,
            List<TagRow> tagRows) {
        Map<Long, List<LectureRow>> lecturesBySectionId =
                lectureRows.stream().collect(Collectors.groupingBy(LectureRow::sectionId));

        List<Section> sections =
                sectionRows.stream()
                        .map(
                                sr -> {
                                    Section section =
                                            new Section(
                                                    sr.sectionId(),
                                                    sr.name(),
                                                    sr.orderNum(),
                                                    sr.createdAt().toLocalDateTime(),
                                                    sr.updatedAt().toLocalDateTime());

                                    List<LectureRow> lrs =
                                            lecturesBySectionId
                                                    .getOrDefault(sr.sectionId(), Collections.emptyList())
                                                    .stream()
                                                    .toList();

                                    for (LectureRow lr : lrs) {
                                        section.createLectureAt(
                                                lr.name(), lr.videoUrl(), lr.lectureTime(), lr.orderNum());
                                    }
                                    return section;
                                })
                        .toList();

        Set<Tag> tags =
                tagRows.stream()
                        .map(
                                tr ->
                                        new Tag(
                                                tr.tagId(),
                                                tr.name(),
                                                tr.createdAt() != null ? tr.createdAt().toLocalDateTime() : null))
                        .collect(Collectors.toCollection(HashSet::new));

        CourseDetail detail = new CourseDetail(courseRow.content(), courseRow.contentDetail());

        Course course =
                new Course(
                        courseRow.courseId(),
                        courseRow.title(),
                        courseRow.instructorId(),
                        courseRow.createdAt().toLocalDateTime(),
                        courseRow.updatedAt().toLocalDateTime(),
                        detail,
                        tags,
                        sections,
                        courseRow.categoryId(),
                        courseRow.totalTime(),
                        courseRow.totalLectureCount());

        return course;
    }

    public static CourseRow toRow(Course course) {
        return new CourseRow(
                null,
                course.getTitle(),
                course.getInstructorId(),
                course.getCategoryId(),
                course.getTotalSeconds(),
                course.getTotalLectureCount(),
                course.getCourseDetail().content(),
                course.getCourseDetail().contentDetail(),
                null,
                null);
    }
}
